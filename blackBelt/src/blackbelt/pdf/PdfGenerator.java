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
	private ColumnText ct;
	private int numPage = 1;
	private Image imgLogo;
	private int status = 0;
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
		// TODO: add better comments (incl links to forum threads....)
		this.pdfCb = pWriter.getDirectContent();
		this.ct = new ColumnText(this.pdfCb);
		this.ct.setSimpleColumn(36, 36, PageSize.A4.getWidth() - 36, PageSize.A4.getHeight() - 36, 18, Element.ALIGN_LEFT);
		this.ct.setLeading(0, 1.5f);
//		this.statut = ColumnText.START_COLUMN;

		//Create Logo
		this.doc.add(this.imgLogo); //Add Logo

		//Write each section to the PDF document
		recursiveWalk(rootSection);

		//Create last footer
		createFooter();

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
			recursiveWalk(sSection);
		}
	}


	private void addBody(Section currentSection) throws IOException,
			DocumentException {
		
		String body = currentSection.getBody();
		
		CourseTextFormatter sectionFormatter = new CourseTextFormatter("", body);
		StyleSheet styleBody = new StyleSheet();
		styleBody.loadStyle("p", "size", "8"); //Style of the body
		List<Element> bodyElements; //Create List with the paragraph
		bodyElements = HTMLWorker.parseToList(new StringReader(sectionFormatter.format()), styleBody); //Format text and apply style

		for (Element element : bodyElements){
			
//			float beforeYLine = this.ct.getYLine();
			this.ct.addElement(element);
			this.ct.go();
			
//			if (this.ct.getYLine() < beforeYLine){
//				this.ct.addElement(element); //Add element
//				this.ct.go(false);  // Do it really (don't simulate).
//			} else{
//				createFooter();
//				this.doc.newPage();
//				this.doc.add(this.imgLogo);
//				this.ct.setSimpleColumn(36, 36, PageSize.A4.getWidth() - 36, PageSize.A4.getHeight() - 36, 18, Element.ALIGN_LEFT);
//				this.ct.setYLine(PageSize.A4.getHeight() - 36);
//				this.ct.addElement(element); //Add element
//				this.ct.go();
//			}
			
//			this.status = this.ct.go(true);
//			
//			if (!ColumnText.hasMoreText(this.status)){ //If there's enough place
//				this.ct.addElement(element); //Add element
//				this.ct.go(false);  // Do it really (don't simulate).
//			} else{ //Or
//				createFooter(); //Add Footer
//				this.doc.newPage(); //Add new page
//				this.doc.add(this.imgLogo); //Add Logo
//				this.ct.addElement(element); //Add element
//				this.ct.setYLine(PageSize.A4.getHeight() - 36);
//				this.ct.go();
//			}
			
			while (true){
				this.status = this.ct.go();
				
				if (this.ct.getYLine() > 120){
					this.ct.addElement(new Paragraph("\n"));
					this.ct.go();
					break;
				} else{
					createFooter();
					this.doc.newPage();
					this.doc.add(this.imgLogo);
					this.ct.setSimpleColumn(36, 36, PageSize.A4.getWidth() - 36, PageSize.A4.getHeight() - 36, 18, Element.ALIGN_LEFT);
				}
			}
		}
	}

	private void addTitle(Chunk pTitle) throws DocumentException {
		this.ct.addElement(new Paragraph(pTitle));
		this.ct.addElement(new Paragraph("\n"));
		this.ct.go();
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
		this.imgLogo = Image.getInstance("S:\\DocumentsPourPDF\\images1.jpg");
		this.imgLogo.setAbsolutePosition(555, 800);
//		this.imgLogo.setWidthPercentage(5); //To modify with the %
		
		/**Modify the pixel size*/
		this.imgLogo.scaleAbsoluteHeight(30);
		this.imgLogo.scaleAbsoluteWidth(30);

	}
}