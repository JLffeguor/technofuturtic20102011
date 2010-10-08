package blackbelt.pdf;

import java.io.FileNotFoundException;

import com.itextpdf.text.DocumentException;

public class Main {

	public static void main(String[] args) throws DocumentException, FileNotFoundException {
		
		TestDataGenerator dataGenerator = new TestDataGenerator();
		dataGenerator.generate();

		PdfGenerator pdfGen = new PdfGenerator();
		pdfGen.generatePdf((dataGenerator.getRoot()));

	}

}
