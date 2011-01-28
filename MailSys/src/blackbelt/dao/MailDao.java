package blackbelt.dao;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.model.Mail;
import blackbelt.model.MailType;
import blackbelt.model.MailingDelayType;
import blackbelt.model.User;

@Transactional
@Repository
public class MailDao {


    @PersistenceContext
    private EntityManager em;

    /**
     * returns a user containing immediate mails
     * @return a user
     */
    public User userHavingImmediateMails(){
        List<User> list = em.createQuery("SELECT m.user FROM Mail m WHERE m.mailType=:MailType")
        .setParameter("MailType", MailType.IMMEDIATE)
        .setMaxResults(1)  // Because we don't need all the users, just one.
        .getResultList();

        if (list.size() == 0) {
            return null;	
        } else {
            return list.get(0);  // We just want one user, we'll execute this query again later to get the next user.
        }
    }

    /**
     * returns a user (the next one) having grouped mails that must be sent now.
     * @return a user
     */
    public User userHavingGroupedMails(){
        ///// We compute the date/time of yesterday (for users having grouped daily), and the date/time of one week ago (for users having grouped weekly)
        Date yesterday;
        Date lastWeek;		
        Date now;
        now = new Date();
        GregorianCalendar cal= new GregorianCalendar();
        cal.setTime(now);
        // FIXME Actually, for debug, we use : daily = 10 sec and weekly = 40 sec.
        cal.add(Calendar.SECOND, -10);   // FIXME  cal.add(Calendar.DAY, -1);   
        yesterday = cal.getTime();
        cal.add(Calendar.SECOND, -30);   // FIXME  cal.add(Calendar.DAY, -7);
        lastWeek = cal.getTime();	
        /************************/

        //request to get a user having at least one old pending grouped mails (so old that we must send it now). 
        List<User> list = em.createQuery("SELECT m.user "
                + "FROM Mail m "
                + " WHERE ("
                + "         m.mailType=:MailType"
                + " 	AND"
                + "          ("
                + "               m.user.lastMailSendedDate IS NULL "   // Not sent yet
                + "           OR  (     m.user.lastMailSendedDate IS NOT NULL"
                + "                AND (    (m.user.mailingDelai = :dailytype AND :yesterday > m.user.lastMailSendedDate)"
                + "                      OR (m.user.mailingDelai = :weeklytype AND :lastWeek > m.user.lastMailSendedDate)"
                + "                      OR  m.user.mailingDelai = :immediatetype"
                + "                    )"
                + "              )"
                + "          )"
                + "     ) ")
                .setParameter("MailType", MailType.GROUPABLE)
                .setParameter("yesterday", yesterday)
                .setParameter("lastWeek", lastWeek)
                .setParameter("dailytype", MailingDelayType.DAILY)
                .setParameter("weeklytype", MailingDelayType.WEEKLY)
                .setParameter("immediatetype", MailingDelayType.IMMEDIATELY)
                .setMaxResults(1)
                .getResultList();
        if (list.size() == 0) {
            return null;	
        } else {
            return list.get(0);  // We just want one user, we'll execute this query again later to get the next user.
        }	
    }	

    /**
     * Get mails for not blackbelt user
     */
    
    public List<Mail> getMailsEmailTarget(){
        return (List<Mail>) em.createQuery("select m from Mail m where m.user is null order by m.creationDate").setMaxResults(5).getResultList(); 
    }


    /**
     * returns a user containing slow not groupable mails
     * @return a user
     */
    public User userHavingSlowMails(){
        List<User> list = em.createQuery("SELECT m.user FROM Mail m WHERE m.mailType=:MailType")
        .setParameter("MailType", MailType.SLOW_NOT_GROUPABLE)
        .setMaxResults(1)  // Because we don't need all the users, just one.
        .getResultList();

        if (list.size() == 0) {
            return null;	
        } else {
            return list.get(0);  // We just want one user, we'll execute this query again later to get the next user.
        }
    }

    /**
     * Get mails, immediate or groupable  from a given user.
     * @param isImmediate indicates if we want immediate mails or grouped mails.
     * @param user the user who we want the mail.
     * @return a list of mails 
     */
    public List<Mail> getMailsFromUser(MailType mailType, User user){
        Query query = em.createQuery("SELECT m FROM Mail m WHERE m.user =:user AND m.mailType =:mailType" )
        .setParameter("user",user)
        .setParameter("mailType",mailType); 
        return query.getResultList();
    }

    /**
     * remove a list of mails
     * @param mails
     */
    public void removeMails(List<Mail> mails) {
        if(!mails.isEmpty()){
            Query update = em.createQuery("DELETE FROM Mail m WHERE m IN (:mails)").setParameter("mails",mails);
            update.executeUpdate();
        }
    }

    // TODO: remove at integration (use inherited)
    /**
     * saves a mail in the database
     * @param mail
     * @param idUser
     */
    public void save(Mail mail, Long idUser) {
        if(idUser !=null){
            User user = (User) em.find(User.class, idUser);
            mail.setUser(user);
            
        }
        em.persist(mail);
    }
}
