package ctfTesting;

public interface BlackBeltTagHandler {

	void onImageTag(String imageSrc);
	void onVideoTag(String videoId,int width,int height,String typeValue);
	void onAttachmentTag(String srcUrl, String imageUrl  );
	void onCodeTag(String textCode,boolean inline, boolean escape, String lang, boolean num);
	void onQuoteTag(String innerText);
	void onText(String text);
	void onError(String errorText);
	String getOutputString();
}
