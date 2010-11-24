package blackbelt.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import coursepage.CourseTextFormatter;

/** This class generate a PDF document */
public class PdfGenerator {
	
	private Document doc = new Document();
	private boolean hasBeenUsed = false;
	private PdfWriter pWriter;
	private PdfContentByte pdfCb;
	private ColumnText columnText;
	private int numPage = 1;
	private Image imgLogo;
	private int sizeTitle = 0;

	/** generate the PDF to the given OutputSteam */
	public void generatePdf(Section rootSection, OutputStream outputStream) throws DocumentException, IOException {
		if (this.hasBeenUsed){
			throw new IllegalStateException("Instances of this class are like condoms : not thread safe and not reusable. It should be thrown away after use. ;-)");
		}
		hasBeenUsed = true;

		
		// Bind the document and the outputSteam together.
		this.pWriter = PdfWriter.getInstance(this.doc, outputStream);
		generateImageLogo();

		this.doc.open();

		//Create the column to avoid orphan
		this.pdfCb = pWriter.getDirectContent();
		this.columnText = new ColumnText(this.pdfCb);
		this.columnText.setSimpleColumn(36, 36, PageSize.A4.getWidth() - 36, PageSize.A4.getHeight() - 36, 18, Element.ALIGN_LEFT);
		this.columnText.setLeading(0, 1.5f);
//		this.statut = ColumnText.START_COLUMN;

		//Create Logo
		this.doc.add(this.imgLogo); //Add Logo

		//Create first footer
		createFooter();
		
		//Write each section to the PDF document
		recursiveWalk(rootSection);

		this.doc.close();
		this.hasBeenUsed = true;
	}


	/** Accumulates paragraphs for the sections (and its subsections) in this.doc 
	 * @throws IOException */
	private void recursiveWalk(Section currentSection) throws DocumentException, IOException{
		
		Chunk pTitle = createTitleChunk(currentSection); // Create Title Style
		addTitle(pTitle); //Add Title
		addBody(currentSection); //Add Body

		// Childs (recursive call)
		for (Section sSection : currentSection.getSubSections()) {
			this.columnText.addElement(new Paragraph("\n"));
			this.columnText.go();
			recursiveWalk(sSection);
		}
	}


	private void addBody(Section currentSection) throws IOException,
			DocumentException {
		
		String body = currentSection.getBody();
		
		CourseTextFormatter sectionFormatter = new CourseTextFormatter("", body);
		StyleSheet styleBody = new StyleSheet();
		styleBody.loadTagStyle("ul", "i", "");
		styleBody.loadTagStyle("ul", "indent", "12");
//		styleBody.loadTagStyle("ul", "align", "center");
		List<Element> bodyElements; //Create List with the paragraph
		bodyElements = HTMLWorker.parseToList(new StringReader(sectionFormatter.format()), styleBody); //Format text and apply style
		
		int status = ColumnText.START_COLUMN;
		float position;
		
		for (Element element : bodyElements){
			
			boolean pageOverflow = simulateAddElement(columnText, element);

			if (! pageOverflow // All the text fits the page until now. 
					||  columnText.getLinesWritten() > 4 // Or (too much text for the page, but) enough lines have been written at the bottom of the page.
			){

				// We write that text for real on the current page.
				columnText.addElement(element);
				columnText.go(false);

				if (pageOverflow) {  // We need to print the rest on the next page.
					newPage(doc, columnText);
					// we don't need to add the element again after the go of the previous page, because elements causing overflow are not removed from the ColumnText.compositeElements list. It's still hanging there.
					columnText.go(false);
				}

			} else {  // We want a new page before we print any part of the element.		
				newPage(doc, columnText);

				columnText.addElement(element);
				columnText.go(false);
			}

		}
	}
	
	/** Returns true if adding the element would overflow the page, and rolls back everything */
	private static boolean simulateAddElement(ColumnText columnText, Element element)
	throws DocumentException {
		int status;
		float position;
		// Simulation start
		position = columnText.getYLine();
		columnText.addElement(element);
		status = columnText.go(true);
		clearBuffer(columnText);
		columnText.setYLine(position); // come back as before the simulation.

		return ColumnText.hasMoreText(status);
	}
	
	/** Removes any element since the last real (non simulated) go **/
	private static void clearBuffer(ColumnText columnText) {
		columnText.setText(null);  // Removes any pending element (else it would be half rendered, only the part that would not fit the previous page would be rendered).
	}

	private void addTitle(Chunk pTitle) throws DocumentException {
		this.columnText.addElement(new Paragraph(pTitle));
		this.columnText.addElement(new Paragraph("\n"));
		this.columnText.go();
	}


	private Chunk createTitleChunk(Section currentSection) {
		Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
//		this.sizeTitle = findNumberOfTagHtmlTitle(currentSection.getTitle());
		this.sizeTitle = findNumberOfTagHtmlTitle(currentSection);
		switch(this.sizeTitle){
			case 0:
				fontTitle.setSize(20);
				break;
			case 1:
				fontTitle.setSize(17);
				break;
			case 2:
				fontTitle.setSize(12);
				break;
			default:
				fontTitle.setSize(10);
				break;
		}
		fontTitle.setColor(170, 0, 0);
	     
		Chunk pTitle = new Chunk(currentSection.getTitle(), fontTitle);
		return pTitle;
	}
	
//	private int findNumberOfTagHtmlTitle(String title) {
//		return StringUtils.countMatches(title, "."); //Count number of "."
//	}
	
	private int findNumberOfTagHtmlTitle(Section section){
		int result = 0;
		
		while(section.getParent() != null){
			section = section.getParent();
			result++;
		}
		
		return result;
	}
	
	public void createFooter(){
		Phrase p = new Phrase();
		p.add("Page : " + this.numPage);
		ColumnText.showTextAligned(this.pdfCb, Element.ALIGN_CENTER, p, (this.doc.right()- this.doc.left())/2+this.doc.leftMargin(), this.doc.bottom() - 10, 0);
		this.numPage++;
	}
	
	public void generateImageLogo() throws BadElementException, MalformedURLException, IOException{
		this.imgLogo = Image.getInstance("http://antisosial.free.fr/projet/BlackBeltFactoryLogo3D-header.png");
		this.imgLogo.setAbsolutePosition(555, 800);
//		this.imgLogo.setWidthPercentage(5); //To modify with the %
		
		/**Modify the pixel size*/
		this.imgLogo.scaleAbsoluteHeight(30);
		this.imgLogo.scaleAbsoluteWidth(30);

	}
	
	private void newPage(Document doc, ColumnText columnText) throws DocumentException {
		doc.newPage();
		createFooter();
		doc.add(this.imgLogo);
		columnText.setYLine(PageSize.A4.getHeight() - 36);
	}
}