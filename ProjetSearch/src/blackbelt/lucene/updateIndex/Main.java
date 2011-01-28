package blackbelt.lucene.updateIndex;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;

import blackbelt.lucene.IndexManager;
import blackbelt.lucene.SectionText;

public class Main {
	
	public static void main(String[] args){
		
		String id="172647220";
		
		SectionText sectionText=new SectionText();
		sectionText.setTitle("test");
		sectionText.setText("hibernate dao1");
		sectionText.setLanguage("EN");
		sectionText.setVersion(30);
		sectionText.setSectionid(Long.valueOf(id));
		
		try {
			IndexManager indexManager=new IndexManager();
			
			System.out.println("***************************** 1");
			indexManager.searchBySectionId(id, "EN");
			indexManager.addSectionText(sectionText);
			System.out.println("***************************** 2");
			indexManager.searchBySectionId(id, "EN");
			sectionText.setText("hibernate dao2");
			indexManager.updateSectionText(sectionText);
			System.out.println("***************************** 3");
			indexManager.searchBySectionId(id, "EN");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
