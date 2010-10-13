package image;
import gui.ava.html.image.generator.HtmlImageGenerator;

import java.awt.image.BufferedImage;

/**
 * HTML To Image Generator
 * @author Alia Faton, Timmermans Gaetan
 *
 */
public class HtmlToImageGenerator {
	
	/**
	 * Generates an image from String HTML code given as parameter. 
	 * @param html
	 */
	public static void imageGenerator(String html,String imageName){

			HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
			
			//Add style to String HTML code *2
			String htmlWithStyle = "<div style=\"font-family:monospace;font-size:14px;\">"+html;
			
			/* Styles for java formating ************************************ */
			//CSS into HTML : the library(html2image) doesn't recognize CSS
			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"java_plain\"", "span style=\"color: rgb(0, 0, 0);\"");
			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"java_keyword\"", "span style=\"color: #7f0055;font-weight: bold;\"");
			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"java_type\"", "span style=\"color: rgb(0, 0, 0);\"");
			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"java_operator\"", "span style=\"color: rgb(0, 124, 31);\"");
			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"java_separator\"", "span style=\"color: rgb(0, 0, 0);\"");
			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"java_literal\"", "span style=\"color: #2a00ff;\"");
			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"java_comment\"", "span style=\"color: #008800;\"");
			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"java_javadoc_comment\"", "span style=\"color: #3f5fbf;\"");
			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"java_javadoc_tag\"", "span style=\"color: #3f5fbf;\"");
			

//			/* Styles for XML formating ************************************ */
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"xml_tag_symbols\"", "span style=\"color: rgb(0, 59, 255);\"");
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"xml_rife_tag\"", "span style=\"color: rgb(0, 0, 0);background-color: rgb(228, 230, 160);\"");
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"xml_plain\"", "span style=\"color: rgb(0, 0, 0);\"");
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"xml_comment\"", "span style=\"color: rgb(147, 147, 147);\"");
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"xml_attribute_name\"", "span style=\"color: rgb(0, 0, 0);font-weight: bold;\"");
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"xml_tag_name\"", "span style=\"color: rgb(0, 55, 255);\"");
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"xml_char_data\"", "span style=\"color: rgb(0, 0, 0);\"");
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"xml_processing_instruction\"", "span style=\"color: rgb(0, 0, 0);font-weight: bold;font-style: italic;\"");
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"xml_attribute_value\"", "span style=\"color: rgb(193, 0, 0);\"");
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"xml_rife_name\"", "span style=\"color: rgb(0, 0, 196); background-color: rgb(228, 230, 160);\"");
			
//			htmlWithStyle=htmlWithStyle.replaceAll("span class=\"code\"", "span style=\"color: rgb(0, 0, 0);font-family: monospace;font-size: 130%;\"");

			htmlWithStyle+="</div>";//End *2
			
			//Allows style "font-size" to function
			htmlWithStyle=htmlWithStyle.replaceAll("\\s*<[/]?code>\\s*","");
			
			//Generate image
			imageGenerator.loadHtml(htmlWithStyle);
			imageGenerator.saveAsImage(imageName);
	}
}
