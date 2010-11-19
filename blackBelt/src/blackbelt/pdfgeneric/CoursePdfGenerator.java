package blackbelt.pdfgeneric;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;

import coursepage.CourseTextFormatter;

import blackbelt.pdf.Section;

/** It stock the HTML code in a String and the String is send to the PdfGenerator. */
public class CoursePdfGenerator {
	
	private List<Section> sectionList = new ArrayList<Section>();
	
	/** Stock a section and his subsections in a List. */
	public void setSectionList(Section currentSection){
		
		sectionList.add(currentSection);
		
		for (Section sSection : currentSection.getSubSections()) {
			setSectionList(sSection);
		}
	}
	
	/** Create the pdf with the string of each section 
	 * @throws DocumentException 
	 * @throws IOException */
	public void createPdf() throws DocumentException, IOException{
		
		GenericPdfGenerator pdf = new GenericPdfGenerator();
		String body;
		
		
		//1nd. We add each section to the document
		for (Section sList : sectionList){
			int size = calculTitleSize(sList); //Calcul the title's size
			pdf.addTitle(sList.getTitle(), size); //Add the title
			
			//2nd. We format the body
			body = sList.getBody();
			CourseTextFormatter sectionFormatter = new CourseTextFormatter("", body);
			body = sectionFormatter.format();
			
			pdf.addHtmlBody(body); //Add the body
		}
		
		/*IMPORTANT !!!
		 * 3d. Never forget to close the document */
		pdf.closeDocument();
	}
	
	private int calculTitleSize(Section section){
		int result = 20;
		
		while(section.getParent() != null){
			section = section.getParent();
			result -= 3;
		}
		
		return result;
	}
}














//	public static void main(String arg[]) throws FileNotFoundException, DocumentException{
//		String titleTest = "TITRE un.";
//		
//		String s1 = ("Dans notre Normandie, glorieuse et mutilée, Bayeux et ses environs furent témoins d'un des plus grands événements de l'Histoire. Nous attestons qu'ils en furent dignes. C'est ici que, quatre années après le désastre initial de la France et des Alliés, débuta la victoire finale des Alliés et de la France. C'est ici que l'effort de ceux qui n'avaient jamais cédé et autour desquels s'étaient, à partir du 18 juin 1940, rassemblé l'instinct national et reformée la puissance française tira des événements sa décisive justification.\n ");
//		String s2 = ("En même temps, c'est ici que sur le sol des ancêtres réapparut l'État; l'État légitime, parce qu'il reposait sur l'intérêt et le sentiment de la nation; l'État dont la souveraineté réelle avait été transportée du côté de la guerre, de la liberté et de la victoire, tandis que la certitude n'en conservait que l'apparence; l'État sauvegardé dans ses droits, sa dignité, son autorité, au milieu des vicissitudes du dénuement et de l'intrigue; l'État préservé des ingérences de l'étranger; l'État capable de rétablir autour de lui l'unité nationale et l'unité impériale, d'assembler toutes les forces de la patrie et de l'Union Française, de porter la victoire à son terme, en commun avec les Alliés, de traiter d'égal à égal avec les autres grandes nations du monde, de préserver l'ordre public, de faire rendre la justice et de commencer notre reconstruction.\n ");
//		String s3 = ("Si cette grande oeuvre fut réalisée en dehors du cadre antérieur de nos institutions, c'est parce que celles-ci n'avaient pas répondu aux nécessités nationales et qu'elles avaient, d'elles-mêmes, abdiqué dans la tourmente. Le salut devait venir d'ailleurs. Il vint, d'abord, d'une élite, spontanément jaillie des profondeurs de la nation et qui, bien au-dessus de toute préoccupation de parti ou de classe, se dévoua au combat pour la libération, la grandeur et la rénovation de la France. Sentiment de sa supériorité morale, conscience d'exercer une sorte de sacerdoce du sacrifice et de l'exemple, passion du risque et de l'entreprise, mépris des agitations, prétentions, surenchères, confiance souveraine en la force et en la ruse de sa puissante conjuration aussi bien qu'en la victoire et en l'avenir de la patrie, telle fut la psychologie de cette élite partie de rien et qui, malgré de lourdes pertes, devait entraîner derrière elle tout l'Empire et toute la France.\n\n");
//		String s4 = ("Elle n'y eût point, cependant, réussi sans l'assentiment de l'immense masse française. Celle-ci, en effet, dans sa volonté instinctive de survivre et de triompher, n'avait jamais vu dans le désastre de 1940 qu'une péripétie de la guerre mondiale où la France servait d'avant-garde. Si beaucoup se plièrent, par force, aux circonstances, le nombre de ceux qui les acceptèrent dans leur esprit et dans leur coeur fut littéralement infime. Jamais la France ne crut que l'ennemi ne fût point l'ennemi et que le salut fût ailleurs que du côté des armes de la liberté. A mesure que se déchiraient les voiles, le sentiment profond du pays se faisait jour dans sa réalité. Partout où paraissait la croix de Lorraine s'écroulait l'échafaudage d'une autorité qui n'était que fictive, bien qu'elle fût, en apparence, constitutionnellement fondée. Tant il est vrai que les pouvoirs publics ne valent, en fait et en droit, que s'ils s'accordent avec l'intérêt supérieur du pays, s'ils reposent sur l'adhésion confiante des citoyens. En matière d'institutions, bâtir sur autre chose, ce serait bâtir sur du sable.\n\n ");
//		String s5 = ("Ce serait risquer de voir l'édifice crouler une fois de plus à l'occasion d'une de ces crises auxquelles, par la nature des choses, notre pays se trouve si souvent exposé.\n\n");
//
//		
//		PdfGenerator.createAndOpenDocument();
//		PdfGenerator.addTitle(titleTest, 16);
//		PdfGenerator.addSimpleText(s1);
//		PdfGenerator.addSimpleText(s2);
//		PdfGenerator.addSimpleText(s3);
//		PdfGenerator.addSimpleText(s4);
//		PdfGenerator.addSimpleText(s5);
//		PdfGenerator.closeDocument();
//	}
//}
