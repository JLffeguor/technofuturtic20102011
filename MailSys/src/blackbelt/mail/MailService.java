package blackbelt.mail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.db.DataBaseConnection;
import blackbelt.model.Mail;
import blackbelt.model.User;

@Repository
@Transactional
public class MailService {
	
	@PersistenceContext
	private EntityManager em;
	
	public static MailService instance = new MailService(); // TODO: replace by being a Sping bean.
	
	public void send(String subject, String text,int userID,boolean isAImmadiateMessage){
		
		/*try{
			Statement st = DataBaseConnection.conn.createStatement();
			ResultSet rs= st.executeQuery("SELECT * FROM USERS WHERE id LIKE "+userID);
			rs.first();
			User user = new User(rs.getInt("id"),rs.getString("pseudo"),rs.getString("email"),rs.getDate("lastMailSendedDate"),rs.getInt("mailingDelai"));
			rs.close();
			Mail mail = new Mail(user,subject,text,isAImmadiateMessage);
			MailDao.instance.save(mail);
		} catch(SQLException e){
			throw new RuntimeException(e);
		}*/
		
		User user = (User) em.createQuery("").getSingleResult();
	}
	
	public void updateLastMailSendedDate(User user){

		try{
			Date now = new Date();
			SimpleDateFormat dFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
			Statement st = DataBaseConnection.conn.createStatement();
			st.execute("UPDATE users SET lastMailSendedDate =STR_TO_DATE('"+dFormat.format(now)+"', '%d.%m.%Y %H.%i.%s') WHERE id LIKE "+user.getId());
			user.setLastMailSendedDate(now);
		} catch(SQLException e){
			throw new RuntimeException(e);
		}		
	}

	
}

