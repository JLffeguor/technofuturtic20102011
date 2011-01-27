package blackbelt.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import blackbelt.parserSection.CourseTextFormatter;

public class SectionTextDocument {

	/**
	 * Some user who write course on BlackBelt write some balises. Some balises
	 * are not delete with the CourseTextFormatter So we have to clean those
	 * balises with this method.
	 * */
	private static String cleanHtmlBalises(String textToFormat) {
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

	public static Document createDocument(SectionText sectionText) {
		// Use a text formatter to format the text
		CourseTextFormatter courseTextFormatter = new CourseTextFormatter(null,sectionText.getText());
		String text = courseTextFormatter.format();
		// Remove all the balises who stay after the format
		text = cleanHtmlBalises(text);

		// Add a new Document to the index
		Document sectionTextDocument = new Document();
		// And add each field
		sectionTextDocument.add(new Field("id", String.valueOf(sectionText.getId()),Field.Store.YES, Field.Index.ANALYZED));
		sectionTextDocument.add(new Field("sectionId", String.valueOf(sectionText.getSectionid()),Field.Store.YES, Field.Index.ANALYZED));
		sectionTextDocument.add(new Field("title", sectionText.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
		sectionTextDocument.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED));
		sectionTextDocument.add(new Field("language", sectionText.getLanguage(), Field.Store.YES,Field.Index.ANALYZED));
		//sectionTextDocument.add(new Field("version", String.valueOf(sectionText.getVersion()),Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
//		//String fullSearchableText = text + " " + sectionText.getLanguage() + " " + sectionText.getVersion();
//		String fullSearchableText = text;
//		sectionTextDocument.add(new Field("content", fullSearchableText, Field.Store.YES,Field.Index.ANALYZED));

		// Print (use it for debug)
		// System.out.println(doc.toString());
		return sectionTextDocument;
	}
}
