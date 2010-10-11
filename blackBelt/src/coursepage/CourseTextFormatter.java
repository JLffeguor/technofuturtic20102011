package coursepage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;


//import blackbelt.model.Course;
//import blackbelt.ui.common.PictureResource;
//import blackbelt.ui.common.Video;
//import blackbelt.ui.common.VimeoVideo;
//import blackbelt.ui.common.YoutubeVideo;


/** From a source text of a Section (with tags as "[image ...]"),
 * produces html for the browser.
 *  
*  
 * @author John
 *
 */
public class CourseTextFormatter {
	String course; //Course
	String input;
    int currentIndex = 0;
    int errorCount = 0;  // errors detected.
	boolean instanceUsed = false;
	
	List<TextBlock> textBlocks = new ArrayList<TextBlock>();
	boolean shouldWePutParagraphTagsInsideTheCurrentTextBlock = true;
    
	public CourseTextFormatter(String courseParam, String inputParam) {//Course
		this.course = courseParam;
		if (inputParam == null) {
		    input = "";
		} else {
		    this.input = inputParam;
		}
	}
		
	public String format() {
		if (instanceUsed) {
			throw new IllegalStateException("Instances of this class are like condoms: not thread safe and not reusable. Should be thrown away after use.");
		} else {
			instanceUsed = true;
		}
		
		try {
			int currentIndexBeforeNextElement = currentIndex;
			Element element = findNextElement();
			while (element != null && errorCount == 0) {
				// Insert text before the element found.
				addResultTextBlock(input.substring(currentIndexBeforeNextElement, element.startPosition));

				// Insert (html) text replacing the element tag.
				insertOutputForElement(element);
				currentIndexBeforeNextElement = currentIndex;
				element = findNextElement();
			}
			addResultTextBlock(StringUtils.substring(input, currentIndex)); // apache substring to allow currentIndex to be too big.
		} catch (Exception e) {
			insertErrorMessage("Unidentified problem while parsing the tags in your text. Tags are elements like [image src='filename'], or [code] ... [/code]. It is probably a mistake in your text, but it may also be a bug. If you are convinced that it's a bug, please copy it on the forum.");
		}
		
		PTagsGenerator pTagsGenerator = new PTagsGenerator();
		return pTagsGenerator.transformTextBlocksIntoStringWithPTags();
	}
	

	protected Element findNextElement() {
		/////// Look for brackets [ ... ]
        int nextOpenBracketIndex = input.indexOf('[', currentIndex);
        if (nextOpenBracketIndex == -1) { // not found
        	return null;
        }
        int nextCloseBracketIndex = input.indexOf(']', currentIndex);
        if (nextCloseBracketIndex == -1) { // not found -> this is an error (not correctly closed tag)
        	insertErrorMessage("This tag has an open bracket [, but no closing bracket");
        	return null;  // TODO: insert something in output about the error.
        }
        if (nextOpenBracketIndex == nextCloseBracketIndex-1) { // "[]"
        	insertErrorMessage("This tag is empty, it has no name");
        	return null;  // TODO: insert something in output a bout the error.
        }

        //////////// Opening Tag.
        ////// Look for tagName
        // From "bla bla bla [tagName param='abc'] bla bla",
        // we isolate "tagName param='abc'"
        String tagWithParams = input.substring(nextOpenBracketIndex+1, nextCloseBracketIndex).trim();
        
        Element element = new Element();
        element.startPosition = nextOpenBracketIndex;
        element.endPosition = nextCloseBracketIndex;
        
        
        // Take the word after the bracket.
        int endOfTagNameIndex = tagWithParams.indexOf(' ');
        if (endOfTagNameIndex == -1) {
        	// There is no parameter (like "[code]")
        	endOfTagNameIndex = tagWithParams.length();
        }
        element.name = tagWithParams.substring(0, endOfTagNameIndex);
        element.name = element.name.trim().toLowerCase();
        
        ////// Look for parameters.
        Param param = findNextParam(tagWithParams, endOfTagNameIndex + 1);
        while (param != null) {  // More parameters
        	element.params.put(param.name, param.value);
        	param = findNextParam(tagWithParams, param.endIndexWithinTag + 1);
        }


        
        currentIndex = nextCloseBracketIndex +1;  // we are after the opening tag.

        //////////// Closing Tag.
        ///// Is there an "[/*tagName] before an opening tag of the same name (we don't support nested tags with same name) ?
        int nextCloseTagSameName = input.indexOf("[/"+element.name, currentIndex);
        if (nextCloseTagSameName == -1) { // Not found
    		return element; // there is no close tag for the current element.
        }
        // But is it the close tag of out current element or of a further element?
        // -> is there a further element starting before the close tag?
        int nextOpenTagSameName = input.indexOf("["+element.name, currentIndex);
        if (nextOpenTagSameName != -1) { // Found
         	if (nextOpenTagSameName < nextCloseTagSameName) { // It's before
           		return element; // there is no close tag for the current element.
           	} // else, ok, it's our close tag.
            
        } // else ok, it's our close tag.
        
        element.innerText = input.substring(currentIndex, nextCloseTagSameName);
        element.endPosition = input.indexOf(']', nextCloseTagSameName);
        
        
        currentIndex = element.endPosition +1;
		return element;
	}

	
	
	/** in "tagName param1="abc"  param2 = 'def'",
	 *  we search "param1" and "abc".*/
	protected Param findNextParam(String tagWithParams, int fromIndex) {
		Param param = new Param();
        int nextEqualIndex = tagWithParams.indexOf('=', fromIndex);
        
        if (nextEqualIndex == -1) {  // Is there one more parameter?
            // Check the error to have something other than a parameter with an = sign in the tag
        	if (fromIndex < tagWithParams.length()) {
        		String remainingText =  tagWithParams.substring(fromIndex).trim();
        		if (! remainingText.isEmpty() && ! "/".equals(remainingText)) {  //  "/" is normal if we use [image src="abc" /].
            		insertErrorMessage("The tag has something after the tag name that looks like not being a parameter (no \"=\" sign)");
            		return null;
        		}
        	}
        	
        	// No more parameter, last parameter already read.
        	return null;
        }
        
        
        ///// Get the name
        param.name = tagWithParams.substring(fromIndex, nextEqualIndex).trim();
        if (param.name.isEmpty()) {
        	insertErrorMessage("Tag parameter has no name");
        }
        
        ///// Get the value
        // Start of the quotes. does the tag use simple ' or double " quotes?
        int openSimpleQuoteIndex = tagWithParams.indexOf('\'', nextEqualIndex+1);
        int openDoubleQuoteIndex = tagWithParams.indexOf('"', nextEqualIndex+1);
        char quoteChar;  // ' or ", according to what has been used for this parameter.
        int valueStartIndex;  // in "tagName param1='XYZ'", it's the position of X.
        if (openSimpleQuoteIndex != -1) {
        	if (openDoubleQuoteIndex != -1) {  // both found
        		if (openSimpleQuoteIndex < openDoubleQuoteIndex) { // double quoite probably for the next parameter, not for this parameter.
        			quoteChar = '\'';
        			valueStartIndex = openSimpleQuoteIndex+1;
        		} else {
        			quoteChar = '"';
        			valueStartIndex = openDoubleQuoteIndex+1;
        		}
        	} else { // only simple quotes found.
    			quoteChar = '\'';
    			valueStartIndex = openSimpleQuoteIndex+1;
        	}
        } else if (openDoubleQuoteIndex != -1) {  // only double quotes  found
			quoteChar = '"';
			valueStartIndex = openDoubleQuoteIndex+1;
        } else { // No quote found....
        	insertErrorMessage("Tag parameter has no value");
        	return null;
        }
        
        // End of the quotes
        int valueEndIndex = tagWithParams.indexOf(quoteChar, valueStartIndex);
        if (valueEndIndex == -1) {
        	insertErrorMessage("Tag parameter value has an opening quote ("+quoteChar+") but no matching closing quote.");
        	return null;
        }
        
        param.value = tagWithParams.substring(valueStartIndex, valueEndIndex);
        param.endIndexWithinTag = valueEndIndex+1;  // On the closing quote.

        return param;
	}
	
	protected void addResultTextBlock(String text) {
		TextBlock textBlock = new TextBlock();
		textBlock.text = text;
		textBlock.insertParagraphs = shouldWePutParagraphTagsInsideTheCurrentTextBlock;
		textBlocks.add(textBlock);
		shouldWePutParagraphTagsInsideTheCurrentTextBlock = true;  // Default for next block.
	}
	
	
	protected void insertErrorMessage(String errorText) {
		addResultTextBlock("<span style='color:red;'>Format error: "+ errorText + "</span>");
		errorCount++;
	}

		
	protected void insertOutputForElement(Element element) {
		if ("image".equals(element.name)) {
			insertImage(element);
		} else if ("code".equals(element.name)) {
			insertCode(element);
		} else if ("quote".equals(element.name)) {
			insertQuote(element);
		} else if ("video".equals(element.name)) {
			insertVideo(element);
		} else if ("attachment".equals(element.name)) {
			insertAttachment(element);
		} else {
			insertErrorMessage("Tag name \""+ element.name + "\" not supported.");
		}
	}

	protected void insertAttachment(Element element) {
		try {
			String srcValue = element.getMandatoryValue("src");
//			String srcUrl = (new PictureResource(course, srcValue)).getURL();
			
			String imageValue = element.getOptionalValue("image");
//			String imageUrl = (new PictureResource(course, imageValue)).getURL();

			if(imageValue != null){
			shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
//			addResultTextBlock("<div style='overflow:auto' align='center'>" +  // Copied from Vaadin book layout. overflow:auto -> scrollbar if too wide.
//									"<a href='" + srcUrl + "'><img style='border: none;' align='middle' src='"+imageUrl+"'/></a>" +
//									"<br/><span style='font-size: 65%; font-color: #999999;' align='center'>Click to download</span>" + 
//							   "</div>");
			} else {
				shouldWePutParagraphTagsInsideTheCurrentTextBlock = true;
				element.innerText = StringEscapeUtils.escapeHtml(element.innerText);
				if(element.innerText == null || element.innerText.isEmpty()){
					insertErrorMessage("Tag name \""+ element.name + "\" must have a body when no image attribute is specified.");
				} else {
//					addResultTextBlock("<a href='" + srcUrl + "'>" + element.innerText + "</a>");	
				}
			}
			
		} catch (MandatoryParameterNotFoundException e) {
			// Ok, do nothing. Error message already inserted in output by exception thrower.
		}
	}
	
	
	protected void insertImage(Element element) {
		try {
			String srcValue = element.getMandatoryValue("src");
			/* only for data test */
			String imageUrl = ("S:\\DocumentsPourPDF\\imagesTestForPdf.gif");
//			String imageUrl = (new PictureResource(course, srcValue)).getURL();
			shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
			addResultTextBlock("<div style='overflow:auto' align='center'>" +  // Copied from Vaadin book layout. overflow:auto -> scrollbar if too wide.
									"<img align='middle' src='"+ imageUrl +"'/>" + //imageURL
							   "</div>");
			
		} catch (MandatoryParameterNotFoundException e) {
			// Ok, do nothing. Error message already inserted in output by exception thrower.
		}
	}
	
	
	protected void insertCode(Element element) {
		if (element.innerText == null) {
			insertErrorMessage("[code] ... [/code] elements should have text inside.");
			return;
		}
		
		if ("true".equals(element.getOptionalValue("escape"))) {  // Default is false.
			element.innerText = StringEscapeUtils.escapeHtml(element.innerText); // Escape any formatting (probably <b> tags that should be writtent "<b>" and not trigger bold).
		}

		if ("true".equals(element.getOptionalValue("inline"))) {  // Default is false.
			addResultTextBlock("<code>"+element.innerText+"</code>");
			
		} else { // Here we do not want inline (usual case) 
			// <pre> creates carriage returns in the browser
			shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
			addResultTextBlock("<pre class='contentProgramListing' xml:space='preserve'>"   // Copied from Vaadin book layout.
					+element.innerText+"</pre>");
		}
	}
	
	protected void insertQuote(Element element) {
		if (element.innerText == null) {
			insertErrorMessage("[quote] ... [/quote] elements should have text inside.");
			return;
		}
		
		shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
		addResultTextBlock("<div class='contentQuote'>"+element.innerText+"</div>");
	}	

	protected void insertVideo(Element element) {
		try {
			int width = element.getOptionalIntValue("width", 500);
			int height = element.getOptionalIntValue("height", width*9/16);
			String videoId = element.getMandatoryValue("id");
			
			
			// Video object creation (to get the html).
//			Video video;
			
			//Use a boolean to know if we will format in pdf
			
			String typeValue = element.getMandatoryValue("type").toLowerCase();
			
			if ("youtube".equals(typeValue)) {
//				video = new YoutubeVideo(videoId, width, height, false);

			} else if ("vimeo".equals(typeValue)) {
				try {
					Long videoIdL = Long.parseLong(videoId);
//					video = new VimeoVideo(videoIdL, width, height);
				} catch(NumberFormatException e) {
					insertErrorMessage("Paramter id is supposed to be a number for Vimeo videos. Current value is ["+videoId+"]");
					return;
				}

			} else {
				insertErrorMessage("[video] element with unsupported type value '"+typeValue+"'. Should be 'youtube' or 'vimeo'");
				return;
			}
			//use a boolean to know if we should format to pdf
			boolean shouldWeFormatToPdf = true;
			if (shouldWeFormatToPdf){
				addResultTextBlock("</p></p><img align='center' src = \"S:\\DocumentsPourPDF\\imagesVideo.jpg \"" + "<p>" + videoId + "\nYou can watch this video on BlackBeltFactory");
			}
//			addResultTextBlock("<div style='overflow:auto' align='center'>" + video.getHtml() + "</div>");
			
		} catch (MandatoryParameterNotFoundException e) {
			// Ok, do nothing. Error message already inserted in output by exception thrower.
		}
	}
	
	
	
	/** Transforms the textBlocks list into a string with <p> tags. */
	protected class PTagsGenerator {

		String[] splitsOfCurrentBlock;
		int currentSplitIndex;
		
		int currentTextBlockIndex;
		boolean currentLineShouldBePTagged = true;
		
		String transformTextBlocksIntoStringWithPTags() {
			StringBuffer output = new StringBuffer(input.length() + (input.length()/4));
			
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

	protected class Element {
		String name;
		Map<String, String> params = new HashMap<String, String>();
		String innerText;  // For elements have an open tag and a close tag. "[code] inner text [/code]"
		int startPosition;
		int endPosition;  // end position of the closing tag (or the only tag if there is no closing tag and innerText)

		String getMandatoryValue(String paramName) throws MandatoryParameterNotFoundException {
			String value = params.get(paramName);
			if (value == null) {
				insertErrorMessage("Mandatory parameter \""+ paramName + "\" not found in tag \""+name+"\".");
				throw new MandatoryParameterNotFoundException();
			}
			return value;
		}
		
		String getOptionalValue(String paramName) { // Just there for symmetry with getMandatoryValue()
			return params.get(paramName);
		}

		int getOptionalIntValue(String paramName, int defaultValue) {
			Integer value = getIntValue(paramName);
			if (value == null) {
				return defaultValue;
			} else {
				return value;
			}
		}
		
		private Integer getIntValue(String paramName) { 
			String value = params.get(paramName);
			if (value == null) {
				return null;
			}

			try {
				int intValue = Integer.parseInt(value);
				return intValue;
			} catch(NumberFormatException e) {
				insertErrorMessage("Paramter ["+ paramName +"] is supposed to be an integer. Current value is ["+params.get(paramName)+"]");
				return null;
			}
		}

	}
	
	/** To get out from a code block when an expected parameter is not found */
	protected class MandatoryParameterNotFoundException extends Exception {
	}
	
	protected class Param {
		String name;
		String value;
		int endIndexWithinTag;  // Position where the param stops within the tag.
	}
	
	// Used to store results of tags parsing, before we insert the <p> tags.
	// Example: if input =
	//   This image shows
	//   [IMAGE .... /]
	//   the [code inline="true"]Button[/code] class.
	//   Bye
	// then we have 5 TextBlocks: 
	//   "This image shows\n",   true
	//   "<img .... />",         false
	//   "\nthe ",               true
	//   "<code>Button</code>",  true (becuase inline code)
	//   " class.\nBye."         true
	protected class TextBlock {
		boolean insertParagraphs = true;  // Should we put the lines around <p> ?
		String text;
	}
}

