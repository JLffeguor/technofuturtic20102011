package blackbelt.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldSelector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.index.TermPositions;
import org.apache.lucene.index.TermVectorMapper;
import org.apache.lucene.index.IndexReader.FieldOption;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class UpdateIndex {
	public static void deleteSection(String ID,String language) {

		Set<String> stopWord = new HashSet<String>();
		
		IndexWriter writer=null;
		try {
			writer = new IndexWriter(new SimpleFSDirectory(new File("index")), new StandardAnalyzer(Version.LUCENE_30,stopWord), IndexWriter.MaxFieldLength.UNLIMITED);
			
			//writer.deleteDocuments(new Term("sectionid", ID),new Term("language",language));
			
			String queryString="sectionid:"+ ID + " AND language:" +language;
			
			QueryParser parser = new QueryParser(Version.LUCENE_30, "sectionid",new StandardAnalyzer(Version.LUCENE_30, stopWord));
			Query query = parser.parse(queryString);
			
			writer.deleteDocuments(query);

			if(writer!=null)
				writer.close(); 
			
		} catch (CorruptIndexException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
