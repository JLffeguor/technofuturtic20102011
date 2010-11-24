package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import dao.domainModel.Section;


public class Services {
	private Section section;
	private final String CSS_URL = ("S:\\DocumentsPourPDF\\stylePDF.css");
	
	public Services(Section section){
		this.section = section;
	}
	public  void printSection(){
		System.out.println(this.section.getTitle());
		System.out.println(this.section.getBody());
		System.out.println("********************************");
		for(Section s : section.getSubSections()){
			Section sub = new Section(s);
			Services serv = new Services(sub);
			serv.printSection();
		}
	}
	public String format(){
		String finalResult = new String("");
		if(this.section.getParent()==null){
			/* header html */
			finalResult+="<html><head><title>"+this.section.getTitle()+"</title></head><body>";
			/*Page de garde*/
			finalResult+=addIntro();
			finalResult+="<br/><br/><h1>"+this.section.getTitle()+"</h1>";
			
		}
		else{
			finalResult+="<br/><br/><h2>"+this.section.getTitle()+"</h2>";
		}
		
		
//		if(this.section.getBody().contains("[video id=")){
//			
//		}
		finalResult+=this.section.getBody();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		for(Section s : this.section.getSubSections()){
			Services serv = new Services(s);
			finalResult+=serv.format();
		}
		if(this.section.getParent()==null){
			finalResult+="</body></html>";
		}
		return finalResult;
	}
	
	public String loadStyleCss(){
		String css = new String("");
		File file = new File(CSS_URL);
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()){
				css+=scanner.nextLine();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException ("erreur lors du chargement de la feuille de style");
		}
		
		return css;
	}
	public String addIntro(){
		String intro = new String("");
		intro +="<div align='center' class='logo'><b>Black<span class='grey'>Belt</span><br/>Factory</b></div><br/><br/><div align='center'><img align='middle' src='http://antisosial.free.fr/projet/asianWomanSword.jpg'></div><pd4ml:page.break>";
		
		return intro;
	}

}
