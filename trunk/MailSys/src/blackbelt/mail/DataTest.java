package blackbelt.mail;

public class DataTest extends Thread{
	
	@Override
	public void run(){
		
		//init date
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",1L,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",2L,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",3L,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",4L,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",5L,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",6L,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",7L,false);
		
		try {
			//delai entre 2 send
			sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		MailService.instance.send("message 1", "contenu de la mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", 1L,false);
		MailService.instance.send("message 2", "contenu de. la mort qui tue, again.", 1L,true);
		MailService.instance.send("message 3", "deuxieme contenu de la mort qui tue, plusieur fois.", 1L,false);
		
		MailService.instance.send("message 4", "deuxieme contenu de la mort qui tue.encore.", 2L,false);
		MailService.instance.send("message 5", "contenu de la mort qui tue.", 2L,true);
		MailService.instance.send("message 6", "blablabla. blablabla blabla.", 2L,false);
		
		MailService.instance.send("message 7","blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,false);
		MailService.instance.send("message 8","blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,true);
		MailService.instance.send("message 9","blablablablma blablabla blabla.blablabla.blabla blablablabla.",3L,false);

	}
}
