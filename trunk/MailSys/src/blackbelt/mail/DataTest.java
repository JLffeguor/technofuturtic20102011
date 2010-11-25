package blackbelt.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
		mailService.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",1L,false);
		mailService.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",2L,false);
		mailService.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",3L,false);
		mailService.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",4L,false);
		mailService.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",5L,false);
		mailService.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",6L,false);
		mailService.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",7L,false);
		
		try {
			//delai entre 2 send
			System.out.println("////////////////////////");
			sleep(40000);
			System.out.println("////////////////////////");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		mailService.send("message 1", "contenu de la mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", 1L,false);
		mailService.send("message 2", "contenu de. la mort qui tue, again.", 1L,true);
		mailService.send("message 3", "deuxieme contenu de la mort qui tue, plusieur fois.", 1L,false);
		
		mailService.send("message 4", "deuxieme contenu de la mort qui tue.encore.", 2L,false);
		mailService.send("message 5", "contenu de la mort qui tue.", 2L,true);
		mailService.send("message 6", "blablabla. blablabla blabla.", 2L,false);
		
		mailService.send("message 7","blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,false);
		mailService.send("message 8","blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,true);
		mailService.send("message 9","blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,false);

	}
}
