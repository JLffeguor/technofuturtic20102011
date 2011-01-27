package blackbelt.lucene.searchIndex;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;

import blackbelt.lucene.IndexManager;

public class Main {
	
	public static void main(String[] args){
		
		try {
			IndexManager.searchByKWandL("hibernate", "en");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
