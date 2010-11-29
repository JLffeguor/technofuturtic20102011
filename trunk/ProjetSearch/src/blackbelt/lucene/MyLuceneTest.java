package blackbelt.lucene;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyLuceneTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Construct a RAMDirectory to hold the in-memory representation
		// of the index.
		// RAMDirectory idx = new RAMDirectory();

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Indexer indexer = (Indexer) applicationContext.getBean("indexer");

		try {
			
			//indexer.createIndexes();			
			SearchInCours.search("hibernate AND language:fr");

		} catch (Exception e) {
			System.out.println("Exception caught.\n" + e);
		}
	}
}
