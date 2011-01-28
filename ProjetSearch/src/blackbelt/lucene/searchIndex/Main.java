package blackbelt.lucene.searchIndex;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;

import blackbelt.lucene.IndexManager;

public class Main {
	
	public static void main(String[] args){
		
		try {
			new IndexManager().searchByKWandL("jpa hibernate", "EN");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

}
