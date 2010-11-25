package blackbelt.lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Indexer {
	@Autowired
	CenterDao dao;
	
	public Document indexSection(Section section) throws IOException {

		//System.out.println("\t"+section);
		Document doc = new Document();
		doc.add(new Field("id", String.valueOf(section.getId()), Field.Store.YES, Field.Index.NO));
        doc.add(new Field("text", section.getText(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("language", section.getLanguage(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("version", String.valueOf(section.getVersion()), Field.Store.YES, Field.Index.ANALYZED));
        String fullSearchableText = section.getText() + " " + section.getLanguage() + " " + section.getVersion();
        doc.add(new Field("content", fullSearchableText, Field.Store.YES, Field.Index.ANALYZED));
		return doc;
	}

	public void createIndexes(Directory directory) throws IOException {
		System.out.println("*****************Begin Indexing hotel*****************");

		Set<String> stopWords=new HashSet<String>();
		
		// Make an writer to create the index
		IndexWriter writer = new IndexWriter(directory, new StandardAnalyzer(Version.LUCENE_30,stopWords), true, IndexWriter.MaxFieldLength.UNLIMITED);
		
		//Index all Accommodation entries
		//Hotel[] hotels = HotelDatabase.getHotels();
		//List<Section> sections=dao.myQuerry("SELECT s FROM Section s");
		
		List<Section> sections=dao.myQuerry("select s1 from Section s1 where s1.version=(" +
				"select max(s2.version) from Section s2 where s2.sectionid=s1.sectionid and s2.language=s1.language " +
				"group by s2.sectionid)" +
				"order by s1.sectionid");
		
		int i=0;
		for (Section section : sections) {
			i++;
			writer.addDocument(indexSection(section));
			System.out.println("\t("+i+") "+section);
		}
		
		// Optimize and close the writer to finish building the index
        writer.optimize();
        writer.close();
        
        System.out.println("*****************End Indexing hotel*****************");
	}
	
	public List myQuerry(String querry){
		return dao.myQuerry(querry);
	}
}
