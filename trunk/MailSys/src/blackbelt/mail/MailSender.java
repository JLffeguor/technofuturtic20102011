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

@Repository
public class MailSender extends Thread {

	public final static int MS_BETWEEN_EACH_MAIL = 100;
	
	@Autowired
	private ExtractionMail extractMail;
	
	@Override
	public void run() {
		
		while (isAlive()) {
			
			List<Mail> nextMailList = extractMail.findNextMail();
			
			while (nextMailList != null) {
				
				// TODO: given list as param should be for 1 user only....
				if (nextMailList.get(0).getImmediate()) {
					List<Mail> toSend = new ArrayList<Mail>();
					toSend.add(nextMailList.get(0));
					sendMailList(toSend);
					extractMail.removeMails(toSend);
					nextMailList = extractMail.findNextMail();
				} else {
					sendMailList(nextMailList);
					extractMail.updateLastMailSendedDate(nextMailList.get(0).getUser());
					extractMail.removeMails(nextMailList);
					nextMailList = extractMail.findNextMail();
				}

				try {
					// delai entre 2 send
					sleep(MS_BETWEEN_EACH_MAIL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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

	public synchronized void sendMailList(List<Mail> mail) {
		// Should send the mail to the SMTP server here.
		// But we just display at the console
		templateTesting(mail);
		sendConsoleMail(mail);
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

	public void templateTesting(List<Mail> mailList) {

		try {
			
			// Get user of all these mails from the 1st mail (same user)
			User user = mailList.get(0).getUser();

			File rep = new File("C:/testing/" + user.getPseudo());
			rep.mkdirs();
			PrintWriter make = new PrintWriter("C:/testing/" + user.getPseudo() + "/" + mailList.size() + "_message(s).html");// apres : le nombbre de message
			String content = new String();

			// head
			content += "<div align=\"center\"><font face=\"Impact,Verdana\" size=\"7\"><font color=\"#000000\">Black</font><font color=\"#bababa\">Belt</font><br>Factory</font></div><hr>";
			content += "<div align=\"center\"> to : " + user.getPseudo()
					+ "  -  <a href=\"C:/testing/users/" + user.getPseudo()
					+ ".html\">your account here</a></div><br>";

			// content
			for (Mail mail : mailList) {
				content += "<div style=\"margin:10;width:70%\"><div style=\"background:#000000;padding:10\"><font face=\"Comic_Sans,Verdana\" color=\"#FFFFFF\" size=\"5\">"
						+ mail.getSubject()
						+ "</font><br><font color=\"#FFFFFF\" size=\"2\">"
						+ mail.getDateMessage()
						+ "</font></div><div style=\"background:#bababa;padding:15px\">"
						+ mail.getFormatedText() + "</div></div>";
			}

			// footage
			content += "<br><hr><br><div align=\"center\"><a href=\"http://www.blackbeltfactory.com/ui#!\"><img border=\"0\" src=\"http://antisosial.free.fr/projet/BlackBeltFactoryLogo3D-header.png\"><br>www.blackbeltfactory.com</a></div>";

			make.println(content);
			make.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}