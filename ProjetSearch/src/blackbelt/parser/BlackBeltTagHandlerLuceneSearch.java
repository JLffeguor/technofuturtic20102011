package blackbelt.parser;

import blackbelt.parser.BlackBeltTagParser.Element;

/**
*This parse a text from a section without BlackBelt tag 
*This is use by the search engine project to build a index
*We only need title of a attachment and the text code between [code][/code]
*/
public class BlackBeltTagHandlerLuceneSearch extends BlackBeltTagHandlerStringGenerator{

	public BlackBeltTagHandlerLuceneSearch(){
	}
	
	public void onImageTag(Element element) {		
		// Nothing: we don't index the video tags...
	}

	@Override
	public void onVideoTag(Element element) {
		// Nothing: we don't index the video tags...
	}

	@Override
	public void onAttachmentTag(Element element) {
		//TODO title
	}
	
	/** We use only the textCode attribute but we must let the other because it the declaration method in
	 * the interface BlackBeltTagParser */
	@Override
	public void onCodeTag(String textCode, boolean inline, boolean escape, String lang, boolean num) {
		addResultTextBlock(textCode, false);
	}

	@Override
	public void onQuoteTag(String innerText) {
		addResultTextBlock(innerText, false);
	}
}
