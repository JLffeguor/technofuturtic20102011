package blackbelt.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackbelt.model.Categorie;
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
		mailService.createAndSaveMail(" subject ",Categorie.CONTRIBUTION,"Ce message ne sert a rien Go te chercher un caf�",1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject ",Categorie.CONTRIBUTION,"Ce message ne sert a rien.Go te chercher un caf�.",2L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject ",Categorie.CONTRIBUTION,"Ce message ne sert a rien.Go te chercher un caf�.",3L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject ",Categorie.CONTRIBUTION,"Ce message ne sert a rien.Go te chercher un caf�.",4L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject ",Categorie.CONTRIBUTION,"Ce message ne sert a rien.Go te chercher un caf�.",5L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject ",Categorie.CONTRIBUTION,"Ce message ne sert a rien.Go te chercher un caf�.",6L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject ",Categorie.QUESTION,"Ce message ne sert a rien.Go te chercher un caf�.",7L,MailType.GROUPABLE, true);
		
		try {
			//delai entre 2 send
			System.out.println("////////////////////////");
			sleep(5000);
			System.out.println("////////////////////////");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		mailService.createAndSaveMail(" subject ",Categorie.QUESTION, "contenu de la mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", 1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject ",null, "contenu de. la mort qui tue, again.", 1L,MailType.IMMEDIATE, true);
		mailService.createAndSaveMail(" subject",Categorie.QUESTION, "deuxieme contenu de la mort qui tue 1, plusieur fois.", 1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject",Categorie.QUESTION, "deuxieme contenu de la mort qui tue 2, plusieur fois.", 1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject",Categorie.CONTRIBUTION, "deuxieme contenu de la mort qui tue 3, plusieur fois.", 1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject",Categorie.CONTRIBUTION, "deuxieme contenu de la mort qui tue 4, plusieur fois.", 1L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject",null, "deuxieme contenu de la mort qui tue 4, plusieur fois.", 1L,MailType.SLOW_NOT_GROUPABLE, true);
		
		mailService.createAndSaveMail(" subject ",Categorie.COACH, "deuxieme contenu de la mort qui tue.encore.", 2L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject ",null, "contenu de la mort qui tue.", 2L,MailType.IMMEDIATE, true);
		mailService.createAndSaveMail(" subject ",Categorie.COACH, "blablabla. blablabla blabla.", 2L,MailType.GROUPABLE, true);
		
		mailService.createAndSaveMail(" subject ",Categorie.COACH,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject ",null,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.IMMEDIATE, true);
		mailService.createAndSaveMail(" subject ",Categorie.COACH,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.GROUPABLE, true);
		mailService.createAndSaveMail(" subject ",null,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.SLOW_NOT_GROUPABLE, false);
		mailService.createAndSaveMail(" subject ",null,"blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,MailType.SLOW_NOT_GROUPABLE, true);
	}
}
