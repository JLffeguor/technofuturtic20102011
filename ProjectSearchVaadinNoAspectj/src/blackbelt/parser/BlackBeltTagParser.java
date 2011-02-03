package blackbelt.parser;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/** Parses a text with [tags] understood by blackbelt factory.
 * 
 * Example: "Does this code compile: [code lang="Java" inline="true"]int i;  ++i;[/code]".
 * 
 * Typical tags are [code], [image], [video].
 * 
 * This gets BlackBeltTagHandler as parameter and calls method on it for each encountered tag.
 */
public class BlackBeltTagParser {
	
	protected BlackBeltTagHandler blackBeltTagHandler;
	protected boolean shouldWePutParagraphTagsInsideTheCurrentTextBlock = true; // Tells if the PTagGenerator can put a textBlock in the tags <p></p>
	protected boolean instanceUsed = false;  // Parser is not reusable.
	protected String input;
	protected int currentIndex = 0;
	protected int errorCount = 0;
	
	/** The BlackBeltTagHandler must have a parser. So, we call the method setParser in the constructor */
	public BlackBeltTagParser(BlackBeltTagHandler handler, String inputString){
		this.input = inputString;
		this.blackBeltTagHandler = handler;
		if (handler instanceof BlackBeltTagHandlerStringGenerator){
			handler.setParser(this);
		}
	}
	
	/** 
	 * This method parses the text. It checks for the character '[' (the element.startPosition)
	 * when it find the element, it call the right method to change the inner text in HTML balises
	 *  */
	public String parse() {
		if (instanceUsed) {
			throw new IllegalStateException(
					"Instances of this class are like condoms: not thread safe and not reusable. Should be thrown away after use.");
		} else {
			instanceUsed = true;
		}

		try {
			int currentIndexBeforeNextElement = currentIndex;
			Element element = findNextElement();
			
			while (element != null && errorCount == 0) {
				// Insert text before the element found.
				blackBeltTagHandler.onText(input.substring(
						currentIndexBeforeNextElement, element.startPosition));

				onElement(element);
				
				currentIndexBeforeNextElement = currentIndex;
				element = findNextElement();
			}
			//Call the TagHandler
			blackBeltTagHandler.onText(
					StringUtils.substring(input, currentIndex)); // apache substring to allow currentIndex to be too big.
			
		} catch (Exception e) {
			fireError("Unidentified problem while parsing the tags in your text. Tags are elements like [image src='filename'], or [code] ... [/code]. It is probably a mistake in your text, but it may also be a bug. If you are convinced that it's a bug, please copy it on the forum.");
		}
		
		return blackBeltTagHandler.getOutputString(input);
	}

	/** May be called by this and by the BlackBeltTagHandler */
	void fireError(String errorText) {
		errorCount++;
		blackBeltTagHandler.onError(errorText);
	}
	
	/** Get the expected attribute of an element (as inline in [code inline="true"]) 
	 * and calls the corresponding BlackBeltTagHandler method. 
	 **/
	protected void onElement(Element element) {

		if ("image".equals(element.name)) {
			blackBeltTagHandler.onImageTag(element);

		} else if ("code".equals(element.name)) {

			if (element.innerText == null) {
				fireError("[code] ... [/code] elements should have text inside.");
				return;
			}				
			blackBeltTagHandler.onCodeTag(element.innerText, 
					"true".equals(element.getOptionalValue("inline")),
					"true".equals(element.getOptionalValue("escape")),
					element.getOptionalValue("lang"),
					"true".equals(element.getOptionalValue("num")));

		} else if ("quote".equals(element.name)) {
			if (element.innerText == null) {
				fireError("[quote]...[/quote] elements should have text inside");
				return;
			}
			shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
			blackBeltTagHandler.onQuoteTag(element.innerText);

		} else if ("video".equals(element.name)) {
			blackBeltTagHandler.onVideoTag(element);

		} else if ("attachment".equals(element.name)) {
			blackBeltTagHandler.onAttachmentTag(element);


		} else {
			fireError("Tag name \"" + element.name + "\" not supported.");
		}
	}

	protected Element findNextElement() {
		// ///// Look for brackets [ ... ]
		int nextOpenBracketIndex = input.indexOf('[', currentIndex);
		if (nextOpenBracketIndex == -1) { // not found
			return null;
		}
		int nextCloseBracketIndex = input.indexOf(']', currentIndex);
		if (nextCloseBracketIndex == -1) { // not found -> this is an error (not
											// correctly closed tag)
			fireError("This tag has an open bracket [, but no closing bracket");
			return null; // TODO: insert something in output about the error.
		}
		if (nextOpenBracketIndex == nextCloseBracketIndex - 1) { // "[]"
			fireError("This tag is empty, it has no name");
			return null; // TODO: insert something in output a bout the error.
		}

		// ////////// Opening Tag.
		// //// Look for tagName
		// From "bla bla bla [tagName param='abc'] bla bla",
		// we isolate "tagName param='abc'"
		String tagWithParams = input.substring(nextOpenBracketIndex + 1,
				nextCloseBracketIndex).trim();

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

		// //// Look for parameters.
		Param param = findNextParam(tagWithParams, endOfTagNameIndex + 1);
		while (param != null) { // More parameters
			element.params.put(param.name, param.value);
			param = findNextParam(tagWithParams, param.endIndexWithinTag + 1);
		}

		currentIndex = nextCloseBracketIndex + 1; // we are after the opening tag.

		// ////////// Closing Tag.
		// /// Is there an "[/*tagName] before an opening tag of the same name
		// (we don't support nested tags with same name) ?
		int nextCloseTagSameName = input.indexOf("[/" + element.name,
				currentIndex);
		if (nextCloseTagSameName == -1) { // Not found
			return element; // there is no close tag for the current element.
		}
		// But is it the close tag of out current element or of a further element?
		// -> is there a further element starting before the close tag?
		int nextOpenTagSameName = input.indexOf("[" + element.name,
				currentIndex);
		if (nextOpenTagSameName != -1) { // Found
			if (nextOpenTagSameName < nextCloseTagSameName) { // It's before
				return element; // there is no close tag for the current element.
			} // else, ok, it's our close tag.

		} // else ok, it's our close tag.

		element.innerText = input.substring(currentIndex, nextCloseTagSameName);
		element.endPosition = input.indexOf(']', nextCloseTagSameName);

		currentIndex = element.endPosition + 1;
		return element;
	}

	protected Param findNextParam(String tagWithParams, int fromIndex) {
		Param param = new Param();
		int nextEqualIndex = tagWithParams.indexOf('=', fromIndex);

		if (nextEqualIndex == -1) { // Is there one more parameter?
			// Check the error to have something other than a parameter with an
			// = sign in the tag
			if (fromIndex < tagWithParams.length()) {
				String remainingText = tagWithParams.substring(fromIndex)
						.trim();
				if (!remainingText.isEmpty() && !"/".equals(remainingText)) { // "/" is normal if we use [image src="abc" /].
					fireError("The tag has something after the tag name that looks like not being a parameter (no \"=\" sign)");
					return null;
				}
			}

			// No more parameter, last parameter already read.
			return null;
		}

		// /// Get the name
		param.name = tagWithParams.substring(fromIndex, nextEqualIndex).trim();
		if (param.name.isEmpty()) {
			fireError("Tag parameter has no name");
		}

		// /// Get the value
		// Start of the quotes. does the tag use simple ' or double " quotes?
		int openSimpleQuoteIndex = tagWithParams.indexOf('\'',
				nextEqualIndex + 1);
		int openDoubleQuoteIndex = tagWithParams.indexOf('"',
				nextEqualIndex + 1);
		char quoteChar; // ' or ", according to what has been used for this parameter.
		int valueStartIndex; // in "tagName param1='XYZ'", it's the position of X.
		if (openSimpleQuoteIndex != -1) {
			if (openDoubleQuoteIndex != -1) { // both found
				if (openSimpleQuoteIndex < openDoubleQuoteIndex) { // double quote probably for the next parameter, not for this parameter.
					quoteChar = '\'';
					valueStartIndex = openSimpleQuoteIndex + 1;
				} else {
					quoteChar = '"';
					valueStartIndex = openDoubleQuoteIndex + 1;
				}
			} else { // only simple quotes found.
				quoteChar = '\'';
				valueStartIndex = openSimpleQuoteIndex + 1;
			}
		} else if (openDoubleQuoteIndex != -1) { // only double quotes found
			quoteChar = '"';
			valueStartIndex = openDoubleQuoteIndex + 1;
		} else { // No quote found....
			fireError("Tag parameter has no value");
			return null;
		}

		// End of the quotes
		int valueEndIndex = tagWithParams.indexOf(quoteChar, valueStartIndex);
		if (valueEndIndex == -1) {
			fireError("Tag parameter value has an opening quote ("
					+ quoteChar + ") but no matching closing quote.");
			return null;
		}

		param.value = tagWithParams.substring(valueStartIndex, valueEndIndex);
		param.endIndexWithinTag = valueEndIndex + 1; // On the closing quote.

		return param;
	}
	
	/** To get out from a code block when an expected parameter is not found */
	protected class MandatoryParameterNotFoundException extends Exception {
	}


	protected class Element {
		String name;
		Map<String, String> params = new HashMap<String, String>();
		String innerText; // For elements have an open tag and a close tag.
							// "[code] inner text [/code]"
		int startPosition;
		int endPosition; // end position of the closing tag (or the only tag if
							// there is no closing tag and innerText)

		String getMandatoryValue(String paramName)
				throws MandatoryParameterNotFoundException {
			String value = params.get(paramName);
			if (value == null) {
				fireError("Mandatory parameter \"" + paramName
						+ "\" not found in tag \"" + name + "\".");
				throw new MandatoryParameterNotFoundException();
			}
			return value;
		}

		String getOptionalValue(String paramName) { // Just there for symmetry
													// with getMandatoryValue()
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
			} catch (NumberFormatException e) {
				fireError("Paramter [" + paramName + "] is supposed to be an integer." +
						" Current value is [" + params.get(paramName) + "]");
				return null;
			}
		}

	}

	protected class Param {
		String name;
		String value;
		int endIndexWithinTag; // Position where the param stops within the tag.
	}

}