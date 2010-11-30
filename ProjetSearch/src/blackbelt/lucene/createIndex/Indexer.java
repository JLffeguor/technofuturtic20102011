package blackbelt.lucene.createIndex;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.PathIndex;
import blackbelt.parserSection.CourseTextFormatter;

@Service
@Transactional
public class Indexer {
	@Autowired
	private SectionDao dao;
	
	public Document indexSection(SectionText section) throws IOException {
		
		//Use a text formatter to format the text
		CourseTextFormatter courseTextFormatter = new CourseTextFormatter(null, section.getText());
		String text = courseTextFormatter.format();
		//Remove all the balises who stay after the format
		text = cleanHtmlBalises(text);
		
		//Add a new Document to the index
		Document doc = new Document();
		//And add each field
		doc.add(new Field("id", String.valueOf(section.getId()), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("sectionid", String.valueOf(section.getSectionid()), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("language", section.getLanguage(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("version", String.valueOf(section.getVersion()), Field.Store.YES, Field.Index.ANALYZED));
        String fullSearchableText = text + " " + section.getLanguage() + " " + section.getVersion();
        doc.add(new Field("content", fullSearchableText, Field.Store.YES, Field.Index.ANALYZED));
        
        //Print (use it for debug)
        //System.out.println(doc.toString());
		return doc;
	}

	public void createIndexes() throws IOException {
		
		SimpleFSDirectory indexDirectory = new SimpleFSDirectory(new File(PathIndex.DIRECTORY));
		
		System.out.println("*****************Begin Indexing Section*****************");
		
		/** 
		 * The keyword "IT" is use for the language (Italian). But the default stopWords of Lucene doesn't accept this keyword.
		 * So we have to create another stopWords.
		 * TODO : add stopWords
		 *  */
		Set<String> stopWords =new java.util.HashSet<String>(); 
		
		// Make an writer to create the index
		IndexWriter writer = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_30,stopWords), true, IndexWriter.MaxFieldLength.UNLIMITED);
		
		//Index all Accommodation entries		
		List<SectionText> sections=dao.myQuerry("select s1 from Section s1 where s1.version=(" +
				"select max(s2.version) from Section s2 where s2.sectionid=s1.sectionid and s2.language=s1.language " +
				"group by s2.sectionid)" +
				"order by s1.sectionid");
		
		//Print (use for debug)
		int i=0;
		for (SectionText section : sections) {
			i++;
			writer.addDocument(indexSection(section));
			System.out.println("\t("+i+") "+section);
		}
		
		// Optimize and close the writer to finish building the index
        writer.optimize();
        writer.close();
        
        System.out.println("*****************End Indexing Section*****************");
	}
	
	/** 
	 * Some user who write course on BlackBelt write some balises. Some balises are not delete with the CourseTextFormatter
	 * So we have to clean those balises with this method.
	 * */
	private String cleanHtmlBalises(String textToFormat){
		String result = "";
		
		result = textToFormat.replaceAll("\\</.*?>","\n"); //remove all balises </?>
		//remove all other balises who are in the DB.
		result = result.replaceAll("(?i)\\<a","");
		result = result.replaceAll("(?i)href.*?>",""); //Remove <a href...>
		result = result.replaceAll("(?i)\\<p>","");
		result = result.replaceAll("(?i)\\<p.*?>",""); //Remove <p align...>
		result = result.replaceAll("(?i)\\<b.*?>","");
		result = result.replaceAll("(?i)\\<i>","");
		result = result.replaceAll("(?i)\\<pre>","");
		result = result.replaceAll("(?i)\\<ul>","");
		result = result.replaceAll("(?i)\\</ul>","");
		result = result.replaceAll("(?i)\\<ol>","");
		result = result.replaceAll("(?i)\\<li>","");
		result = result.replaceAll("(?i)\\</li>","");
		
		return result;
	}
}
