package blackbelt.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.PdfWriter;

import coursepage.CourseTextFormatter;

/** This class generate a PDF document */
public class PdfGenerator {
	
	private static final String DOCUMENT_RESULT = ("S:\\DocumentsPourPDF\\HtmlPage.pdf");
	private Document doc = new Document();
	private boolean isUse = false;

	
	public void generatePdf(Section rootSection) throws DocumentException, IOException {
		//Generate the PDF document
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


	/** Accumulates paragraphs for the sections (and its subsections) in this.doc 
	 * @throws IOException */
	private void recursiveWalk(Section currentSection) throws DocumentException, IOException{
		// Title
			//Style
		Font fontTitle = new Font(Font.getFamily(DOCUMENT_RESULT), 18, Font.BOLD);
		fontTitle.setSize(18);
		fontTitle.setColor(170, 0, 0);
			//Text and Style
		Chunk pTitle = new Chunk(currentSection.getTitle(), fontTitle);
			//Add Text
		this.doc.add(pTitle);
		
		// Body
		CourseTextFormatter sectionFormatter = new CourseTextFormatter("", currentSection.getBody());
		StyleSheet styleBody = new StyleSheet();
		styleBody.loadStyle("p", "size", "12px"); //Style of the body
		List<Element> objBody; //Create List with the paragraph
		objBody = HTMLWorker.parseToList(new StringReader(sectionFormatter.format()), styleBody); //Format text and apply style
			for (int i = 0; i < objBody.size(); i++){
				this.doc.add(objBody.get(i)); //Add paragraph
			}

		// Childs (recursive call)
		for (Section sSection : currentSection.getSubSections()) {
			recursiveWalk(sSection);
		}
	}
	
}
