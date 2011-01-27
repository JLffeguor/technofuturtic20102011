package blackbelt.lucene.updateIndex;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;

import blackbelt.lucene.IndexManager;
import blackbelt.lucene.SectionText;

public class Main {
	
	public static void main(String[] args){
		
		String id="172647220";
		
		SectionText sectionText=new SectionText();
		sectionText.setText("hibernate dao1");
		sectionText.setLanguage("EN");
		sectionText.setVersion(30);
		sectionText.setSectionid(Long.valueOf(id));
		
		try {
			IndexManager.addSectionText(sectionText);
			IndexManager.searchBySectionId(id, "EN");
			sectionText.setText("hibernate dao2");
			IndexManager.updateSectionTextById(id, sectionText);
			IndexManager.searchBySectionId(id, "EN");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
