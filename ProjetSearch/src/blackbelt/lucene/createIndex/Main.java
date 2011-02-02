package blackbelt.lucene.createIndex;

import java.io.IOException;

import org.apache.lucene.index.IndexWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import blackbelt.lucene.IndexManager;

public class Main {

	public static void main(String[] args) {

		// Construct a RAMDirectory to hold the in-memory representation
		// of the index.
		// RAMDirectory idx = new RAMDirectory();
		
		try {
			ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
			IndexManager indexManager=(IndexManager)applicationContext.getBean("indexManager");
			indexManager.createIndexes();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
