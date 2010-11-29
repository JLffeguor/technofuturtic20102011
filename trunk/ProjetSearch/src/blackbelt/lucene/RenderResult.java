package blackbelt.lucene;

import org.apache.lucene.document.Document;

public class RenderResult {

	public String extractResult(Document doc,String keyword) {
		String textSectionFull = doc.get("text");
		textSectionFull = textSectionFull.toLowerCase();
		int keyWordStart = textSectionFull.indexOf(keyword);
		int keyWordEnd = textSectionFull.indexOf(keyword)+keyword.length();
		
		String subStrBeforeKeyword = "";
		String subStrAfterKeyword = "";
		//find first  /n caracter before (20 char next)
		int startSubStrBeforeKeyword = keyWordStart - 50;
		if (keyWordStart==-1){
			keyWordStart=0;
		}
		if (startSubStrBeforeKeyword < 0){
			startSubStrBeforeKeyword = 0;
			
		}
		subStrBeforeKeyword = textSectionFull.substring(startSubStrBeforeKeyword,keyWordStart);
		if(subStrBeforeKeyword.indexOf(" ")!= -1){
		startSubStrBeforeKeyword = subStrBeforeKeyword.indexOf(" ");
		}else{
			startSubStrBeforeKeyword=0;
		}
		subStrBeforeKeyword =  subStrBeforeKeyword.substring(startSubStrBeforeKeyword,subStrBeforeKeyword.length());
		//find first void caracter after (20 char next)
		int endSubStrAfterKeyword = keyWordEnd + 50;
		if (endSubStrAfterKeyword > textSectionFull.length()){
			endSubStrAfterKeyword = textSectionFull.length();
		}
		
		subStrAfterKeyword = textSectionFull.substring(keyWordEnd,endSubStrAfterKeyword);
		endSubStrAfterKeyword = subStrAfterKeyword.lastIndexOf(" ");
		if(endSubStrAfterKeyword == -1){
			endSubStrAfterKeyword = subStrAfterKeyword.length();
		}
		subStrAfterKeyword = subStrAfterKeyword.substring(0,endSubStrAfterKeyword);
		
		String finalResultText = subStrBeforeKeyword + " <b>" + keyword + "</b> " + subStrAfterKeyword ;
		return finalResultText;
	}
}
