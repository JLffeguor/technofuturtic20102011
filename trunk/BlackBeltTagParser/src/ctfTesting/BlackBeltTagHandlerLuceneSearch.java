package ctfTesting;

import org.apache.commons.lang.StringEscapeUtils;

public class BlackBeltTagHandlerLuceneSearch extends BlackBeltTagHandlerStringGenerator{

	public void onImageTag(String imageSrc) {//param must be replace by Element element in JavaBlackBelt project
//		try {
//			String srcValue = element.getMandatoryValue("src");
//			String imageUrl = (new PictureResource(course, srcValue)).getURL();
//			shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
//			addResultTextBlock(imageUrl);
//			
//		} catch (MandatoryParameterNotFoundException e) {
//			// Ok, do nothing. Error message already inserted in output by exception thrower.
//		}
		
		shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
		addResultTextBlock(imageSrc);
	}

	@Override
	public void onVideoTag(String videoId, int width, int geight,//param must be replace by Element element in JavaBlackBelt project
			String typeValue) {
//		try {
//			int width = element.getOptionalIntValue("width", 500);
//			int height = element.getOptionalIntValue("height", width*9/16);
//			String videoId = element.getMandatoryValue("id");
//			
//			// Video object creation (to get the html).
//			Video video;
//			String typeValue = element.getMandatoryValue("type").toLowerCase();
//			
//			if ("youtube".equals(typeValue)) {
//				video = new YoutubeVideo(videoId, width, height, false);
//
//			} else if ("vimeo".equals(typeValue)) {
//				try {
//					Long videoIdL = Long.parseLong(videoId);
//					video = new VimeoVideo(videoIdL, width, height);
//				} catch(NumberFormatException e) {
//					insertErrorMessage("Paramter id is supposed to be a number for Vimeo videos. Current value is ["+videoId+"]");
//					return;
//				}
//
//			} else {
//				insertErrorMessage("[video] element with unsupported type value '"+typeValue+"'. Should be 'youtube' or 'vimeo'");
//				return;
//			}
//			
//			addResultTextBlock(video.getHtml());
//			
//		} catch (MandatoryParameterNotFoundException e) {
//			// Ok, do nothing. Error message already inserted in output by exception thrower.
//		}
		
		shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
		addResultTextBlock("<div style='overflow:auto' align='center'>" +  // Copied from Vaadin book layout. overflow:auto -> scrollbar if too wide.
									 "<p>The video is "+videoId+"</p>"+
									 "<p>The video is on "+typeValue+"</p>"+
									 "<p>The video width is "+width+"</p>"+
							   "</div>");
	}

	@Override
	public void onAttachmentTag(String srcUrl, String imageUrl) {//param must be replace by Element element in JavaBlackBelt project
		
//		try {
//			String srcValue = element.getMandatoryValue("src");
//			String srcUrl = (new PictureResource(course, srcValue)).getURL();
//			
//			String imageValue = element.getOptionalValue("image");
//			String imageUrl = (new PictureResource(course, imageValue)).getURL();
//
//			if(imageValue != null){
//			shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
//			addResultTextBlock(srcUrl);
//			} else {
//				shouldWePutParagraphTagsInsideTheCurrentTextBlock = true;
//				element.innerText = StringEscapeUtils.escapeHtml(element.innerText);
//				if(element.innerText == null || element.innerText.isEmpty()){
//					insertErrorMessage("Tag name \""+ element.name + "\" must have a body when no image attribute is specified.");
//				} else {
//					addResultTextBlock(element.innerText);	
//				}
//			}
//			
//		} catch (MandatoryParameterNotFoundException e) {
//			// Ok, do nothing. Error message already inserted in output by exception thrower.
//		}
		
		shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
		addResultTextBlock("<div style='overflow:auto' align='center'>" +  // Copied from Vaadin book layout. overflow:auto -> scrollbar if too wide.
									 "<p>The image is "+imageUrl+"</p>"+
									 "<p>The source is "+srcUrl+"</p>"+ 
							   "</div>");
	}

	@Override
	public void onCodeTag(String textCode, boolean inline, boolean escape,//param must be replace by Element element in JavaBlackBelt project
			String lang, boolean num) {
		if (textCode == null) {
			onError("[code] ... [/code] elements should have text inside.");
			return;
		}
		
		if ("true".equals(escape)) {  // Default is false.
			textCode = StringEscapeUtils.escapeHtml(textCode); // Escape any formatting (probably <b> tags that should be writtent "<b>" and not trigger bold).
		}

		if ("true".equals(inline)) {  // Default is false.
			addResultTextBlock(textCode);
			
		} else { // Here we do not want inline (usual case) 
			// <pre> creates carriage returns in the browser
			shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
			addResultTextBlock(textCode);
		}
	}

	@Override
	public void onQuoteTag(String innerText) {//param must be replace by Element element in JavaBlackBelt project
		if (innerText == null) {
			onError("[quote] ... [/quote] elements should have text inside.");
			return;
		}
		
		shouldWePutParagraphTagsInsideTheCurrentTextBlock = false;
		addResultTextBlock(innerText);
	}
}
