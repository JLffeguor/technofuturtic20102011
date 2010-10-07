package generatorpdf;

import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * The class parse a (X)HTML document to a PDF document
 * 
 * Library : iText 5.0.4
 * 
 * @author Julien
 * */

public class HtmlToPdf {
	/** The String in parameter is a big String with the (X)HTML code*/
	public static void convertPdf(String stringHtmlToPdf){
		Document doc = new Document();
		StyleSheet bodyStyle = new StyleSheet();
		
		try{	
			PdfWriter.getInstance(doc, new FileOutputStream("htmlPage.pdf"));
			doc.open();
		/** Create a list of elements who are present in the body*/
			ArrayList<Element> pBody;
		/** Create property of the (X)HTML tags who are in the attribute stringHtmlToPdf*/
			bodyStyle.loadTagStyle("h1", "leading", "38,0");
			bodyStyle.loadTagStyle("h1", "color", "red");
			bodyStyle.loadTagStyle("h1", "face", "Courier");
			bodyStyle.loadTagStyle("h2", "color", "blue");
			bodyStyle.loadTagStyle("h2", "face", "Arial");
			bodyStyle.loadTagStyle("p", "color", "black");
			bodyStyle.loadTagStyle("p", "leading", "14,0");
			bodyStyle.loadTagStyle("p", "face", "Times");
			
		/** Parse HTML document with a list of iText objects*/
			pBody = HTMLWorker.parseToList(new StringReader(stringHtmlToPdf), bodyStyle);
			for (int i = 0; i < pBody.size(); i++){
				doc.add(pBody.get(i)); //add each element
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
		doc.close();
	}
}

