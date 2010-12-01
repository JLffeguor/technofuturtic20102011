package blackbelt.lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import blackbelt.parserSection.CourseTextFormatter;

public class SectionTextDocument {
	private Document sectionTextDocument;
	
	
	public SectionTextDocument(SectionText section) throws IOException {

		// Use a text formatter to format the text
		CourseTextFormatter courseTextFormatter = new CourseTextFormatter(null,section.getText());
		String text = courseTextFormatter.format();
		// Remove all the balises who stay after the format
		text = cleanHtmlBalises(text);

		// Add a new Document to the index
		Document doc = new Document();
		// And add each field
		doc.add(new Field("id", String.valueOf(section.getId()),Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("sectionid", String.valueOf(section.getSectionid()),Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("language", section.getLanguage(), Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field("version", String.valueOf(section.getVersion()),Field.Store.YES, Field.Index.ANALYZED));
		String fullSearchableText = text + " " + section.getLanguage() + " " + section.getVersion();
		doc.add(new Field("content", fullSearchableText, Field.Store.YES,Field.Index.ANALYZED));

		// Print (use it for debug)
		// System.out.println(doc.toString());
		sectionTextDocument=doc;
	}

	/**
	 * Some user who write course on BlackBelt write some balises. Some balises
	 * are not delete with the CourseTextFormatter So we have to clean those
	 * balises with this method.
	 * */
	private String cleanHtmlBalises(String textToFormat) {
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

	public Document getDoc() {
		return sectionTextDocument;
	}
}
