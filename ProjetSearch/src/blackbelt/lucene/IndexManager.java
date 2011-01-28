package blackbelt.lucene;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.lucene.spring.SectionDao;
import blackbelt.parser.BlackBeltTagHandlerLuceneSearch;
import blackbelt.parser.BlackBeltTagParser;

@Service
public class IndexManager {
	@Autowired
	SectionDao sectionDao;
	
	public final String DIRECTORY="index";
	
	/** The collection of stopwords is empty. 
	 * We cannot use the default stopwords fo Lucene, because they include "IT" which is used for the Italian language.
	 * => we use an empty stopword collection. 
	 * */
	public final Set<String> STOP_WORDS = new HashSet<String>();

	public void createIndexes() throws IOException, CorruptIndexException {

		SimpleFSDirectory indexDirectory = new SimpleFSDirectory(new File(DIRECTORY));

		// Make an writer to create the index
		IndexWriter writer = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_30, STOP_WORDS), true, IndexWriter.MaxFieldLength.UNLIMITED);

		//Index all Accommodation entries		
		List<SectionText> sectionTexts = sectionDao.findLastVersionOfEachSectionTexts();  // TODO: use DAO directly.
		for (SectionText sectionText : sectionTexts) {
			writer.addDocument(createDocument(sectionText));
		}

		// Optimize and close the writer to finish building the index
		writer.optimize();
		writer.close();
	}
	
	public void updateSectionText(SectionText sectionText) throws IOException, CorruptIndexException {
		IndexWriter writer = getIndexWriter();
		writer.updateDocument(new Term("id", String.valueOf(sectionText.getId())), createDocument(sectionText));
		writer.close();
	}

	public void deleteSectionTextById(String id) throws IOException, CorruptIndexException {
		IndexWriter writer = getIndexWriter();
		writer.deleteDocuments(new Term("id", id));
		writer.close();
	} 

	public void addSectionText(SectionText sectionText) throws IOException, CorruptIndexException {
		IndexWriter writer = getIndexWriter();
		writer.addDocument(createDocument(sectionText));
		writer.close();
	}

	private IndexWriter getIndexWriter() throws IOException, CorruptIndexException {
		SimpleFSDirectory indexDirectory = new SimpleFSDirectory(new File(DIRECTORY));
		StandardAnalyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_30,STOP_WORDS);
		return new IndexWriter(indexDirectory, standardAnalyzer, IndexWriter.MaxFieldLength.UNLIMITED);
	}

	public void searchByKeyWordAndLanguage(String keyWord, String language) throws ParseException, IOException {

		String queryString="(" + keyWord + ") AND language:" + language;

		Searcher searcher = new IndexSearcher(new SimpleFSDirectory(new File(DIRECTORY)));

		// Build a Query object
		MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30, new String[]{"title", "text"}, new StandardAnalyzer(Version.LUCENE_30, STOP_WORDS));
		Query query = parser.parse(queryString);

		int hitsPerPage = 50;
		// Search for the query
		// TODO review the number of display per page 
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, false);
		searcher.search(query, collector);

		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		int hitCount = collector.getTotalHits();

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
		QueryParser parser = new QueryParser(Version.LUCENE_30, "sectionId",new StandardAnalyzer(Version.LUCENE_30, STOP_WORDS));
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
	
	/**
	 * Some user who write course on BlackBelt write some balises. Some balises
	 * are not delete with the CourseTextFormatter So we have to clean those
	 * balises with this method.
	 * */
	private String cleanHtmlTags(String textToFormat) {
		String result = "";

		result = textToFormat.replaceAll("\\</.*?>", "\n"); // remove all
															// balises </?>
		// remove all other balises who are in the DB.
		result = result.replaceAll("(?i)\\<a", "");
		result = result.replaceAll("(?i)href.*?>", ""); // Remove <a href...>
		result = result.replaceAll("(?i)\\<p>", "");
		result = result.replaceAll("(?i)\\<p.*?>", ""); // Remove <p align...>
		result = result.replaceAll("(?i)\\<b.*?>", "");
		result = result.replaceAll("(?i)\\<i>", "");
		result = result.replaceAll("(?i)\\<pre>", "");
		result = result.replaceAll("(?i)\\<ul>", "");
		result = result.replaceAll("(?i)\\</ul>", "");
		result = result.replaceAll("(?i)\\<ol>", "");
		result = result.replaceAll("(?i)\\<li>", "");
		result = result.replaceAll("(?i)\\</li>", "");

		return result;
	}

	private Document createDocument(SectionText sectionText) {
		// Use a text formatter to format the text
		BlackBeltTagParser blackBeltTagParser=new BlackBeltTagParser(new BlackBeltTagHandlerLuceneSearch(), sectionText.getText());
		String text = blackBeltTagParser.parse();
		text = cleanHtmlTags(text);

		// Add a new Document to the index
		Document doc = new Document();
		
		doc.add(new Field("id", String.valueOf(sectionText.getId()),Field.Store.YES, Field.Index.NOT_ANALYZED));
		// We need the sectionId in the search results page to link to the CoursePage.
		// TODO set Field.Index.NO
		doc.add(new Field("sectionId", String.valueOf(sectionText.getSectionid()),Field.Store.YES, Field.Index.NOT_ANALYZED));
		Field titleField=new Field("title", sectionText.getTitle(), Field.Store.YES, Field.Index.ANALYZED);
		titleField.setBoost(1.5f);
		doc.add(titleField);
		doc.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("language", sectionText.getLanguage(), Field.Store.YES,Field.Index.ANALYZED));

		return doc;
	}
	
	public static class CourseSearchResult {
		//TODO
//		constructor (ScoreDoc ??)
//		
//		public String textFound;
//		public Section section;
//		public Language
	}
}
