package javablackbelt.inventory.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User class.
 * A user has a nick name and a list of items
 */
public class User  {

	private static long countUsers = 0 ;
	
	private long userId;
	private String nickName;
	private List<Item> inventoryItems = new ArrayList<Item>();

	public User(String nickName) {
		this.nickName = nickName;
		this.userId = ++User.countUsers;	
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
