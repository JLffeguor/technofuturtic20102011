package blackbelt.mail;

public class DataTest extends Thread{
	
	@Override
	public void run(){
		
		//init date
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",1,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",2,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",3,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",4,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",5,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",6,false);
		MailService.instance.send("message d'initialisation","Ce message ne sert a rien.Go te chercher un café.",7,false);
		
		try {
			//delai entre 2 send
			sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		MailService.instance.send("message 1", "contenu de la mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", 1,false);
		MailService.instance.send("message 2", "contenu de. la mort qui tue, again.", 1,true);
		MailService.instance.send("message 3", "deuxieme contenu de la mort qui tue, plusieur fois.", 1,false);
		
		MailService.instance.send("message 4", "deuxieme contenu de la mort qui tue.encore.", 2,false);
		MailService.instance.send("message 5", "contenu de la mort qui tue.", 2,true);
		MailService.instance.send("message 6", "blablabla. blablabla blabla.", 2,false);
		
		MailService.instance.send("message 7","blablablablma blablabla blabla.blablabla.blabla blablablabla.",3,false);
		MailService.instance.send("message 8","blablablablma blablabla blabla.blablabla.blabla blablablabla.",3,true);
		MailService.instance.send("message 9","blablablablma blablabla blabla.blablabla.blabla blablablabla.",3,false);

	}
}
