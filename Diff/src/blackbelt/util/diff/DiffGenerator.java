package blackbelt.util.diff;

import java.util.LinkedList;

import blackbelt.util.diff.diff_match_patch.Diff;
import blackbelt.util.diff.diff_match_patch.Operation;

/**
 * Stateful object that takes 2 texts and generates a decorated version to
 * highlight their differences. It uses a google diff code: diff_match_patch
 * class
 * 
 * Use a new instance for each original/modified text pairs.
 * 
 * @Author Gaëtan Timmermans (Technofutur 2010)
 * 
 * */

public class DiffGenerator {

	private String textOriginal, textModified;
	private diff_match_patch googleDiffMatchPatch;
	private LinkedList // Cannot upcast to List because diff_match_patch class
						// takes LinkedList as param.
	<diff_match_patch.Diff> diffs;

	private enum TextType {
		ORIGINAL, MODIFIED
	}

	public DiffGenerator(String textOriginal, String textModified) {
		this.textOriginal = textOriginal;
		this.textModified = textModified;

		googleDiffMatchPatch = new diff_match_patch();
		diffs = googleDiffMatchPatch.diff_main(textOriginal, textModified);
		googleDiffMatchPatch.diff_cleanupSemantic(diffs);
	}

	public String getTextOriginalHtmlDiff(boolean escapeSourceHtml) {
		return createHtmlDiff(TextType.ORIGINAL, escapeSourceHtml);
	}
	public String getTextModifiedHtmlDiff(boolean escapeSourceHtml) {
		return createHtmlDiff(TextType.MODIFIED,escapeSourceHtml);
	}

	/**
	 * Does 2 things: 
	 * 1. Removes inserted or deleted text (according output param)
	 * 2. Escapes the html in the text if parameter "escapeSourceHtml" is true
	 *    and decorates inserted/deleted text with html.
	 * 
	 * @return text showing diffs with html tags to highlights 
	 * (original html tags are escaped if escapeSourceHtml is true)
	 */
	private String createHtmlDiff(TextType output, boolean escapeSourceHtml) {
		StringBuilder html = new StringBuilder();
		int i = 0;
		for (Diff aDiff : diffs) {
			String text;
			if (escapeSourceHtml)
				//escape html tags
				text = aDiff.text.replace("&", "&amp;").replace("<", "&lt;")
						.replace(">", "&gt;").replace("\n", "&para;<BR>");
			else
				text = aDiff.text;

			if (aDiff.operation == Operation.DELETE
					&& output != TextType.MODIFIED) {
				html.append("<DEL STYLE=\"background:#FFE6E6;\" TITLE=\"i=")
						.append(i).append("\">").append(text).append("</DEL>");
			} else if (aDiff.operation == Operation.INSERT
					&& output != TextType.ORIGINAL) {
				html.append("<INS STYLE=\"background:#E6FFE6;\" TITLE=\"i=")
						.append(i).append("\">").append(text).append("</INS>");
			} else if (aDiff.operation == Operation.EQUAL) {
				html.append("<SPAN TITLE=\"i=").append(i).append("\">")
						.append(text).append("</SPAN>");
			}
			if (aDiff.operation != Operation.DELETE) {
				i += aDiff.text.length();
			}
		}
		return html.toString();
	}
}
