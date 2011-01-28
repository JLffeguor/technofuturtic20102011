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

	 
	public final static int DELAY_BETWEEN_EACH_MAIL = 1000;  // in ms. In case the SMTP server is too slow (cannot accept too many mails too fast),
	   // use this const to temporize between 2 SMTP calls. 
	   // FIXME : put this value: ???????????????
	public final static int WAKE_UP_DELAY_WHEN_NO_MAIL = 15 * 1000;  // ms. When there is no mail anymore, how long should this batch sleep before querying the DB again for mails to be sent ?
	
	@Autowired	private MailDao mailDao;
//	@Autowired  private UserDao userDao; // FIXME: CHECK WHEN INTEGRATION
	
	@Autowired	private MainTemplateService mainTemplate;
	
	@Override
	public void run() {
		
		while (isAlive()) {
			
			List<Mail> toSend;
			List<Mail> nextMailList = this.findNextMails();
			
			while (nextMailList != null && nextMailList.size()>0) {
				Mail nextMail = nextMailList.get(0);
				if (nextMail.getMailType()== MailType.IMMEDIATE || nextMail.getMailType()== MailType.SLOW_NOT_GROUPABLE || nextMail.getEmailTarget() != null) { // if there are immediate mails, we send one after one... 
					for (int i=0; i<nextMailList.size() ; i++) {
						// Send the mail and remove it from the DB.
						List<Mail> mails;
						mails = new ArrayList<Mail>();
						mails.add(nextMailList.get(i));
						sendMailList(mails);
						mailDao.removeMails(mails);
						this.sleepWell(DELAY_BETWEEN_EACH_MAIL);
					}
				} else {// ...here we send a group of mails as one mail
					// Send all these mails grouped as one mail and remove them from the DB.
					sendMailList(nextMailList);
					nextMail.getUser().setLastMailSendedDate(new Date());
//					userDao.save(nextMail.getUser()); // FIXME: CHECK WHEN INTEGRATION 
					mailDao.removeMails(nextMailList);// FIXME: CHECK WHEN INTEGRATION 
					//the thread sleeps well between each mail sent, if it gets interrupted there is a bug 
					this.sleepWell(DELAY_BETWEEN_EACH_MAIL);
				}
				nextMailList = this.findNextMails();
			}
			
			// FIXME --- remove these prints.
			System.out.println("******************************************************************");
			System.out.println("*****aucun message a envoyer dans l'immediat, dodo 15 secondes****");// on attend 15 secondes
			System.out.println("******************************************************************");
			//if new mail is in database the thread will wake up and get back to work.
			sleepBad(WAKE_UP_DELAY_WHEN_NO_MAIL);
		}
	}
	
	/** To manage the InterruptedException */
	synchronized private void sleepWell(int delayMs){
		try {
		    //There is no mail in database, sleep
			sleep(delayMs);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
        }
	}
	
	synchronized private void sleepBad(int delayMs){
        try {
            //wait for new mail 
            wait(delayMs);
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
		MainTemplateService.MailSubjectAndContent mp;
		mp = this.mainTemplate.templateMail(mails);
		
		// FIXME: really send the mail via SMTP instead of saving it on the file system.
		// sendSmtpMail(mail.get(0).getUser(), content);
		sendConsoleMail(mails);
		if ((mails.get(0).getUser()!=null)){
		    sendFileMail(mails.get(0).getUser(), mp.getContent(), mp.getSubject());
		} else {
		    sendFileMail(mails.get(0).getEmailTarget(), mp.getContent(), mp.getSubject());
		}
	}
	
	private void sendSmtpMail(User user, String content, String subject) {
		throw new UnsupportedOperationException();
	}
		
	// TODO: remove when integrated.
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
	
	private void sendFileMail(String targerMail, String content, String subject) {
        try {
            File rep;
            Date date;
            PrintWriter make;
            GregorianCalendar gc;
            String fileName;
            rep = new File("C:/testing/Mails/" + targerMail);
            rep.mkdirs();
            date=new Date();
            fileName = date.getYear()+"_"+(date.getMonth()+1)+"_"+date.getDay()+"_"+date.getHours()+"_"+date.getMinutes()+"_"+date.getSeconds()+"_"+subject+"_message(s).html";
            make = new PrintWriter("C:/testing/Mails/" + targerMail + "/" +fileName);// apres : le nombbre de message
            make.println("<div>subject: "+subject+"</div>");
            make.println(content);
            make.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	private void sendConsoleMail(List<Mail> mail) {
		// TODO: replace this code by SMTP send.
		System.out.println("************Mailing to:" + ((mail.get(0).getUser()!=null)?mail.get(0).getUser().getPseudo():mail.get(0).getEmailTarget()) + "************");
		for (Mail i : mail) {
			System.out.println(i.getSubject().toString() + "       " + i.getDateMessage());
			System.out.println(i.getText());
			System.out.println("-----------------");
		}
	}
	
	/**
	 * returns a list of mails which are either immediate or grouped of a user.
	 * 
	 * @return a list of mails all to the same user.
	 */
	public List<Mail> findNextMails(){
		
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
		
		// 3. There is no immediate and groupable mail to send (anymore) => we look for mails to send to electronic address mails (instead of user).
		List<Mail> mails = mailDao.getMailsEmailTarget(); 
		if(mails!=null && mails.size()>0) {
		    return mails;
		}
		
		// 4. There is no immediate and groupable mail to send (anymore), we look for slow_not_groupabel(example newsletter) mails to send
		user = mailDao.userHavingSlowMails();
		if(user!=null){
			return mailDao.getMailsFromUser(MailType.SLOW_NOT_GROUPABLE, user);
		}
	
		// 5. There is no next mail to be sent.
		return new ArrayList<Mail>();  // Empty list, no mail to send.
	}
}