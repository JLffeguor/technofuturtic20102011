package blackbelt.lucene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;

@Deprecated
public class RenderResult {
	private String keyword;
//	private Document doc;
	private final int MAX = 100;
	
	public RenderResult(String keyword){
//		this.doc=doc;
		this.keyword = keyword;
	}

	public void highlightResult(Document doc){
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b>","</b>");
		TokenStream tokens = new StandardAnalyzer(Version.LUCENE_30)
				.tokenStream(new String[]{"title", "text"},new Reader(doc.get("text")));
		Highlighter highlighter = new Highlighter();
	}
	public String extractResult(Document doc) {
		String courseUrl="http://www.blackbeltfactory.com/ui#CoursePage/";
		String textSectionFull = doc.get("text");
		textSectionFull = textSectionFull.toLowerCase();
		int indexDebKeyword = 0;
		List<String> keywordList = new ArrayList<String>();
		String result = "";
		String testKeyword = keyword;
		
		while(indexDebKeyword < keyword.length()){
			int indexBlankSpace = testKeyword.indexOf(" ");
			if (indexBlankSpace == -1){
				keywordList.add(testKeyword);
				break;
			}else{
			keywordList.add(testKeyword.substring(indexDebKeyword,indexBlankSpace));
			}
			testKeyword = testKeyword.substring(indexBlankSpace+1,testKeyword.length());
		}
		for(String str: keywordList){
			result += "...."+textAroundKeyword(doc, courseUrl, textSectionFull,str)+"....<br/>";
		}
		
		return  result +"<br/><a href='"+courseUrl+doc.get("sectionid")+"'>"+courseUrl+doc.get("sectionid")+"</a>"; 
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
		
		String firstResult = subStrBeforeKeyword + " <b>" + singleKeyword + "</b> " + subStrAfterKeyword ;
		return firstResult;
	}
	
	public void toHTML(List<String> bigString){
		String result = "";
		String courseUrl="http://www.blackbeltfactory.com/ui#CoursePage/";
		result+="<html><head><title>Black Belt Google</title><LINK rel='stylesheet' type='text/css' href='style.css'>";
		result+="</head><body>";
		result+="<img align='left' src='http://www.blackbeltfactory.com/imgs/logos/BlackBeltFactory-logo-100x63.png'/><br/><div class='searchLogo'><form style='vertical-align:middle' name='searching'><input name='keyword' type='text' value='keyword'/><input type='submit' value='search' onClick=\"return search()\"/></form></div><br/><br/><br/><br/>";
		
		
		result+="<div align='left'><h2><u>Results for :"+keyword+"</u></h2></div><br/>";
		for(String s : bigString){
			result+="<div class='search' align='left'>"+s+"</div>";
		}
		result+="</body></html>";
		
		
		File file = new File("C:\\testing\\Search\\test.html");
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
