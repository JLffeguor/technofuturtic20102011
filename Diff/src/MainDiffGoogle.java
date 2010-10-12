
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

import blackbelt.util.diff.DiffGenerator;
import blackbelt.util.diff.diff_match_patch;



public class MainDiffGoogle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String acienneVersionText="Bonjour, comment ça va? - ça va bien";
		
		String nouvelleVersionText="Bonjour, azarz comment vas-tu? - ça va bien";
		
		
		diff_match_patch diff =new diff_match_patch();
		LinkedList<diff_match_patch.Diff> resultatTemp=diff.diff_main(acienneVersionText, nouvelleVersionText);
		diff.diff_cleanupSemantic(resultatTemp);
		String resultatFinal=diff.diff_prettyHtml(resultatTemp);
		try {
			PrintWriter print=new PrintWriter("./oldAndNew.html");
			print.write(resultatFinal);
			print.flush();
			print.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DiffGenerator service=new DiffGenerator(acienneVersionText, nouvelleVersionText);
		try {
			PrintWriter print=new PrintWriter("./old.html");
			print.write(service.getTextOriginalHtmlDiff(false));
			print.flush();
			print.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			PrintWriter print=new PrintWriter("./new.html");
			print.write(service.getTextModifiedHtmlDiff(false));
			print.flush();
			print.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
