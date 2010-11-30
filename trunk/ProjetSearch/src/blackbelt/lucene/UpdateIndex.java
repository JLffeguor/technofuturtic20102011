package blackbelt.lucene;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class UpdateIndex {
	public static void deleteSection(String ID) {

		Set<String> stopWord = new HashSet<String>();
		
		IndexWriter writer=null;
		try {
			writer = new IndexWriter(new SimpleFSDirectory(new File("index")), new StandardAnalyzer(Version.LUCENE_30,stopWord), IndexWriter.MaxFieldLength.UNLIMITED);
			
			writer.deleteDocuments(new Term("id", ID));

			if(writer!=null)
				writer.close(); 
			
		} catch (CorruptIndexException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
