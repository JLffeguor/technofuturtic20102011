package blackbelt.pdf;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import coursepage.CourseTextFormatter;

/** JE SERT A KOI ?????? */
public class PdfGenerator {
	
	private static final String DOCUMENT_RESULT = ("C:\\Users\\forma704\\Documents\\Julien\\blackBelt\\HtmlPage.pdf");
	private Document doc = new Document();
	private boolean isUse = false;

	
	public void generatePdf(Section rootSection) throws FileNotFoundException, DocumentException {
				// CA FAIT KOI??
		PdfWriter.getInstance(this.doc, new FileOutputStream(DOCUMENT_RESULT));
		
		if (!this.isUse){
			this.doc.open();
			recursiveWalk(rootSection);
			this.doc.close();
			this.isUse = true;
		} else{
			throw new IllegalStateException("Instances of this class are like condoms : not thread safe and not reusable. It should be thrown away after use. ;-)");
		}
	}


	/** Accumulates paragraphs for the sections (and its subsections) in this.doc */
	private void recursiveWalk(Section currentSection) throws DocumentException, FileNotFoundException{
		// Title
		this.doc.add(new Paragraph(currentSection.getTitle()));
		
		// Body
		CourseTextFormatter sectionFormatter = new CourseTextFormatter("",
				currentSection.getBody());
		this.doc.add(new Paragraph(sectionFormatter.format()));

		// Childs (recursive call)
		for (Section sSection : currentSection.getSubSections()) {
			recursiveWalk(sSection);
		}
	}
	
}
