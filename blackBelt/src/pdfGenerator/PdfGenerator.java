package pdfGenerator;

import htmltopdf.HtmlToPdf;
import coursepage.CourseTextFormatter;

public class PdfGenerator {

	

	public void recursiveWalk(Section currentSection) {

		CourseTextFormatter sectionFormatter = new CourseTextFormatter("",
				currentSection.getBody());
//		 System.out.println(currentSection.getTitle() + "\n" +
//		 sectionFormatter.format());
		HtmlToPdf.convertPdf(sectionFormatter.format());
//		sectionFormatter = null;

		try {
			if (!currentSection.getSubSections().isEmpty()) {
				for (Section sSection : currentSection.getSubSections()) {

					recursiveWalk(sSection);

				}

			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}

	
	
}
