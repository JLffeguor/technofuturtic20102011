package blackbelt.pdf;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

public class Main {

	public static void main(String[] args) throws DocumentException, IOException {
		
		TestDataGenerator dataGenerator = new TestDataGenerator();
		dataGenerator.generate();

		PdfGenerator pdfGen = new PdfGenerator();
		pdfGen.generatePdf((dataGenerator.getRoot()));

	}

}
