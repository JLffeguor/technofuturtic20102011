package blackbelt.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.model.MainSubject;
import blackbelt.model.MailType;
/**
 *It' simulates a sending of mails
 */
@Service
public class DataTest extends Thread{
	
	@Autowired
	MailService mailService;
	@Override
	public void run(){
		
		//init date
		mailService.createAndSaveMail(" blllllllaaaaaaaaaaaaaaaaa ",MainSubject.ANSWER_IN_FORUM,"Ce message ne sert a rien.Go te chercher un café.",1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" blllllllaaaaaaaaaaaaaaaaa ",MainSubject.ANSWER_IN_FORUM,"Ce message ne sert a rien.Go te chercher un café.",2L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" blllllllaaaaaaaaaaaaaaaaa ",MainSubject.ANSWER_IN_FORUM,"Ce message ne sert a rien.Go te chercher un café.",3L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" blllllllaaaaaaaaaaaaaaaaa ",MainSubject.ANSWER_FROM_COACH,"Ce message ne sert a rien.Go te chercher un café.",4L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" blllllllaaaaaaaaaaaaaaaaa ",MainSubject.ANSWER_FROM_COACH,"Ce message ne sert a rien.Go te chercher un café.",5L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" blllllllaaaaaaaaaaaaaaaaa ",MainSubject.ANSWER_FROM_COACH,"Ce message ne sert a rien.Go te chercher un café.",6L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" blllllllaaaaaaaaaaaaaaaaa ",MainSubject.ANSWER_FROM_COACH,"Ce message ne sert a rien.Go te chercher un café.",7L,MailType.GROUPABLE, true);
		
		try {
			//delai entre 2 send
			System.out.println("////////////////////////");
			sleep(40000);
			System.out.println("////////////////////////");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		mailService.createAndSaveMail(" message 1 ",MainSubject.REMINDER, "contenu de la mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", 1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" message 2 ",MainSubject.CONGRATULATION, "contenu de. la mort qui tue, again.", 1L,MailType.IMMEDIATE, true);
		mailService.createAndSaveMail(" message 3 - 1",MainSubject.REMINDER, "deuxieme contenu de la mort qui tue 1, plusieur fois.", 1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" message 3 - 2",MainSubject.CONGRATULATION, "deuxieme contenu de la mort qui tue 2, plusieur fois.", 1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" message 3 - 3",MainSubject.REMINDER, "deuxieme contenu de la mort qui tue 3, plusieur fois.", 1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" message 3 - 4",MainSubject.ANSWER_IN_FORUM, "deuxieme contenu de la mort qui tue 4, plusieur fois.", 1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" message 10",MainSubject.ANSWER_IN_FORUM, "deuxieme contenu de la mort qui tue 4, plusieur fois.", 1L,MailType.SLOW_NOT_GROUPABLE, true);
		
		mailService.createAndSaveMail(" message 4 ",MainSubject.REMINDER, "deuxieme contenu de la mort qui tue.encore.", 2L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" message 5 ",MainSubject.CONGRATULATION, "contenu de la mort qui tue.", 2L,MailType.IMMEDIATE, true);
		mailService.createAndSaveMail(" message 6 ",MainSubject.REMINDER, "blablabla. blablabla blabla.", 2L,MailType.GROUPABLE, true);
		
		mailService.createAndSaveMail(" message 7 ",MainSubject.REMINDER,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" message 8 ",MainSubject.CONGRATULATION,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.IMMEDIATE, true);
		mailService.createAndSaveMail(" message 9 ",MainSubject.REMINDER,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" message 11a ",MainSubject.REMINDER,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.SLOW_NOT_GROUPABLE, false);
		mailService.createAndSaveMail(" message 11b ",MainSubject.REMINDER,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.SLOW_NOT_GROUPABLE, true);
	}
}
