package blackbelt.mail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import blackbelt.model.Mail;

public class MailSender extends Thread{
	
	
	
	@Override
	public void run(){
		while(isAlive()){
		Mail nextMail = MailDao.instance.findNextMail();
		while (nextMail != null) {
			sendMail(nextMail);
			nextMail.getUser().setLastMailSendedDate(new Date());
			try {
				sleep(1000);//on laisse une seconde entre chaque mail pour pas surcharger le serveur
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextMail = MailDao.instance.findNextMail();
		}
		try {
			System.out.println("db vide, dodo");//la db est vide, on attend 10 secondes
			sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		

	}
	
	
	
	
	public synchronized void sendMail(Mail mail) {
		// Should send the mail to the SMTP server here.
		// But we just display at the console
		templateTesting(mail);
		sendConsoleMail(mail);
	}
	
	private void sendConsoleMail(Mail mail){
		System.out.println("Mailing to:"+mail.getUser().getPseudo());
		System.out.println(mail.getSubject()+"       "+mail.getDateMessage());
		System.out.println("---------------------------------");
		System.out.println(mail.getText());
	}
	
	public void templateTesting(Mail mail){
		try{
		File rep = new File("C:/testing/"+mail.getUser().getPseudo());
		rep.mkdirs();
		PrintWriter make = new PrintWriter("C:/testing/"+mail.getUser().getPseudo()+"/"+mail.getSubject()+".html");
		make.println("<html>\n<head><title>"+mail.getSubject()+"</title></head>\n<body>");
		make.println("<p>"+mail.getText()+"</p>");
		make.println("</body>\n</html>");
		make.close();
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
	}

}