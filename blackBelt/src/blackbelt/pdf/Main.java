package blackbelt.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;

public class Main {
	private static final String DOCUMENT_RESULT = ("S:\\DocumentsPourPDF\\HtmlPage.pdf");

	public static void main(String[] args) throws DocumentException, IOException {
		
		TestDataGenerator dataGenerator = new TestDataGenerator();
		dataGenerator.generate();

		PdfGenerator pdfGen = new PdfGenerator();
		pdfGen.generatePdf(dataGenerator.getRoot(), new FileOutputStream(DOCUMENT_RESULT));

	}

}
