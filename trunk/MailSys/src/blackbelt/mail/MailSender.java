package blackbelt.mail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import blackbelt.dao.ExtractionMail;
import blackbelt.model.Mail;
import blackbelt.model.User;
import blackbelt.template.MainTemplate;
/**
 *This class checks if there are any mails and sends them
 */
@Repository
public class MailSender extends Thread {

	public final static int MS_BETWEEN_EACH_MAIL = 100;
	
	@Autowired
	private ExtractionMail extractMail;
	
	@Autowired
	private MainTemplate mainTemplate;
	
	@Override
	public void run() {
		
		while (isAlive()) {
			
			List<Mail> nextMailList = extractMail.findNextMail();
			List<Mail> toSend;
			
			while (nextMailList != null) {
				
				if (nextMailList.get(0).getImmediate()) { // if there are immediate mails, we send one after one... 
					for (int i=0; i<nextMailList.size() ; i++) {
						toSend = new ArrayList();
						toSend.add(nextMailList.get(i));
						sendMailList(toSend);
						extractMail.removeMails(toSend);
						this.sleepBetweenSend();
					}
				} else {// ...here we send a group of mails as one mail
					sendMailList(nextMailList);
					extractMail.updateLastMailSendedDate(nextMailList.get(0).getUser());
					extractMail.removeMails(nextMailList);
					this.sleepBetweenSend();
				}
				nextMailList = extractMail.findNextMail();
			}
			
			try {
				System.out.println("******************************************************************");
				System.out.println("*****aucun message a envoyer dans l'immediat, dodo 15 secondes****");// on attend 15 secondes
				System.out.println("******************************************************************");
				//sleep(MS_BETWEEN_EACH_MAIL * 15);
				sleep(15000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sleepBetweenSend(){
		try {
			// delai entre 2 send
			sleep(MS_BETWEEN_EACH_MAIL);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Send mails to a user.
	 * @param mail
	 * NB : here we simulate the sending of mails by saving them in a file
	 */
	public void sendMailList(List<Mail> mail) {
		String content;
		sendConsoleMail(mail);
		
		try {
			content = this.mainTemplate.TemplateMail(mail);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		try {
			
			File rep;
			PrintWriter make;
			User user;
			user = mail.get(0).getUser();
			rep = new File("C:/testing/" + user.getPseudo());
			rep.mkdirs();
			make = new PrintWriter("C:/testing/" + user.getPseudo() + "/" + mail.size() + "_message(s).html");// apres : le nombbre de message
			make.println(content);
			make.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void sendConsoleMail(List<Mail> mail) {
		// TODO: replace this code by SMTP send.
		System.out.println("************Mailing to:" + mail.get(0).getUser().getPseudo() + "************");
		for (Mail i : mail) {
			System.out.println(i.getSubject() + "       " + i.getDateMessage());
			System.out.println(i.getText());
			System.out.println("-----------------");
		}

	}
}