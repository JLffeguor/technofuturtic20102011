package blackbelt.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import blackbelt.parser.BlackBeltTagHandlerLuceneSearch;
import blackbelt.parser.BlackBeltTagParser;

public class SectionTextDocument {

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

	public Document createDocument(SectionText sectionText) {
		// Use a text formatter to format the text
		BlackBeltTagParser blackBeltTagParser=new BlackBeltTagParser(new BlackBeltTagHandlerLuceneSearch(), sectionText.getText());
		String text = blackBeltTagParser.parse();
		text = cleanHtmlBalises(text);

		// Add a new Document to the index
		Document sectionTextDocument = new Document();
		
		sectionTextDocument.add(new Field("id", String.valueOf(sectionText.getId()),Field.Store.YES, Field.Index.NOT_ANALYZED));
		// We need the sectionId in the search results page to link to the CoursePage.
		// TODO set Field.Index.NO
		sectionTextDocument.add(new Field("sectionId", String.valueOf(sectionText.getSectionid()),Field.Store.YES, Field.Index.NOT_ANALYZED));
		Field titleField=new Field("title", sectionText.getTitle(), Field.Store.YES, Field.Index.ANALYZED);
		titleField.setBoost(1.5f);
		sectionTextDocument.add(titleField);
		sectionTextDocument.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED));
		sectionTextDocument.add(new Field("language", sectionText.getLanguage(), Field.Store.YES,Field.Index.ANALYZED));

		return sectionTextDocument;
	}
}
