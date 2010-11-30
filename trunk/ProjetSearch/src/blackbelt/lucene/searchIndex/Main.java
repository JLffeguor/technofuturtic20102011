package blackbelt.lucene.searchIndex;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;

public class Main {
	
	public static void main(String[] args){
		
		SearchInCours searchInCours = new SearchInCours();
		try {
			searchInCours.search("hibernate", "en");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
