package blackbelt.lucene.searchIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;

import blackbelt.lucene.IndexManager;
import blackbelt.lucene.IndexManager.CourseSearchResult;

public class Main {
	
	public static void main(String[] args){
		String StartHtml = "<html><body>";
		String EndHtml = "</body></html>";
		String bigStringforHtmlPage ="";
		try {
			List<CourseSearchResult> result = new IndexManager().searchByKeyWordAndLanguage("jpa hibernate", "EN");
			for(CourseSearchResult courseSearchResult : result){
				System.out.println(courseSearchResult);
				bigStringforHtmlPage = bigStringforHtmlPage + courseSearchResult.getTitle() + courseSearchResult.getText();
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		File file = new File("C:\\Users\\forma702\\Desktop\\testHighlight.html");
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(StartHtml + bigStringforHtmlPage + EndHtml);
			pw.close();
		} catch (FileNotFoundException e) {
			
		}
	}

}
