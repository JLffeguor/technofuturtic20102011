package blackbelt.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.util.Version;

import blackbelt.lucene.spring.SpringUtil;

public class CourseSearchResultsManager{
	private ScoreDoc[] scoreDocs;
	private String keyWord;
	private String language;
	
	public int getTotalHits(){
		return scoreDocs.length;
	}
	
	public void search(String keyWord, String language){
		this.keyWord=keyWord;
		this.language=language;
		scoreDocs=SpringUtil.getBean().searchByKeyWordAndLanguage(keyWord, language);
	}
	
	public CourseSearchResult getResult(int index){
		ScoreDoc scoreDoc=scoreDocs[index];
		Document doc=SpringUtil.getBean().findDocument(scoreDoc);
		return new CourseSearchResult(scoreDoc.score, keyWord, doc);
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

		public CourseSearchResult(float score, String keyword, Document doc){
				this.score=score;
				sectionId=doc.get("sectionId");
				language=doc.get("language").toUpperCase();
				
				title=getHighlight(keyword, doc.get("title"));
				if(title.isEmpty()){
					title=doc.get("title");
				}
				text=getHighlight(keyword, doc.get("text"));

		}
		
		private String getHighlight(String keyword, String textToHighlight){

			try {
				QueryParser queryParser = new QueryParser(Version.LUCENE_30, "field", new StandardAnalyzer(Version.LUCENE_30, SpringUtil.getBean().STOP_WORDS));//(new Term("field", keyword));
				Query query = queryParser.parse(keyword);
				SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b>","</b>");
				QueryScorer scorer = new QueryScorer(query,"field");
				Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
				Highlighter highlighter = new Highlighter(formatter,scorer);
				highlighter.setTextFragmenter(fragmenter);
				TokenStream tokens = new StandardAnalyzer(Version.LUCENE_30).tokenStream("field",new StringReader(textToHighlight));
				return highlighter.getBestFragments(tokens, textToHighlight,4 ,"<BR/>...");
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (InvalidTokenOffsetsException e) {
				throw new RuntimeException(e);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
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
			return "Score: " + score + " // title: " + title;
		}
	}
}
