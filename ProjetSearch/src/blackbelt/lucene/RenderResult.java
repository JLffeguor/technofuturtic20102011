package blackbelt.lucene;

import org.apache.lucene.document.Document;

public class RenderResult {

	public String extractResult(Document doc,String keyword) {
		String textSectionFull = doc.get("text");
		
		int keyWordStart = textSectionFull.indexOf(keyword);
		int keyWordEnd = textSectionFull.indexOf(keyword)+keyword.length();
		
		String subStrBeforeKeyword = "";
		String subStrAfterKeyword = "";
		//find first  /n caracter before (20 char next)
		int startTextBefore = keyWordStart - 50;
		if (keyWordStart==-1){
			keyWordStart=0;
		}
		if (startTextBefore < 0){
			startTextBefore = 0;
			
		}
		subStrBeforeKeyword = textSectionFull.substring(startTextBefore,keyWordStart);
		if(subStrBeforeKeyword.indexOf(" ")!= -1){
		startTextBefore = subStrBeforeKeyword.indexOf(" ");
		}else{
			startTextBefore=0;
		}
		subStrBeforeKeyword =  subStrBeforeKeyword.substring(startTextBefore,subStrBeforeKeyword.length());
		//find first void caracter after (20 char next)
		int endTextAfter = keyWordEnd + 50;
		if (endTextAfter > textSectionFull.length()){
			endTextAfter = textSectionFull.length();
		}
		subStrAfterKeyword = textSectionFull.substring(keyWordEnd,endTextAfter);
		endTextAfter = subStrAfterKeyword.lastIndexOf(" ");
		subStrAfterKeyword = subStrAfterKeyword.substring(0,endTextAfter);
		
		String finalResultText = subStrBeforeKeyword + " <b>" + keyword + "</b> " + subStrAfterKeyword ;
		return finalResultText;
	}
}
