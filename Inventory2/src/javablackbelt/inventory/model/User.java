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
		this.userId = ++countUsers;	
	}

	public void addItem(Item itemToAdd) {
		inventoryItems.add(itemToAdd);
		itemToAdd.setOwner(this); // changement du proprietaire de l'objet
	}

	public void removeItem(Item itemToRemove) {
		inventoryItems.remove(itemToRemove);  // supprime un objet de l'inventaire d'un user
	}

	public String getNickName() {
		return nickName;
	}
	
	public long getUserId() {
		return userId;
	}
}
