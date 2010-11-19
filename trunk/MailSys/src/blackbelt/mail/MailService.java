package blackbelt.mail;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.dao.InsertionMail;
import blackbelt.model.Mail;
import blackbelt.model.User;

@Repository
@Transactional
public class MailService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private InsertionMail im;
	
	public static MailService instance = new MailService(); // TODO: replace by being a Sping bean.

	public void send(String subject, String text,Long userID,boolean isAImmadiateMessage){
		Mail mail = new Mail(null,subject,text,isAImmadiateMessage);
		im.save(mail, userID);
	}

	/*public void updateLastMailSendedDate(User user){

		try{
			Date now = new Date();
			SimpleDateFormat dFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
			Statement st = DataBaseConnection.conn.createStatement();
			st.execute("UPDATE users SET lastMailSendedDate =STR_TO_DATE('"+dFormat.format(now)+"', '%d.%m.%Y %H.%i.%s') WHERE id LIKE "+user.getId());
			user.setLastMailSendedDate(now);
		} catch(SQLException e){
			throw new RuntimeException(e);
		}		
	}*/
}

