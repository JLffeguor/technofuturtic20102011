package blackbelt.lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.lucene.spring.SectionDao;
import blackbelt.parser.BlackBeltTagHandlerLuceneSearch;
import blackbelt.parser.BlackBeltTagParser;

@Service
public class IndexManager {
	
	@Autowired	SectionDao sectionDao;
	
	/**
	 * Directory of the index Lucene.
	 *  TODO It may be removed at the integration in BBF.
	 */
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
	
	/**
	 *Update Document is a method in Lucene to update the index
	 *It take a Term and a document as param 
	 *A Term represents a word from text.  This is the unit of search.  
	 *It is composed of two elements,the text of the word, as a string,
	 * and the name of the field that the text occurred in, an interned string.*/
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

	public List<CourseSearchResult> searchByKeyWordAndLanguage(String keyWord, String language) throws ParseException, IOException {

		String queryString="(" + keyWord + ") AND language:" + language;

		Searcher searcher = new IndexSearcher(new SimpleFSDirectory(new File(DIRECTORY)));

		// Build a Query object
		MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30, 
				new String[]{"title", "text"}, new StandardAnalyzer(Version.LUCENE_30, STOP_WORDS));
		Query query = parser.parse(queryString);
		
		//We use Highlight lucene library for display the result
		
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b>","</b>");
		QueryScorer scorer = new QueryScorer(query,"title");
		Highlighter highlighter = new Highlighter(formatter,scorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer,45));

		int hitsPerPage = 50;
		// Search for the query
		// TODO review the number of display per page 
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, false);
		searcher.search(query, collector);
		
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		int hitCount = collector.getTotalHits();
		List<CourseSearchResult> listResultDocuments = new ArrayList<CourseSearchResult>();

		// Examine the Hits object to see if there were any matches
		if (hitCount == 0) {
			System.out.println("No matches were found for \"" + query
					+ "\"");
		} else {
			System.out.println("Hits for \"" + query
					+ "\" were found in quotes by:");

			// Iterate over the Documents in the Hits object
			//TODO review render result
			//
			//RenderResult rr = new RenderResult(keyWord);
			List<String> bigString = new ArrayList<String>();
			
			
			for (int i = 0; i < hits.length; i++) {
				ScoreDoc scoreDoc = hits[i];
				int docId = scoreDoc.doc;
				String title ;
				
				listResultDocuments.add(new CourseSearchResult(scoreDoc.score,
						searcher.doc(docId),highlighter));
			}
		}
		
		return listResultDocuments;
	}

	/**
	 * Some user who write course on BlackBelt write some tags. Some tags
	 * are not delete with the BlackBeltTagHangdlerLuceneSearch So we have to clean those
	 * tags with this method.
	 * */
	private String cleanHtmlTags(String textToFormat) {
		String result = "";
		// remove all end tags
		result = textToFormat.replaceAll("\\</.*?>", "\n");
		
		// remove all start tags
		result = result.replaceAll("(?i)\\<a", "");
		// Remove <a href...>
		result = result.replaceAll("(?i)href.*?>", ""); 
		result = result.replaceAll("(?i)\\<p>", "");
		// Remove <p align...>
		result = result.replaceAll("(?i)\\<p.*?>", ""); 
		result = result.replaceAll("(?i)\\<b.*?>", "");
		result = result.replaceAll("(?i)\\<i>", "");
		result = result.replaceAll("(?i)\\<pre>", "");
		result = result.replaceAll("(?i)\\<ul>", "");
		result = result.replaceAll("(?i)\\</ul>", "");  // BECAUSE................
		result = result.replaceAll("(?i)\\<ol>", "");
		result = result.replaceAll("(?i)\\<li>", "");
		result = result.replaceAll("(?i)\\</li>", "");

		return result;
	}

	private Document createDocument(SectionText sectionText) {
		// use BlackBeltTagHandlerLuceneSearch to escape the BlackBelt tag from sectiontext
		BlackBeltTagParser blackBeltTagParser=new BlackBeltTagParser(new BlackBeltTagHandlerLuceneSearch(), sectionText.getText());
		String text = blackBeltTagParser.parse();
		text = cleanHtmlTags(text);

		// Add a new Document to the index
		Document doc = new Document();
		
		doc.add(new Field("id", String.valueOf(sectionText.getId()),Store.YES, Index.NOT_ANALYZED));
		// We need the sectionId in the search results page to link to the CoursePage.
		// TODO set Field.Index.NO
		doc.add(new Field("sectionId", String.valueOf(sectionText.getSectionid()),Field.Store.YES, Field.Index.NOT_ANALYZED));
		Field titleField=new Field("title", sectionText.getTitle(), Field.Store.YES, Field.Index.ANALYZED, TermVector.WITH_POSITIONS_OFFSETS);
		// This give more importance to title during the search
		// The user see the result from the title before the result from the text 
		titleField.setBoost(1.5f);
		doc.add(titleField);
		doc.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED, TermVector.WITH_POSITIONS_OFFSETS));
		doc.add(new Field("language", sectionText.getLanguage(), Field.Store.YES,Field.Index.ANALYZED));

		return doc;
	}
	
	/**
	 * This class contains all information of an item found during a search to display it later.
	 */
	public static class CourseSearchResult {
		private float score;
		private String sectionId;
		private String title;
		private String text;
		private String language;
		
		public CourseSearchResult(float score,Document doc, Highlighter highlighter){
			this.score=score;
			//add <b> </b> around the word search in the title
			TokenStream tokens = new StandardAnalyzer(Version.LUCENE_30).tokenStream("title",new StringReader(doc.get("title")));
			try {
				title = highlighter.getBestFragments(tokens,doc.get("title"),1 ,"<BR/>");
				if (title.isEmpty()){
				    title=doc.get("title");
				}
			}catch (InvalidTokenOffsetsException e){
				throw new RuntimeException(e);
			}catch (IOException e) {
				throw new RuntimeException(e);
			} 
//			title=doc.get("title");
			//add <b> </b> around the word search in the text
			tokens = new StandardAnalyzer(Version.LUCENE_30).tokenStream("text",new StringReader(doc.get("text")));
			try {
				text = highlighter.getBestFragments(tokens,doc.get("text"),4 ,"<BR/>...");
			}catch (InvalidTokenOffsetsException e){
				throw new RuntimeException(e);
			}catch (IOException e) {
				throw new RuntimeException(e);
			}
			sectionId=doc.get("sectionId");
			language=doc.get("language").toUpperCase();
			
		}

		public float getScore() {
			return score;
		}
		
		public String getTitle() {
			return title;
		}

		public String getText() {
			return text;
		}

		public String getSectionId() {
			return sectionId;
		}
		
		public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        @Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Score: " + score + " // title: " + title;
		}
	}
}
