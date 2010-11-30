package blackbelt.lucene.searchIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;

public class RenderResult {
	private String keyword;
//	private Document doc;
	private final int MAX = 100;
	RenderResult(String keyword){
//		this.doc=doc;
		this.keyword = keyword;
	}

	public String extractResult(Document doc) {
		String courseUrl="http://www.blackbeltfactory.com/ui#CoursePage/";
		String textSectionFull = doc.get("text");
		textSectionFull = textSectionFull.toLowerCase();
		int indexDebKeyword = 0;
		List<String> keywordList = new ArrayList<String>();
		String result = "";
		
		while(indexDebKeyword < keyword.length()){
			int indexBlankSpace = keyword.indexOf(" ");
			if (indexBlankSpace == -1){
				keywordList.add(keyword);
				break;
			}else{
			keywordList.add(keyword.substring(indexDebKeyword,indexBlankSpace));
			}
			keyword = keyword.substring(indexBlankSpace+1,keyword.length());
		}
		for(String str: keywordList){
			result += textAroundKeyword(doc, courseUrl, textSectionFull,str);
		}
		
		return "...." + result +"....<br/><a href='"+courseUrl+doc.get("sectionid")+"'>"+courseUrl+doc.get("sectionid")+"</a>"; 
	}

	private String textAroundKeyword(Document doc, String courseUrl,String textSectionFull,String singleKeyword) {
		int keyWordStart = textSectionFull.indexOf(singleKeyword);
		int keyWordEnd = textSectionFull.indexOf(singleKeyword)+singleKeyword.length();
		
		String subStrBeforeKeyword = "";
		String subStrAfterKeyword = "";
		//find first  /n caracter before (20 char next)
		int startSubStrBeforeKeyword = keyWordStart - MAX;
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
		int endSubStrAfterKeyword = keyWordEnd + MAX;
		if (endSubStrAfterKeyword > textSectionFull.length()){
			endSubStrAfterKeyword = textSectionFull.length();
		}
		
		subStrAfterKeyword = textSectionFull.substring(keyWordEnd,endSubStrAfterKeyword);
		endSubStrAfterKeyword = subStrAfterKeyword.lastIndexOf(" ");
		if(endSubStrAfterKeyword == -1){
			endSubStrAfterKeyword = subStrAfterKeyword.length();
		}
		subStrAfterKeyword = subStrAfterKeyword.substring(0,endSubStrAfterKeyword);
		
		String firstResult = subStrBeforeKeyword + " <b>" + singleKeyword + "</b> " + subStrAfterKeyword  + "<br>";
		return firstResult;
	}
	
	public void toHTML(List<String> bigString){
		String result = "";
		String courseUrl="http://www.blackbeltfactory.com/ui#CoursePage/";
		result+="<html><head><title>Black Belt Google</title></head><LINK rel='stylesheet' type='text/css' href='style.css'><body>";
		result+="<img align='left' src='http://www.blackbeltfactory.com/imgs/logos/BlackBeltFactory-logo-100x63.png'><br/><div class='searchLogo'><form><input type='text' value='keyword'/><input type='submit' value='search!'/></form></div><br/><br/><br/><br/>";
		
		
		result+="<div align='left'><h2><u>Results for :"+keyword+"</u></h2></div><br/>";
		for(String s : bigString){
			result+="<div class='search' align='left'>"+s+"</div>";
		}
		result+="</body></html>";
		
		
		File file = new File("S:\\SearchHtml\\searchJey.html");
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(result);
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
