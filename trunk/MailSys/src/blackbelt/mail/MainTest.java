package blackbelt.mail;

public class MainTest{
	static MailDao bdd = new MailDao();
	
	public static void main(String[] args) {
				
		
		
		MailSender consumer = new MailSender();
		consumer.start();
		
		DataTest data = new DataTest();
		data.start();
		
	}
	
}
