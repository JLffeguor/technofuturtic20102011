package blackbelt.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BlackBeltTagHandlerStringGenerator implements BlackBeltTagHandler{
	
	List<TextBlock> textBlocks = new ArrayList<TextBlock>();

	BlackBeltTagParser blackBeltTagParser;  // Parser that is using/calling us.

	BlackBeltTagHandlerStringGenerator() {}

	public void setParser(BlackBeltTagParser blackBeltTagParser) {
		this.blackBeltTagParser = blackBeltTagParser;
	}


	/** Call this method after the parser.parse(), to get the result HTML formatted text */
	public String getOutputString(String output){
		PTagsGenerator pTagsGenerator = new PTagsGenerator();
		
		return pTagsGenerator.transformTextBlocksIntoStringWithPTags(output);
	}

	@Override
	public void onText(String text){
		addResultTextBlock(text, true);
	}
	
	
	
	/** Inserts error text in red */
	@Override
	public void onError(String errorText) {
		addResultTextBlock("<span style='color:red;'>Format error: "+ errorText + "</span>", true);
	}

	/** Called internally to add text to the output String 
	 * Text is not directly added to the string but stored as TextBlocks (paragraphs).
	 * PTagsGenerator will transform the list of TextBlocks into the output String.
	 * */
	protected void addResultTextBlock(String text, boolean shouldWePutParagraphTagsInsideTheCurrentTextBlock) {
		TextBlock textBlock = new TextBlock();
		textBlock.text = text;
		textBlock.insertParagraphs = shouldWePutParagraphTagsInsideTheCurrentTextBlock;
		this.textBlocks.add(textBlock);
	}

	/** Inserts <p> tags to form paragraphs
	 * Example input: 
	 *   Hello
	 *   World
	 *   
	 *   line4<br/>
	 *   line5.
	 *   
	 * Example output:
	 *   <p>Hello</p>
	 *   <p>World</p>
	 *   <p>line4<br/>    -- br in input prevents </p> to be generated.
	 *   line5</p>
	 *  
	 *  */
	protected class PTagsGenerator {

		String[] splitsOfCurrentBlock;
		int currentSplitIndex;
		
		int currentTextBlockIndex;
		boolean currentLineShouldBePTagged = true;
		
		String transformTextBlocksIntoStringWithPTags(String text) {
			StringBuffer output = new StringBuffer(text.length() + (text.length()/4));
			
			boolean openPHasNotBeenClosed = false;  // true if a <br/> makes the paragraph span multiple lines. 
			String line = getNextLine();
			while (line != null) {
				if (currentLineShouldBePTagged) {
					if ("".equals(line)) { // Empty line. We remove them. If the user really wants extra lines, he must insert <br/>
						// do nothing (don't output a <p></p>)
						
					} else {  // Non empty line (normal case
						if (line.indexOf("<br>") >=0 || line.indexOf("<br/>") >=0) { // Contains (probably ends with) "<br>"

							// Means that the user does not want us to add a paragraph around, but simply wants a line break inside another paragraph.
							if (! openPHasNotBeenClosed) { // It's a new paragraph (usual case)
								output.append("<p>");
								openPHasNotBeenClosed = true;  // Should be closed later.
							}
							output.append(line + "\n");
						} else { // Usual case
							if (! openPHasNotBeenClosed) { // It's a new paragraph (usual case)
								output.append("<p>");
							}
							output.append(line + "</p>\n");
							openPHasNotBeenClosed = false;  // Should be closed later.
						}
					}
				} else { // Special line with no <p>. example: "<img .../>", or "<pre>abcd".
					output.append(line + "\n");
				}
				line = getNextLine();
			}

			return output.toString();
		}

		
		// If we have 5 TextBlocks: 
		//   "This image shows\n",   true
		//   "<img .... />",         false
		//   "\nthe ",               true
		//   "<code>Button</code>",  true (because inline code)
		//   " class.\nBye."         true
		// then we have the following lines
		//   This image shows        true
		//   <img .../>              true
		//   the <code>Button</code> class. true
		//   Bye                     true
		private String getNextLine() {
			if (currentTextBlockIndex >= textBlocks.size()) {
				return null; // Last line reached
			}
				
			String result = "";
			TextBlock textBlock = textBlocks.get(currentTextBlockIndex);
			
			if (splitsOfCurrentBlock != null &&  // Could be the case for the 1st call.
					currentSplitIndex < splitsOfCurrentBlock.length-1) {  // It's not the last line of its block 
				result = splitsOfCurrentBlock[currentSplitIndex];
				currentSplitIndex++;
				currentLineShouldBePTagged = textBlock.insertParagraphs;
				
			} else {  // We are the last line of the block.
				if (splitsOfCurrentBlock == null) { // First call to getNextLine 
					currentTextBlockIndex = -1;  // a ++ follows below...
				} else { // Normal case
					result = splitsOfCurrentBlock[currentSplitIndex];
					currentLineShouldBePTagged = textBlock.insertParagraphs;
				}
				//// Next block with a \n
				// We iterate until we find a block containing are \n
				while (true) {  // We have return inside statements as stop conditions.
					// Next block from the list
					splitsOfCurrentBlock = null;
					currentSplitIndex = 0;
					currentTextBlockIndex++;
					
					if (currentTextBlockIndex < textBlocks.size()) { // One more block to handle
						textBlock = textBlocks.get(currentTextBlockIndex);
						currentLineShouldBePTagged = currentLineShouldBePTagged && textBlock.insertParagraphs;  // Should be tagged with <p> only if all parts of the line (result) should be tagged.
						splitsOfCurrentBlock = textBlock.text.split("\n");
						if (textBlock.text.endsWith("\n")) { // There is a new line at the end of the block (not detected by the split above).
							// We are going to add one empty element "" at the end of splitsOfCurrentBlock.
							splitsOfCurrentBlock = Arrays.copyOf(splitsOfCurrentBlock, splitsOfCurrentBlock.length+1);
							splitsOfCurrentBlock[splitsOfCurrentBlock.length-1] = ""; // additional element (will be added at the beginning of the next line).
						}

						if (splitsOfCurrentBlock.length == 1) {  // There is no "/n" in this block.
							// We add the split and we continue for more.
							result += splitsOfCurrentBlock[0];
						} else if (splitsOfCurrentBlock.length > 1) { // There is a \n in this block. We'll stop iterating for blocks here.
							// We add the first split and we stop.
							result += splitsOfCurrentBlock[0];
							currentSplitIndex++;
							return result;
						} else throw new RuntimeException("This should not happen... String is ["+textBlock.text+"]");
					} else {  // We have reached the end, the last block.
						return result;
					}
				}
			}
			
			return result;
		}
	}

	
	protected class TextBlock {
		boolean insertParagraphs = true;  // Should we put the lines around <p> ?
		String text;
	}
	
	/** Descendant must call this when they detect an error, to insert a red error text within the output */
	protected void insertErrorMessage(String errorText) {
		blackBeltTagParser.fireError(errorText);  // Will indirectly call this.onError();
	}
}
