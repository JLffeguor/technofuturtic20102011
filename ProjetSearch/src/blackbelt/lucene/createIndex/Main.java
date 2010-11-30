package blackbelt.lucene.createIndex;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {

		// Construct a RAMDirectory to hold the in-memory representation
		// of the index.
		// RAMDirectory idx = new RAMDirectory();

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Indexer indexer = (Indexer) applicationContext.getBean("indexer");
		
		try {
			indexer.createIndexes();			
			
		} catch (Exception e) {
			System.out.println("Exception caught.\n" + e);
		}
	}
}
