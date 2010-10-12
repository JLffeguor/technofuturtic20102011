package blackbelt.mail;

public class MainTest{
	static MailDao bdd = new MailDao();
	
	public static void main(String[] args) {
				
		
		
		MailSender consumer = new MailSender();
		consumer.start();
		

		MailService.instance.send("blablabla", "contenu de la mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", 1,false);
		MailService.instance.send("reblabla ", "contenu de. la mort qui tue, again.", 1,true);
		MailService.instance.send("rerereblabla ", "deuxieme contenu de la mort qui tue, plusieur fois.", 1,false);
		MailService.instance.send("la reproduction des pinguins en australie", "deuxieme contenu de la mort qui tue.encore.", 2,false);
		MailService.instance.send("sujet de la mort qui tue", "contenu de la mort qui tue.", 2,true);
		MailService.instance.send("encoxcbxcbre sujet de la mortsdfbxbv qui tue", "blablabla. blablabla blabla.", 2,false);
		MailService.instance.send("woooooot","i'm on thxfndfgnhdfgne road,AGAIN.",4,false);
		MailService.instance.send("blablxcbvxbabla", "contenu de lxcbvxcna mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", 4,false);
		MailService.instance.send("reblsdfhsghabla ", "contenu de. la mort qui tue, again.", 5,false);
		MailService.instance.send("rerereblabla ", "deuxieme contenu de xcbxbvla mort qui tue, plusieur fois.", 6,false);
		MailService.instance.send("la   rexvbgproduction des pinguins en australie", "deuxieme contenu de la mort qui tue.encore.", 6,true);
		MailService.instance.send("sujet de xcbxcbvla mort qui tue", "contenu de la mort qui tue.", 7,false);
		MailService.instance.send("encore sujet de la mort qui tue", "blablabla. blablabla blabla.", 7,false);
		MailService.instance.send("woooooot","i'm on the road,AGAIN.",7,false);
		MailService.instance.send("blav,v,blabla", "contenu de la mort qui tue, ztrztz zertzg blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", 2,false);
		MailService.instance.send("reblabla ", "contenu de. la mort qui tue, again.", 2,false);
		MailService.instance.send("rerereblabla ", "deuxieme contenu de la mort qui tue, plusieur fois.", 2,false);
		MailService.instance.send("la reproduction des pingui fgsdfgns en australie", "deuxiemexbxcbvxc contenu de la mort qui tue.encore.", 1,false);
		MailService.instance.send("sujet de la mort qui tue", "contenu debvxcbxcbvxcb la mort qui tue.", 1,false);
		MailService.instance.send("encore sujet de la mosfg srt qui tue", "blablabla. blablabla blabla.", 3,false);
		MailService.instance.send("woooooot","i'm on the road,AGAIN.",4,false);		MailService.instance.send("blablabla", "contenu de la mort qui tue, blablabla.blablabla blabla blablabla.blabla blablabla blablablabla blabla.", 5,false);
		MailService.instance.send("reblasgfsgbla ", "contenu de. la mort qui tue, again.", 7,false);
		MailService.instance.send("rersthgssereblabla ", "deuxieme contenu de la mort qui tue, plusieur fois.", 5,false);
		MailService.instance.send("la reproduction des pinguins en australie", "deuxieme contenu de la mort qui tue.encore.", 2,false);
		MailService.instance.send("sujeghshsghht de la mort qui tue", "contenu de la mosdfgsgrt qui tue.", 1,false);
		MailService.instance.send("encosdgsgsdgfsre sujet de lasgsgsdg mort qui tue", "blablabla. blabsdgsgsglabla blabla.", 3,false);
		MailService.instance.send("woooosdgsdgsdgoot","i'm on the rgsgsdgsdgoad,AGAIN.",4,false);
		

		
	}
	
}
