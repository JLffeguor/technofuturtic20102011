package pdfGenerator;

public class Main {

	public static void main(String[] args) {
		
		TestDataGenerator dataGenerator = new TestDataGenerator();
		PdfGenerator pdfGen = new PdfGenerator();
		dataGenerator.generate();
		pdfGen.recursiveWalk((dataGenerator.section1));

	}

}
