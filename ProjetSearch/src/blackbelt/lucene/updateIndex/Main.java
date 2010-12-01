package blackbelt.lucene.updateIndex;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;

import blackbelt.lucene.SectionText;
import blackbelt.lucene.searchIndex.SearchInCours;

public class Main {
	
	public static void main(String[] args){
		
		SearchInCours searchInCours = new SearchInCours();
		
		SectionText sectionText=new SectionText();
		sectionText.setText("hibernate dao");
		sectionText.setLanguage("EN");
		sectionText.setVersion(30);
		sectionText.setSectionid(172647220);
		
		try {
			searchInCours.searchBySectionId("12647220", "EN");
			UpdateIndex.update(sectionText);
			searchInCours.searchBySectionId("12647220", "EN");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
