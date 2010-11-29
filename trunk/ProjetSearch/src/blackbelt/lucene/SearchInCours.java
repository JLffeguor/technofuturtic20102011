package blackbelt.lucene;

import java.io.File;
import java.io.IOException;
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

public class SearchInCours implements PathIndex{
	public void search(String keyWord,String language) throws ParseException, IOException {

		try {
			String queryString=keyWord + " AND language:" +language;
			
			Searcher searcher = new IndexSearcher(new SimpleFSDirectory(new File(DIRECTORY)));

			// Build a Query object
			
			Set<String> stopWords =new java.util.HashSet<String>(); 

			QueryParser parser = new QueryParser(Version.LUCENE_30, "text",new StandardAnalyzer(Version.LUCENE_30, stopWords));
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
					System.out.println("docId: " + docId + "\t" + "docScore: "
							+ docScore);

					Document doc = searcher.doc(docId);

					// Print the value that we stored in the "title" field. Note
					// that this Field was not indexed, but (unlike the
					// "contents" field) was stored verbatim and can be
					// retrieved.
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
