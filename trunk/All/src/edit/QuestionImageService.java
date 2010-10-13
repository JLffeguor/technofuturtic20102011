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

	/**
	 * Find an image and return an image HTML tag of this one 
	 * @param questionId : image identification
	 * @param questionText : question text
	 * @return an image HTML tag in string
	 */
	public static String replaceCodeTextWithImgTag(long questionId, String questionText) {
		File imageFile=new File(QUESTION_IMAGE_DIRECTORY+questionId);
		if(!imageFile.exists())
			create(questionId, questionText);
		return "<img src=\""+imageFile+"\">";
	}
	
	
	/** Deletes old images and generate new ones.
	 * Typically called when a new question is created or when an edit proposal is accepted. 
	 */
	public static void updateQuestionImages(long questionId, String questionText) {
		delete(questionId);
		create(questionId,questionText);
		//.... parse.
	}
	
	
	/**
	 * Deletes all images according to idImage
	 * @param questionId : image identification
	 */
	public static void delete(long questionId){
		File folder=new File(QUESTION_IMAGE_DIRECTORY);
		//listeFile contains images according to idImage
		File[] listeFile=folder.listFiles(new FileFilterImage(Long.toString(questionId)));
		
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
	public static void create(long questionId, String questionText){
		//generates image and saves it
		HtmlToImageGenerator.imageGenerator(questionText,QUESTION_IMAGE_DIRECTORY+questionId);
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
