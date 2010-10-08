package blackbelt.pdf;

import java.io.IOException;
import java.io.OutputStream;
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
	
	private Document doc = new Document();
	private boolean isUse = false;

	/** generate the pdf to the given OutputSteam */
	public void generatePdf(Section rootSection, OutputStream outputStream) throws DocumentException, IOException {
		// Bind the document and the outputSteam together.
		PdfWriter.getInstance(this.doc, outputStream);
		
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
		Chunk pTitle = createTitleChunk(currentSection);
		
		//Add Text
		addTitle(pTitle);
		
		// Body
		addBody(currentSection);

		// Childs (recursive call)
		for (Section sSection : currentSection.getSubSections()) {
			recursiveWalk(sSection);
		}
	}


	private void addBody(Section currentSection) throws IOException,
			DocumentException {
		CourseTextFormatter sectionFormatter = new CourseTextFormatter("", currentSection.getBody());
		StyleSheet styleBody = new StyleSheet();
		styleBody.loadStyle("p", "size", "8"); //Style of the body
		List<Element> bodyElements; //Create List with the paragraph
		bodyElements = HTMLWorker.parseToList(new StringReader(sectionFormatter.format()), styleBody); //Format text and apply style
		
		for (Element element : bodyElements){
			this.doc.add(element); //Add paragraph
			this.doc.add(Chunk.NEWLINE);  //Add return to line
		}
	}


	private void addTitle(Chunk pTitle) throws DocumentException {
		this.doc.add(pTitle);
		this.doc.add(Chunk.NEWLINE);
		this.doc.add(Chunk.NEWLINE);
	}


	private Chunk createTitleChunk(Section currentSection) {
		Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
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
		return pTitle;
	}
	
	public int findNumberOfTagHtmlTitle(String title) {
		return title.lastIndexOf(".");
	}
	
}
