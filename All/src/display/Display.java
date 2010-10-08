package display;
import image.HtmlToImageGenerator;

import java.io.File;

import directory.Directory;

import edit.QuestionImageService;

/** 
 * @author Timmermans Gaetan, Alia Faton
 * Searches an image(creates if it doesn't exist)and then parses it into a HTML code
 */
public class Display implements Directory{
	/**
	 * @param html 	: blackbelt TAG 
	 * @param id	: image identification
	 * @param image	: image name
	 */
	public static void display(String html,String id,String image){
		if(chekFile(directory+image))
			System.out.println("parse image");//parse
		else
			QuestionImageService.create(html, id);
	}
	/**
	 * Checks if image exists
	 * @param image : image name
	 * @return	: returns true if image exists
	 */
	public static boolean chekFile(String image){
		File file=new File(image);
		return file.exists();
	}
}
