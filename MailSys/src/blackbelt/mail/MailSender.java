package blackbelt.mail;

import blackbelt.model.Mail;

public class MailSender {

	public static MailSender instance = new MailSender(); // TODO: replace by being a Sping bean.
	
	
	/** Searches for the next mail to be send and send it (if any) */
	public void sendNextMails() {
		Mail nextMail = MailDao.instance.findNextMail();
		while (nextMail != null) {
			sendMail(nextMail);
			// Wait for the SMTP server to get the previous mail and not crash being overflown.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextMail = MailDao.instance.findNextMail();
		}
	}
	
	
	public synchronized void sendMail(Mail mail) {
		// Should send the mail to the SMTP server here.
		// But we just display at the console
		sendConsoleMail(mail);
	}
	
	private void sendConsoleMail(Mail mail){
		System.out.println("Mailing to:"+mail.getUser().getPseudo());
		System.out.println(mail.getSubject()+"       "+mail.getDateMessage());
		System.out.println("---------------------------------");
		System.out.println(mail.getText());
	}

}
