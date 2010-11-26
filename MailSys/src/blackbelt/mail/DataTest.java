package blackbelt.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.model.MailSubject;
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
		mailService.send(MailSubject.SUBJECT_1,"Ce message ne sert a rien.Go te chercher un café.",1L,MailType.GOUPABLE);
		mailService.send(MailSubject.SUBJECT_1,"Ce message ne sert a rien.Go te chercher un café.",2L,MailType.GOUPABLE);
		mailService.send(MailSubject.SUBJECT_1,"Ce message ne sert a rien.Go te chercher un café.",3L,MailType.GOUPABLE);
		mailService.send(MailSubject.SUBJECT_2,"Ce message ne sert a rien.Go te chercher un café.",4L,MailType.GOUPABLE);
		mailService.send(MailSubject.SUBJECT_2,"Ce message ne sert a rien.Go te chercher un café.",5L,MailType.GOUPABLE);
		mailService.send(MailSubject.SUBJECT_2,"Ce message ne sert a rien.Go te chercher un café.",6L,MailType.GOUPABLE);
		mailService.send(MailSubject.SUBJECT_2,"Ce message ne sert a rien.Go te chercher un café.",7L,MailType.GOUPABLE);
		
		try {
			//delai entre 2 send
			System.out.println("////////////////////////");
			sleep(40000);
			System.out.println("////////////////////////");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		mailService.send(MailSubject.SUBJECT_3, "contenu de la mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", 1L,MailType.GOUPABLE);
		mailService.send(MailSubject.CONGRATULATION, "contenu de. la mort qui tue, again.", 1L,MailType.IMMEDIATE);
		mailService.send(MailSubject.SUBJECT_3, "deuxieme contenu de la mort qui tue, plusieur fois.", 1L,MailType.GOUPABLE);
		
		mailService.send(MailSubject.SUBJECT_3, "deuxieme contenu de la mort qui tue.encore.", 2L,MailType.GOUPABLE);
		mailService.send(MailSubject.CONGRATULATION, "contenu de la mort qui tue.", 2L,MailType.IMMEDIATE);
		mailService.send(MailSubject.SUBJECT_4, "blablabla. blablabla blabla.", 2L,MailType.GOUPABLE);
		
		mailService.send(MailSubject.SUBJECT_4,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.GOUPABLE);
		mailService.send(MailSubject.CONGRATULATION,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.IMMEDIATE);
		mailService.send(MailSubject.SUBJECT_4,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.GOUPABLE);

	}
}
