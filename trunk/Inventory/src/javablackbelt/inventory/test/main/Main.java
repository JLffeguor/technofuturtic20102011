package javablackbelt.inventory.test.main;

import javax.swing.JOptionPane;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String response = JOptionPane.showInputDialog(null,
				  "What is the target's nickname ?",
				  "Enter the target's nickname",
				  JOptionPane.QUESTION_MESSAGE);
		
		System.out.println(response);
	}

	
}
