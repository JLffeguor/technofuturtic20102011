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
			SimpleFSDirectory indexDirectory = new SimpleFSDirectory(new File("index"));

			// Make an writer to create the index
			indexer.createIndexes(indexDirectory);

			// Build an IndexSearcher using the in-memory index
			Searcher searcher = new IndexSearcher(indexDirectory);

			// Run some queries
			search(searcher, "Hibernate AND language:en");

		} catch (Exception e) {
			System.out.println("Exception caught.\n" + e);
		}
	}

	private static void search(Searcher searcher, String queryString)
			throws ParseException, IOException {

		try {

			// Build a Query object
			Set<String> stopWords=new HashSet<String>();
			
			QueryParser parser = new QueryParser(Version.LUCENE_30, "text",new StandardAnalyzer(Version.LUCENE_30,stopWords));
			Query query = parser.parse(queryString);

			int hitsPerPage = 10;
			// Search for the query
			TopScoreDocCollector collector = TopScoreDocCollector.create(5 * hitsPerPage, false);
			searcher.search(query, collector);

			ScoreDoc[] hits = collector.topDocs().scoreDocs;

			int hitCount = collector.getTotalHits();
			System.out.println(hitCount + " total matching documents");

			// Examine the Hits object to see if there were any matches

			if (hitCount == 0) {
				System.out.println("No matches were found for \"" + queryString
						+ "\"");
			} else {
				System.out.println("Hits for \"" + queryString
						+ "\" were found in quotes by:");

				// Iterate over the Documents in the Hits object
				for (int i = 0; i < hits.length; i++) {
					ScoreDoc scoreDoc = hits[i];
					int docId = scoreDoc.doc;
					float docScore = scoreDoc.score;
					System.out.println("docId: " + docId + "\t" + "docScore: " + docScore);

					Document doc = searcher.doc(docId);

					// Print the value that we stored in the "title" field. Note
					// that this Field was not indexed, but (unlike the
					// "contents" field) was stored verbatim and can be
					// retrieved.
					// System.out.println("  " + (i + 1) + ". " +
					// doc.get("title"));
					// System.out.println("Content: " + doc.get("content"));
					System.out.println("Content N°" + (i + 1) + ": "
							+ doc.get("language") + " (" + doc.get("id") + ")");
				}
			}
			System.out.println();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
