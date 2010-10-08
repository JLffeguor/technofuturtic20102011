package blackbelt.mail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import blackbelt.model.Mail;

public class MailSender extends Thread{
	
	
	
	@Override
	public void run(){
		while(isAlive()){
		List<Mail> nextMail = MailDao.instance.findNextMail();
		while (nextMail != null) {
			System.out.println("message trouvé, traitement par user...");
			sendMail(nextMail);
			nextMail.get(0).getUser().setLastMailSendedDate(new Date());
			MailDao.instance.removeMails(nextMail);
			try {
				sleep(1000);//on laisse une seconde entre chaque mail pour pas surcharger le serveur
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextMail = MailDao.instance.findNextMail();
		}
		try {
			System.out.println("db vide, dodo 15 secondes...");//la db est vide, on attend 15 secondes
			sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		

	}
	
	
	
	
	public synchronized void sendMail(List<Mail> mail) {
		// Should send the mail to the SMTP server here.
		// But we just display at the console
		templateTesting(mail);
		//sendConsoleMail(mail);
	}
	
	private void sendConsoleMail(Mail mail){
		System.out.println("Mailing to:"+mail.getUser().getPseudo());
		System.out.println(mail.getSubject()+"       "+mail.getDateMessage());
		System.out.println("---------------------------------");
		System.out.println(mail.getText());
	}
	
	public void templateTesting(List<Mail> mailList){
		
		try{	
		Mail mail0 = mailList.get(0);
		File rep = new File("C:/testing/"+mail0.getUser().getPseudo());
		rep.mkdirs();
		PrintWriter make = new PrintWriter("C:/testing/"+mail0.getUser().getPseudo()+"/"+mailList.size()+"_message(s).html");//apres : le nombbre de message
		String content = new String();
		//head
		content += "<div align=\"center\"><font face=\"Impact,Verdana\" size=\"7\"><font color=\"#000000\">Black</font><font color=\"#bababa\">Belt</font><br>Factory</font></div><hr>";
		content += "<div align=\"center\"> to : "+mail0.getUser().getPseudo()+"  -  <a href=\"C:/testing/users/"+mail0.getUser().getPseudo()+".html\">your account here</a></div><br>";
		//contenu	
		for(Mail mail:mailList){
		content += "<div style=\"margin:10;width:70%\"><div style=\"background:#000000;padding:10\"><font face=\"Comic_Sans,Verdana\" color=\"#FFFFFF\" size=\"5\">"+mail.getSubject()+"</font><br><font color=\"#FFFFFF\" size=\"2\">"+mail.getDateMessage()+"</font></div><div style=\"background:#bababa;padding:15px\">"+mail.getFormatedText()+"</div></div>";
		}
		//footage
		content += "<br><hr><br><div align=\"center\"><a href=\"http://www.blackbeltfactory.com/ui#!\"><img border=\"0\" src=\"http://antisosial.free.fr/projet/BlackBeltFactoryLogo3D-header.png\"><br>www.blackbeltfactory.com</a></div>";
		make.println(content);
		make.close();
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
	}

}