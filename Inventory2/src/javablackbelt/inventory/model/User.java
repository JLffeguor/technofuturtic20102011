package javablackbelt.inventory.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User class.
 * A user has a nick name and a list of items
 */
@Entity
@Table(name="User_")
public class User  {

	//private static long countUsers = 0 ;
	
	@Id
	@GeneratedValue
	private Long userId;
	
	private String nickName;
	private List<Item> inventoryItems = new ArrayList<Item>();

	public User(String nickName) {
		this.nickName = nickName;
		//this.userId = ++User.countUsers;	
	}

	public void addItem(Item itemToAdd) {
		inventoryItems.add(itemToAdd);
		itemToAdd.setOwner(this); /** change of object owner */
	}

	public void removeItem(Item itemToRemove) {
		inventoryItems.remove(itemToRemove);  /** delete an object from user inventory */
	}

	public String getNickName() {
		return nickName;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public List<Item> getInventoryItems() {
		return this.inventoryItems;
	}
}
