package blackbelt.lucene.testHighlight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.util.Version;

public class MainHighlight {

	public static void main(String[] args) throws ParseException, IOException{
		
		String keyWord = "hibernate";
		String language = "en";
		String text = "Hibernate is an object-relational mapping (ORM) library for the Java language,"
			+ "providing a framework for mapping an object-oriented domain model to a traditional relational"
			+ "database. Hibernate solves object-relational impedance mismatch problems by replacing direct "
			+ "persistence-related database accesses with high-level object handling functions. "
			+ "Hibernate is free software that is distributed under the GNU Lesser General Public License. "
			+ "Hibernate's primary feature is mapping from Java classes to database tables " +
			"(and from Java data types to SQL data types). Hibernate also provides data query" +
			" and retrieval facilities. Hibernate generates the SQL calls and attempts to relieve" +
			" the developer from manual result set handling and object conversion and keep the application" +
			" portable to all supported SQL databases with little performance overhead.";
		String result;

		
		QueryParser parser = new QueryParser(Version.LUCENE_30, "title",new StandardAnalyzer(Version.LUCENE_30));
		Query query = parser.parse(keyWord);
		
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b>","</b>");
		TokenStream tokens = new StandardAnalyzer(Version.LUCENE_30).tokenStream("title",new StringReader(text));
		
		QueryScorer scorer = new QueryScorer(query,"title");
		Highlighter highlighter = new Highlighter(formatter,scorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer,85));
		
		try {
			result =highlighter.getBestFragments(tokens,text,4 ,"<BR/>...");
			System.out.println(result);
			System.out.println("\n" + result.length());
		} catch (InvalidTokenOffsetsException e) {
		throw new RuntimeException(e);
		
		}
		result = "<html><body>" + result + "</body></html>";
		File file = new File("C:\\Users\\forma702\\Desktop\\testHighlight.html");
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(result);
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
