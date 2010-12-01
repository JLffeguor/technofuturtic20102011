package blackbelt.lucene.searchIndex;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import blackbelt.PathIndex;
import blackbelt.SectionTextDocument;
import blackbelt.lucene.createIndex.SectionText;

public class UpdateIndex implements PathIndex{
	public static void update(SectionText sectionText) {
		deleteSectionIndex(String.valueOf(sectionText.getSectionid()),sectionText.getLanguage());
		addSectionIndex(sectionText);
	}

	private static void deleteSectionIndex(String sectionId, String language) {

		IndexWriter writer = null;
		try {
			writer = new IndexWriter(new SimpleFSDirectory(new File("index")),
					new StandardAnalyzer(Version.LUCENE_30, STOPWORD),
					IndexWriter.MaxFieldLength.UNLIMITED);

			// writer.deleteDocuments(new Term("sectionid", ID),new
			// Term("language",language));

			String queryString = "sectionid:" + sectionId + " AND language:"
					+ language;

			QueryParser parser = new QueryParser(Version.LUCENE_30,
					"sectionid", new StandardAnalyzer(Version.LUCENE_30,STOPWORD));
			Query query = parser.parse(queryString);

			writer.deleteDocuments(query);

			if (writer != null)
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

	private static void addSectionIndex(SectionText sectionText) {

		IndexWriter writer = null;
		try {
			writer = new IndexWriter(new SimpleFSDirectory(new File("index")),
					new StandardAnalyzer(Version.LUCENE_30, STOPWORD),
					IndexWriter.MaxFieldLength.UNLIMITED);

			writer.addDocument(new SectionTextDocument(sectionText).getDoc());

			if (writer != null)
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
