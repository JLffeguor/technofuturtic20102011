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
		String keyWord="";
		if(args.length==0){
			keyWord="hibernate";
		}
		else{
			keyWord=args[1];
		}
		String language="en";

		try {
			
			indexer.createIndexes();			
			searchInCours.search(keyWord,language);

		} catch (Exception e) {
			System.out.println("Exception caught.\n" + e);
		}
	}
}
