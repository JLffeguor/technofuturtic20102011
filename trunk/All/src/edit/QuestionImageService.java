package edit;
import image.HtmlToImageGenerator;

import java.io.File;
import java.io.FileFilter;

import directory.Directory;
/**
 * Handles images produced to replace text in [code] tags (to avoid copy/paste/execute cheating).
 * These images are only used during exams (not in listings and others).
 *  
 * @author Timmermans Gaetan, Alia Faton
 */

public class QuestionImageService {
	
	static final String QUESTION_IMAGE_DIRECTORY="C:\\workspaceDiff\\All\\";

	public static String replaceCodeTextWithImgTag(long questionId, String questionText) {
		
	}
	
	
	/** Deletes old images and generate new ones.
	 * Typically called when a new question is created or when an edit proposal is accepted.
	 *  */
	public static void updateQuestionImages(long questionId, String questionText) {
		delete...
		.... parse and call create.
	}
	
	
	/**
	 * Deletes all images according to idImage 
	 * @param QUESTION_IMAGE_DIRECTORY : image(s) directory 
	 * @param idImage	: image identification
	 */
	public static void delete(String idImage){
		File folder=new File(QUESTION_IMAGE_DIRECTORY);
		//listeFile contains images according to idImage
		File[] listeFile=folder.listFiles(new FileFilterImage(idImage));
		
		for(File file:listeFile){
			//check if file was deleted while the program is in execution
			if(file.exists()){
				file.delete();
			}
		}
	}
	
	/**
	 * Generates image and saves it
	 * @param html
	 * @param id
	 */
	public static void create(String html,String id){
		//generates image and saves it
		HtmlToImageGenerator.imageGenerator(html,QUESTION_IMAGE_DIRECTORY+id);
	}
	
	
	
	/**
	 * @author Timmermans Gaetan, Alia Faton
	 * Filters images according to their identification
	 * Example: 1234-A.png   (where 1234 is the question id).
	 */

	private static class FileFilterImage implements FileFilter{

		private static final char separator='-';
		private String idImage;
		
		public FileFilterImage(String idImage){
			this.idImage=idImage;
		}
		
		@Override
		public boolean accept(File pathname) {
			return pathname.getName().startsWith(idImage+separator);
		}
		
	}

	
}
