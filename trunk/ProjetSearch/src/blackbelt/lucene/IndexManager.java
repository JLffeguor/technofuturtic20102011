package blackbelt.lucene;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
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

import blackbelt.lucene.spring.IndexerService;

public class IndexManager {
	public final String DIRECTORY="index";
	public final Set<String> STOPWORD=new HashSet<String>();
	private SectionTextDocument sectionTextDocument = new SectionTextDocument();
	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	private IndexerService indexerService;

	public IndexManager() {
		indexerService = (IndexerService) applicationContext.getBean("indexerService");
	}

	public void createIndexes() throws IOException, CorruptIndexException {

		SimpleFSDirectory indexDirectory = new SimpleFSDirectory(new File(DIRECTORY));

		System.out.println("*****************Begin Indexing Section*****************");

		/** 
		 * The keyword "IT" is use for the language (Italian). But the default stopWords of Lucene doesn't accept this keyword.
		 * So we have to create another stopWords.
		 * TODO : add stopWords
		 *  */

		// Make an writer to create the index
		IndexWriter writer = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_30,STOPWORD), true, IndexWriter.MaxFieldLength.UNLIMITED);

		//Index all Accommodation entries		
		List<SectionText> sections=indexerService.getLastVersionOfEachSectionTexts();

		//Print (use for debug)
		int i=0;
		for (SectionText section : sections) {
			i++;
			System.out.println("\t("+i+") "+section);
			writer.addDocument(sectionTextDocument.createDocument(section));
		}

		// Optimize and close the writer to finish building the index
		writer.optimize();
		writer.close();

		System.out.println("*****************End Indexing Section*****************");
	}
	
	public void updateSectionText(SectionText sectionText) throws IOException, CorruptIndexException {
		IndexWriter writer = getIndexWriter();
		writer.updateDocument(new Term("id", String.valueOf(sectionText.getId())), sectionTextDocument.createDocument(sectionText));
		writer.close();
	}

	public void deleteSectionTextById(String id) throws IOException, CorruptIndexException {
		IndexWriter writer = getIndexWriter();
		writer.deleteDocuments(new Term("id", id));
		writer.close();
	} 

	public void addSectionText(SectionText sectionText) throws IOException, CorruptIndexException {
		IndexWriter writer = getIndexWriter();
		writer.addDocument(sectionTextDocument.createDocument(sectionText));
		writer.close();
	}

	private IndexWriter getIndexWriter() throws IOException, CorruptIndexException {
		SimpleFSDirectory indexDirectory = new SimpleFSDirectory(new File(DIRECTORY));
		StandardAnalyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_30,STOPWORD);
		return new IndexWriter(indexDirectory, standardAnalyzer, IndexWriter.MaxFieldLength.UNLIMITED);
	}

	public void searchByKWandL(String keyWord,String language) throws ParseException, IOException {

		String queryString="(" + keyWord + ") AND language:" + language;

		Searcher searcher = new IndexSearcher(new SimpleFSDirectory(new File(DIRECTORY)));

		// Build a Query object
		MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30, new String[]{"title", "text"}, new StandardAnalyzer(Version.LUCENE_30, STOPWORD));
		Query query = parser.parse(queryString);

		int hitsPerPage = 10;
		// Search for the query
		// TODO review the number of display per page 
		TopScoreDocCollector collector = TopScoreDocCollector.create(5 * hitsPerPage, false);
		searcher.search(query, collector);

		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		int hitCount = collector.getTotalHits();
		System.out.println(hitCount + " total matching documents");

		// Examine the Hits object to see if there were any matches

		if (hitCount == 0) {
			System.out.println("No matches were found for \"" + query
					+ "\"");
		} else {
			System.out.println("Hits for \"" + query
					+ "\" were found in quotes by:");

			// Iterate over the Documents in the Hits object
			//TODO review render result
			//List<String> bigString = new ArrayList<String>();
			//RenderResult rr = new RenderResult(keyWord);
			for (int i = 0; i < hits.length; i++) {
				ScoreDoc scoreDoc = hits[i];
				int docId = scoreDoc.doc;
				float docScore = scoreDoc.score;
				System.out.println("docId: " + docId + "\t" + "docScore: "
						+ docScore);

				Document doc = searcher.doc(docId);
				//bigString.add(rr.extractResult(doc));
				// Print the value that we stored in the "title" field. Note
				// that this Field was not indexed, but (unlike the
				// "contents" field) was stored verbatim and can be
				// retrieved.
				System.out.println("Content N°" + (i + 1) + ": "
						+ doc.get("language") + " (" + doc.get("id") + ")");
			}
			//rr.toHTML(bigString);
		}
	}

	public void searchBySectionId(String sectionId,String language) throws ParseException, IOException {

		String queryString=sectionId + " AND language:" +language;

		Searcher searcher = new IndexSearcher(new SimpleFSDirectory(new File(DIRECTORY)));

		// Build a Query object
		QueryParser parser = new QueryParser(Version.LUCENE_30, "sectionId",new StandardAnalyzer(Version.LUCENE_30, STOPWORD));
		Query query = parser.parse(queryString);
		System.out.println(query);

		int hitsPerPage = 10;
		// Search for the query
		TopScoreDocCollector collector = TopScoreDocCollector.create(5 * hitsPerPage, false);
		searcher.search(query, collector);

		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		int hitCount = collector.getTotalHits();
		System.out.println(hitCount + " total matching documents");

		// Examine the Hits object to see if there were any matches

		if (hitCount == 0) {
			System.out.println("No matches were found for \"" + query
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
						+ doc.get("language") + " (" + doc.get("id") + ") " + doc.get("text"));
			}
		}
	}
}
