package blackbelt.mail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import blackbelt.dao.MailDao;
import blackbelt.model.Mail;
import blackbelt.model.MailType;
import blackbelt.model.User;
import blackbelt.template.MainTemplateService;
/**
 *This class checks if there are any mails and sends them
 */
@Repository
public class MailSender extends Thread {

	 
	public final static int DELAY_BETWEEN_EACH_MAIL = 100;  // ms.  // FIXME : put this value: ???????????????
	public final static int WAKE_UP_DELAY_WHEN_NO_MAIL = 15 * 1000;  // ms. When there is no mail anymore, how long should this batch sleep ?
	
	@Autowired	private MailDao mailDao;
	
	@Autowired	private MainTemplateService mainTemplate;
	
	@Override
	public void run() {
		
		while (isAlive()) {
			
			List<Mail> toSend;
			List<Mail> nextMailList = this.findNextMail();
			
			while (nextMailList != null && nextMailList.size()>0) {
				
				if (nextMailList.get(0).getMailType()== MailType.IMMEDIATE || nextMailList.get(0).getMailType()== MailType.SLOW_NOT_GROUPABLE) { // if there are immediate mails, we send one after one... 
					for (int i=0; i<nextMailList.size() ; i++) {
						// Send the mail and remove it from the DB.
						List<Mail> mails;
						mails=new ArrayList<Mail>();
						mails.add(nextMailList.get(i));
						sendMailList(mails);
						mailDao.removeMails(mails);
						this.sleepBetweenSend();
					}
				} else {// ...here we send a group of mails as one mail
					// Send all these mails grouped as one mail and remove them from the DB.
					sendMailList(nextMailList);
					mailDao.updateLastMailSendedDate(nextMailList.get(0).getUser());
					mailDao.removeMails(nextMailList);
					this.sleepBetweenSend();
				}
				nextMailList = this.findNextMail();
			}
			
			try {
				// FIXME --- remove these prints.
				System.out.println("******************************************************************");
				System.out.println("*****aucun message a envoyer dans l'immediat, dodo 15 secondes****");// on attend 15 secondes
				System.out.println("******************************************************************");
				sleep(WAKE_UP_DELAY_WHEN_NO_MAIL);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private void sleepBetweenSend(){
		try {
			// delai entre 2 send
			sleep(DELAY_BETWEEN_EACH_MAIL);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Send mails to a user.
	 * @param mail
	 * NB : here we simulate the sending of mails by saving them in a file
	 */
	public void sendMailList(List<Mail> mails) {
		MainTemplateService.MailPackage mp;
		try {
			mp = this.mainTemplate.templateMail(mails);
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		// FIXME: really send the mail via SMTP instead of saving it on the file system.
		// sendSmtpMail(mail.get(0).getUser(), content);
		sendConsoleMail(mails);
		sendFileMail(mails.get(0).getUser(), mp.getContent(), mp.getSubject());
	}
	
	private void sendSmtpMail(User user, String content, String subject) {
		throw new UnsupportedOperationException();
	}
		
	private void sendFileMail(User user, String content, String subject) {
		try {
			File rep;
			Date date;
			PrintWriter make;
			GregorianCalendar gc;
			String fileName;
			rep = new File("C:/testing/Mails/" + user.getPseudo());
			rep.mkdirs();
			date=new Date();
			fileName = date.getYear()+"_"+(date.getMonth()+1)+"_"+date.getDay()+"_"+date.getHours()+"_"+date.getMinutes()+"_"+date.getSeconds()+"_"+subject+"_message(s).html";
			make = new PrintWriter("C:/testing/Mails/" + user.getPseudo() + "/" +fileName);// apres : le nombbre de message
			make.println("<div>subject: "+subject+"</div>");
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
			System.out.println(i.getSubject().toString() + "       " + i.getDateMessage());
			System.out.println(i.getText());
			System.out.println("-----------------");
		}
	}
	
	/**
	 * returns a list of mails which are either immediate or grouped of a user.
	 * @return a list of mails
	 */
	public List<Mail> findNextMail(){
		
		User user;
		
		// 1. Send non groupables mails (i.e: user creation activation mail).
		user = mailDao.userHavingImmediateMails();
		if(user!=null){
			return mailDao.getMailsFromUser(MailType.IMMEDIATE, user);
		}
		
		// 2. There is no immediate (non groupable) mail to send (anymore) => we look for groupable mails.
		user = mailDao.userHavingGroupedMails();
		if(user!=null){
			return mailDao.getMailsFromUser(MailType.GROUPABLE, user);
		}
		
		// 3. There is no immediate (non groupable) mail to send (anymore) => we look for groupable mails.
		user = mailDao.userHavingSlowMails();
		if(user!=null){
			return mailDao.getMailsFromUser(MailType.SLOW_NOT_GROUPABLE, user);
		}
	
		// 4. There is no next mail to be sent.
		return new ArrayList<Mail>();  // Empty list, no mail to send.
	}
}