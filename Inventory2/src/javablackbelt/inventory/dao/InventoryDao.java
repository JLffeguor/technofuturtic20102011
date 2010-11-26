package javablackbelt.inventory.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
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

	
	public List<User> getUsersByBirthDate(){
		
		
		Calendar calendar = Calendar.getInstance();
		 
		int monthOfBirth = calendar.get(Calendar.MONTH);
		int dayOfBirth = calendar.get(Calendar.DAY_OF_MONTH);
		
		monthOfBirth = monthOfBirth + 1 ; // for gregorian calendar
		
		List<User> userList = (List<User>)em
		.createQuery("select u from User u where day(u.birthDate) =:dayOfBirth and month(u.birthDate) =:monthOfBirth")
		.setParameter("dayOfBirth", dayOfBirth).setParameter("monthOfBirth", monthOfBirth)
		.getResultList();
		
		return userList;
	}
	
	
	public List<Item> getUsedItems(boolean usedOrNot){

		// Calendar to get the date of today
		Calendar calendar = Calendar.getInstance();		
		Date dateOfToday = calendar.getTime();
		
		// if the choice is to get used items
		if(usedOrNot == false){
								
			List<Item> itemList = (List<Item>)em.createQuery("select i from Item i where i.removalDate <=:dateOfToday")
			.setParameter("dateOfToday", dateOfToday).getResultList();
			
			return itemList;
		}
		
		else {
		
			List<Item> itemList = (List<Item>)em.createQuery("select i from Item i where i.removalDate >:dateOfToday")
			.setParameter("dateOfToday", dateOfToday).getResultList();
			
			return itemList;
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