package coursepage;

import java.io.IOException;
import java.io.PrintWriter;


public class Main {

	public static void main(String[] args){
	CourseTextFormatter courseTextFormatter = new CourseTextFormatter("helfhdhyrghryryrlo","<html><head><title>Coucou c'est pas moi</title></head><body><h1>Titre h1</h1><p>Greetings Earthlings! <br />We've come for your Java.Voici un texte <b>avec du gras</b> et avec <i>de l'italic</i> !!!</p><h2>[code]Toujours en test h2[/code]</h2><p>Bonjour et bienvenue<br />I'me going home</p></body></html>");
//	System.out.println(courseTextFormatter.format());
	
	try{
	PrintWriter outF = new PrintWriter("S:\\TestCourseTextFormatter.txt");
	String s = (courseTextFormatter.format());
	outF.println(s);
	outF.close();
			
	}catch (IOException e){
		throw new RuntimeException(e);
	}
	}
}
