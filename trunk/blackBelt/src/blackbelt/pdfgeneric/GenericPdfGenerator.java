package blackbelt.pdfgeneric;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

/** 
 * Facade of iText API
 * The purpose is to create a pdf with any text but with a predefined style.
 * It can also parse an HTML document with the same predefined style.
 * */
public class GenericPdfGenerator {
	
	private String DOCUMENT_RESULT = ("S:\\DocumentsPourPDF\\GenericPdf.pdf");
	private Document doc;
	private PdfWriter writer;
	private PdfContentByte contentByte;
	private ColumnText columnText;
	private int linesAtTheAndOfTheDocumentToAvoidOrphan = 4;
	private int pageNumber = 1;
	private Image logo;
	
	
	/** 
	 * Create and prepare the document
	 * @throws DocumentException
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * */
	public GenericPdfGenerator() throws DocumentException, MalformedURLException, IOException{
		
		//Create the document and the writer
		doc = new Document();
		writer = PdfWriter.getInstance(doc, new FileOutputStream(DOCUMENT_RESULT));
		doc.open();
		contentByte = writer.getDirectContent();
		
		//Create a column text to avoid orphan
		//The column is a A4 page with little margin and the text will be align left.
		columnText = new ColumnText(contentByte);
		columnText.setSimpleColumn(36, 36, PageSize.A4.getWidth() - 36, PageSize.A4.getHeight() - 36, 18, Element.ALIGN_LEFT);
		columnText.setLeading(0, 1.5f);
		
		//Create the footer
		addFooter();
		
		//Add Logo
		logo = generateImageLogo("S:\\DocumentsPourPDF\\images1.jpg");
		doc.add(logo);
	}
	
	public GenericPdfGenerator(String path) throws FileNotFoundException, DocumentException{
		
		//Create the document and the writer
		doc = new Document();
		writer = PdfWriter.getInstance(doc, new FileOutputStream(path));
		doc.open();
		contentByte = writer.getDirectContent();
		
		//Create a column text to avoid orphan
		//The column is a A4 page with little margin and the text will be align left.
		columnText = new ColumnText(contentByte);
		columnText.setSimpleColumn(36, 36, PageSize.A4.getWidth() - 36, PageSize.A4.getHeight() - 36, 18, Element.ALIGN_LEFT);
		columnText.setLeading(0, 1.5f);
		
		//Create the footer
		addFooter();
	}
	
	
	/**
	 * Create a title.
	 * The title will is red, bold and you must choose the size
	 * @throws DocumentException 
	 * */
	public void addTitle(String title, int size) throws DocumentException{
	
		//Create a Chunk with the text and the size.
		Chunk chunkTitle = createTitleChunk(title, size);
		
		//Add the title
		columnText.addElement(chunkTitle);
		columnText.addElement(new Paragraph("\n"));
		columnText.go(false);
	}
	
	/**
	 * Write a text. You can choose the size. If you d'ont precise it, it'll be 12px
	 * @throws DocumentException 
	 * */
	public void addSimpleText(String text) throws DocumentException{
		addSimpleText(text, 12);
	}
	
	public void addSimpleText(String text, int size) throws DocumentException{		
		Paragraph paragraph = new Paragraph(text);
		
		//Check if there is enough place in the document
		boolean pageOverflow = simulateAddElement(paragraph);
		
		if (! pageOverflow // All the text fits the page until now. 
				||  columnText.getLinesWritten() > linesAtTheAndOfTheDocumentToAvoidOrphan){ // Or (too much text for the page, but) enough lines have been written at the bottom of the page.

			// We write that text for real on the current page.
			columnText.addElement(paragraph);
			columnText.go(false);

			if (pageOverflow) {  // We need to print the rest on the next page.
				newPage();
				// we don't need to add the element again after the go of the previous page, because elements causing overflow are not removed from the ColumnText.compositeElements list. It's still hanging there.
				columnText.go(false);
			}

		} else {  // We want a new page before we print any part of the element.		
			newPage();

			columnText.addElement(paragraph);
			columnText.go(false);
		}
	}
	
	/**
	 * Parse a string with HTML code to a pdf document.
	 * The parser analyse each tag in the string and give the defined style if the tag is find thanks to the method loadTagStyle
	 * @throws IOException 
	 * @throws DocumentException 
	 * */
	public void addHtmlBody(String body) throws IOException, DocumentException{
		
		StyleSheet styleBody = new StyleSheet();
		styleBody.loadTagStyle("ul", "i", ""); //If the tag "ul" is find, it will be italic
		styleBody.loadTagStyle("ul", "indent", "12"); //And the indentation is 12px
		
		List<Element> bodyElements;
		bodyElements = HTMLWorker.parseToList(new StringReader(body), styleBody); //Format text and apply style
		
		for (Element element : bodyElements){
			//Check if there is enough place in the document
			boolean pageOverflow = simulateAddElement(element);
			
			if (! pageOverflow // All the text fits the page until now. 
					||  columnText.getLinesWritten() > linesAtTheAndOfTheDocumentToAvoidOrphan){ // Or (too much text for the page, but) enough lines have been written at the bottom of the page.

				// We write that text for real on the current page.
				columnText.addElement(element);
				columnText.go(false);

				if (pageOverflow) {  // We need to print the rest on the next page.
					newPage();
					// we don't need to add the element again after the go of the previous page, because elements causing overflow are not removed from the ColumnText.compositeElements list. It's still hanging there.
					columnText.go(false);
				}

			} else {  // We want a new page before we print any part of the element.		
				newPage();

				columnText.addElement(element);
				columnText.go(false);
			}
			
			columnText.addElement(new Paragraph("\n")); //Add a new line
			columnText.go(false);
		}
	}
	
	
	private Chunk createTitleChunk(String text, int size){
		//The font will be Helveltica and the text will be bold.
		Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
		fontTitle.setSize(size); // the size is the variable.
		fontTitle.setColor(170, 0, 0); //And the color is dark red.
		
		Chunk result = new Chunk(text, fontTitle);
		
		return result;		
	}
	
	/** Returns true if adding the element would overflow the page, and rolls back everything 
	 * @throws DocumentException */
	private boolean simulateAddElement(Paragraph para) throws DocumentException{
		int status;
		float position;
		// Simulation start
		position = columnText.getYLine();
		columnText.addElement(para);
		status = columnText.go(true);
		clearBuffer(columnText);
		columnText.setYLine(position); // come back as before the simulation.

		return ColumnText.hasMoreText(status);
	}
	
	/** Returns true if adding the element would overflow the page, and rolls back everything
	 *  * @throws DocumentException */
	private boolean simulateAddElement(Element element) throws DocumentException {
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
	private void clearBuffer(ColumnText columnText) {
		columnText.setText(null);  // Removes any pending element (else it would be half rendered, only the part that would not fit the previous page would be rendered).
	}
	
	private Image generateImageLogo(String path) throws BadElementException, MalformedURLException, IOException{
		Image imageLogo;
		imageLogo = Image.getInstance(path);
		imageLogo.setAbsolutePosition(555, 800);
//		imageLogo.setWidthPercentage(5); //To modify with the %
		
		/**Modify the pixel size*/
		imageLogo.scaleAbsoluteHeight(30);
		imageLogo.scaleAbsoluteWidth(30);
		
		return imageLogo;
	}
	
	private void addFooter(){
		Paragraph p = new Paragraph("Page : " + pageNumber);
		ColumnText.showTextAligned(contentByte, Element.ALIGN_CENTER, p, (this.doc.right()- this.doc.left())/2+this.doc.leftMargin(), this.doc.bottom() - 12, 0);
		pageNumber++;
	}
	
	/** Add a new page to the document 
	 * @throws DocumentException */
	private void newPage() throws DocumentException{
		doc.newPage();
		doc.add(logo);
		columnText.setYLine(PageSize.A4.getHeight() - 36);
		addFooter();
	}
	
	public void closeDocument(){
		doc.close();
	}

}
