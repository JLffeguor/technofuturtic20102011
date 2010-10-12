//on the road again
package blackbelt.mail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import blackbelt.db.DataBaseConnection;
import blackbelt.model.Mail;
import blackbelt.model.User;

/** Fake Dao to be replace by JPA code */
public class MailDao {

	final static int MAXEMAILFORASEND = 2;

	public static MailDao instance = new MailDao();

	public MailDao() {
	}

	public void save(Mail mail) {
		Statement st;
		SimpleDateFormat dFormat = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss");
		try {
			st = DataBaseConnection.conn.createStatement();
			String s;
			s="insert into mails (UserId,Subject, Content, IsImediateMessage, CreationDate) values ("
					 + mail.getUser().getId() +","
					 + "'" + mail.getSubject().replace("'", "''") + "',"
					 + "'" + mail.getText().replace("'", "''") + "',"
					 + ((mail.getImmadiate()) ? "1," : "0,")
					 + "STR_TO_DATE('"+dFormat.format(mail.getDate())+"', '%d.%m.%Y %H.%i.%s'))";
			st.execute(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Mail> findNextMail() {
		
		String sql;
		Date now;
		Date dateForWeek;
		Date dateForDay;
		GregorianCalendar cal;
		SimpleDateFormat dFormat = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss");
		List<Mail> mails;
		Statement statement;
		ResultSet rs;
		
		now = new Date();
		cal = new GregorianCalendar();
		cal.setTime(now);
		cal.add(Calendar.SECOND, -10);
		dateForDay = cal.getTime();
		cal.add(Calendar.SECOND, -30);
		dateForWeek = cal.getTime();
		User user;
		
		mails = null;
		
		try {
			statement = DataBaseConnection.conn.createStatement();
			sql = "SELECT u.Id iduser, u.pseudo pseudo, u.email email, u.lastMailSendedDate lastMailSendedDate, u.mailingDelai mailingDelai " +
				  "\nFROM mails m " +
				  "\nINNER JOIN users u ON (m.UserId = u.id)" +
				  "\nWHERE (" +
				  "\n       m.IsImediateMessage=1" +
				  "\n      ) " +
				  "\n      OR" +
				  "\n      (" +
				  "\n           m.IsImediateMessage=0" +
				  "\n       AND " +
				  "\n          (" +
				  "\n               MailingDelai = 0 " +
				  "\n           OR (MailingDelai = 1 AND STR_TO_DATE('"+dFormat.format(dateForDay)+"', '%d.%m.%Y %H.%i.%s') > u.lastMailSendedDate)" +
				  "\n           OR (MailingDelai = 2 AND STR_TO_DATE('"+dFormat.format(dateForWeek)+"', '%d.%m.%Y %H.%i.%s') > u.lastMailSendedDate)" +
				  "\n          )" +
				  " \n     ) " +
				  "\nORDER BY m.IsImediateMessage DESC " +
				  "\nLIMIT 1;";
			rs = statement.executeQuery(sql);
			
			if (!rs.first()) {
				return null;
			}
			
			user = new User (rs.getInt("iduser"),rs.getString("pseudo"),rs.getString("email"), rs.getDate("lastMailSendedDate"), rs.getInt("mailingDelai"));
			rs.close();
			
			sql = "SELECT id, Subject, Content, IsImediateMessage, CreationDate " +
				  "FROM mails " +
				  "WHERE UserId = " + user.getId() + " " +
				  "ORDER BY IsImediateMessage DESC";
			rs = statement.executeQuery(sql);
			mails = new ArrayList<Mail>();
			
			while(rs.next()) {
				mails.add(new Mail(rs.getInt("id"), user, rs.getString("Subject"), rs.getString("Content"), rs.getBoolean("IsImediateMessage"), rs.getDate("CreationDate")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mails;
	}

	public void removeMails(List<Mail> mails) {
		
		int i, max;
		String s;
		Statement st;
		
		try {
			st = DataBaseConnection.conn.createStatement();
			s = "DELETE FROM mails WHERE id IN ("; 
			
			for (i = 0, max = mails.size() ; i < max ; i++) {
				if (i!=0) {
					s += ",";
				}
				s += mails.get(i).getId();
			}
			
			s += ");";
			
			st.execute(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
