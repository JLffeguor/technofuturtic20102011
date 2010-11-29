package blackbelt.lucene;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyLuceneTest {

	public static void main(String[] args) {

		// Construct a RAMDirectory to hold the in-memory representation
		// of the index.
		// RAMDirectory idx = new RAMDirectory();

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Indexer indexer = (Indexer) applicationContext.getBean("indexer");
		
		SearchInCours searchInCours = new SearchInCours();

		try {
			
			indexer.createIndexes();			
			searchInCours.search("hibernate AND language:fr");

		} catch (Exception e) {
			System.out.println("Exception caught.\n" + e);
		}
	}
}
