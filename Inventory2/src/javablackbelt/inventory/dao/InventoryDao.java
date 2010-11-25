package javablackbelt.inventory.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class InventoryDao {

	@PersistenceContext
	private EntityManager em;

	
	public void getUsersByBirthDate(){

		Calendar calendar = Calendar.getInstance();
		 
		int monthOfBirth = calendar.get(Calendar.MONTH);
		int dayOfBirth = calendar.get(Calendar.DAY_OF_MONTH);
		monthOfBirth = monthOfBirth + 1 ; 
		
		System.out.println("month : " + monthOfBirth + " / day : " + dayOfBirth);
		
		List<User> userList = (List<User>)em.createQuery("select u from User u where day(u.birthDate) =:dayOfBirth and month(u.birthDate) =:monthOfBirth")
		.setParameter("dayOfBirth", dayOfBirth).setParameter("monthOfBirth", monthOfBirth)
		.getResultList();
//		
//		List<User> userList = (List<User>)em.createQuery("select u from User u where day(u.birthDate) <30)
//		.getResultList();
		
		for(User u : userList){
			System.out.println("User name : " + u.getNickName() + "  / birthDate : " + u.getBirthDate());
		}
	}
		
	public void persist(Object obj){
		
		if(obj != null){
			em.persist(obj);
		}
	}
	
	public Object merge(Object obj) {
		
		return em.merge(obj);
	}	
	
	public User findUser(Long id){
		
		User theUser = em.find(User.class, id);
		
		return theUser;
	}
	
	public Group findGroup(Long id){
		
		Group theGroup = em.find(Group.class, id);
		
		return theGroup;
	}
}
