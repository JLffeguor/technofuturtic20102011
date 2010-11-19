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
//		String s1 = ("Dans notre Normandie, glorieuse et mutil�e, Bayeux et ses environs furent t�moins d'un des plus grands �v�nements de l'Histoire. Nous attestons qu'ils en furent dignes. C'est ici que, quatre ann�es apr�s le d�sastre initial de la France et des Alli�s, d�buta la victoire finale des Alli�s et de la France. C'est ici que l'effort de ceux qui n'avaient jamais c�d� et autour desquels s'�taient, � partir du 18 juin 1940, rassembl� l'instinct national et reform�e la puissance fran�aise tira des �v�nements sa d�cisive justification.\n ");
//		String s2 = ("En m�me temps, c'est ici que sur le sol des anc�tres r�apparut l'�tat; l'�tat l�gitime, parce qu'il reposait sur l'int�r�t et le sentiment de la nation; l'�tat dont la souverainet� r�elle avait �t� transport�e du c�t� de la guerre, de la libert� et de la victoire, tandis que la certitude n'en conservait que l'apparence; l'�tat sauvegard� dans ses droits, sa dignit�, son autorit�, au milieu des vicissitudes du d�nuement et de l'intrigue; l'�tat pr�serv� des ing�rences de l'�tranger; l'�tat capable de r�tablir autour de lui l'unit� nationale et l'unit� imp�riale, d'assembler toutes les forces de la patrie et de l'Union Fran�aise, de porter la victoire � son terme, en commun avec les Alli�s, de traiter d'�gal � �gal avec les autres grandes nations du monde, de pr�server l'ordre public, de faire rendre la justice et de commencer notre reconstruction.\n ");
//		String s3 = ("Si cette grande oeuvre fut r�alis�e en dehors du cadre ant�rieur de nos institutions, c'est parce que celles-ci n'avaient pas r�pondu aux n�cessit�s nationales et qu'elles avaient, d'elles-m�mes, abdiqu� dans la tourmente. Le salut devait venir d'ailleurs. Il vint, d'abord, d'une �lite, spontan�ment jaillie des profondeurs de la nation et qui, bien au-dessus de toute pr�occupation de parti ou de classe, se d�voua au combat pour la lib�ration, la grandeur et la r�novation de la France. Sentiment de sa sup�riorit� morale, conscience d'exercer une sorte de sacerdoce du sacrifice et de l'exemple, passion du risque et de l'entreprise, m�pris des agitations, pr�tentions, surench�res, confiance souveraine en la force et en la ruse de sa puissante conjuration aussi bien qu'en la victoire et en l'avenir de la patrie, telle fut la psychologie de cette �lite partie de rien et qui, malgr� de lourdes pertes, devait entra�ner derri�re elle tout l'Empire et toute la France.\n\n");
//		String s4 = ("Elle n'y e�t point, cependant, r�ussi sans l'assentiment de l'immense masse fran�aise. Celle-ci, en effet, dans sa volont� instinctive de survivre et de triompher, n'avait jamais vu dans le d�sastre de 1940 qu'une p�rip�tie de la guerre mondiale o� la France servait d'avant-garde. Si beaucoup se pli�rent, par force, aux circonstances, le nombre de ceux qui les accept�rent dans leur esprit et dans leur coeur fut litt�ralement infime. Jamais la France ne crut que l'ennemi ne f�t point l'ennemi et que le salut f�t ailleurs que du c�t� des armes de la libert�. A mesure que se d�chiraient les voiles, le sentiment profond du pays se faisait jour dans sa r�alit�. Partout o� paraissait la croix de Lorraine s'�croulait l'�chafaudage d'une autorit� qui n'�tait que fictive, bien qu'elle f�t, en apparence, constitutionnellement fond�e. Tant il est vrai que les pouvoirs publics ne valent, en fait et en droit, que s'ils s'accordent avec l'int�r�t sup�rieur du pays, s'ils reposent sur l'adh�sion confiante des citoyens. En mati�re d'institutions, b�tir sur autre chose, ce serait b�tir sur du sable.\n\n ");
//		String s5 = ("Ce serait risquer de voir l'�difice crouler une fois de plus � l'occasion d'une de ces crises auxquelles, par la nature des choses, notre pays se trouve si souvent expos�.\n\n");
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
