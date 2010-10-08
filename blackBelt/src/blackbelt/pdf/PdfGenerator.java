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
import com.itextpdf.text.Phrase;
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
			//Style of the title
		Font fontTitle = new Font(Font.getFamily(DOCUMENT_RESULT), 18, Font.BOLD);
		int sizeTitle = findNumberOfTagHtmlTitle(currentSection.getTitle());
		switch(sizeTitle){
			case 1:
				fontTitle.setSize(20);
				break;
			case 3:
				fontTitle.setSize(17);
				break;
			case 5:
				fontTitle.setSize(12);
				break;
			default:
				fontTitle.setSize(10);
				break;
		}
		fontTitle.setColor(170, 0, 0);
			//Set the style to the title 
		Chunk pTitle = new Chunk(currentSection.getTitle(), fontTitle);
//			if(sizeTitle == 1){
//				pTitle.setUnderline(3.5f, -8f);
//			}
			//Add Text
		this.doc.add(pTitle);
		this.doc.add(Chunk.NEWLINE);
		this.doc.add(Chunk.NEWLINE);
		
		// Body
		CourseTextFormatter sectionFormatter = new CourseTextFormatter("", currentSection.getBody());
		StyleSheet styleBody = new StyleSheet();
		styleBody.loadStyle("p", "size", "8"); //Style of the body
		List<Element> objBody; //Create List with the paragraph
		objBody = HTMLWorker.parseToList(new StringReader(sectionFormatter.format()), styleBody); //Format text and apply style
			for (int i = 0; i < objBody.size(); i++){
				this.doc.add(objBody.get(i)); //Add paragraph
				this.doc.add(Chunk.NEWLINE);  //Add return to line
			}

		// Childs (recursive call)
		for (Section sSection : currentSection.getSubSections()) {
			recursiveWalk(sSection);
		}
	}
	
	public int findNumberOfTagHtmlTitle(String title) {
		return title.lastIndexOf(".");
	}
	
}
