package blackbelt.lucene.searchIndex;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;

import blackbelt.lucene.IndexManager;
import blackbelt.lucene.IndexManager.CourseSearchResult;

public class Main {
	
	public static void main(String[] args){
		
		try {
			List<CourseSearchResult> result = new IndexManager().searchByKeyWordAndLanguage("jpa hibernate", "EN");
			for(CourseSearchResult courseSearchResult : result){
				System.out.println(courseSearchResult);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

}
