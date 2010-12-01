package blackbelt.lucene.createIndex;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.PathIndex;
import blackbelt.SectionTextDocument;

@Service
@Transactional
public class Indexer {
	@Autowired
	private SectionDao dao;

	public void createIndexes() throws IOException {
		
		SimpleFSDirectory indexDirectory = new SimpleFSDirectory(new File(PathIndex.DIRECTORY));
		
		System.out.println("*****************Begin Indexing Section*****************");
		
		/** 
		 * The keyword "IT" is use for the language (Italian). But the default stopWords of Lucene doesn't accept this keyword.
		 * So we have to create another stopWords.
		 * TODO : add stopWords
		 *  */
		
		// Make an writer to create the index
		IndexWriter writer = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_30,PathIndex.STOPWORD), true, IndexWriter.MaxFieldLength.UNLIMITED);
		
		//Index all Accommodation entries		
		List<SectionText> sections=dao.myQuerry("select s1 from SectionText s1 where s1.version=(" +
				"select max(s2.version) from SectionText s2 where s2.sectionid=s1.sectionid and s2.language=s1.language " +
				"group by s2.sectionid)" +
				"order by s1.sectionid");
		
		//Print (use for debug)
		int i=0;
		for (SectionText section : sections) {
			i++;
			writer.addDocument(new SectionTextDocument(section).getDoc());
			System.out.println("\t("+i+") "+section);
		}
		
		// Optimize and close the writer to finish building the index
        writer.optimize();
        writer.close();
        
        System.out.println("*****************End Indexing Section*****************");
	}
}
