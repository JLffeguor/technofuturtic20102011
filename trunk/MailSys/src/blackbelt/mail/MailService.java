package blackbelt.mail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import blackbelt.db.DataBaseConnection;
import blackbelt.model.Mail;
import blackbelt.model.User;

public class MailService {

	public static MailService instance = new MailService(); // TODO: replace by being a Sping bean.
	
	
	public void send(String subject, String text,int userID,boolean isAImmadiateMessage){
		// TODO: templating around the content.
		
		try{
			Statement st = DataBaseConnection.conn.createStatement();
			ResultSet rs= st.executeQuery("SELECT * FROM USERS WHERE id LIKE "+userID);
			rs.first();
			User user = new User(rs.getInt("id"),rs.getString("pseudo"),rs.getString("email"),rs.getDate("lastMailSendedDate"),rs.getInt("mailingDelai"));
			rs.close();
			Mail mail = new Mail(user,subject,text,isAImmadiateMessage);
			MailDao.instance.save(mail);
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}		
	}
	public void updateLastMailSendedDate(User user){
		//todo
	}

	
}

