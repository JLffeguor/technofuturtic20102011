package javablackbelt.inventory.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User class.
 * A user has a nick name and a list of items
 */
public class User {
	
	/////////////////////////
	// VARIABLE DECLARATION
	
	private String nickName;
	private List<Item> inventoryItems = new ArrayList<Item>();
	
	///////////////////////////
	// CONSTRUCTOR AND METHODS
	
	/**
	 * Constructor
	 */
	public User(String nickName) {
		this.nickName = nickName;
	}
	
	/**
	 * To add an item in the list
	 * @param itemToAdd
	 */
	public void addItem(Item itemToAdd) {
		this.inventoryItems.add(itemToAdd);
	}
	
	///////////////////////
	// GETTERS AND SETTERS
	
	/**
	 * Return the name of the user
	 * @return nick name
	 */
	public String getNickName() {
		return nickName;
	}
	
	/**
	 * Get the list of items
	 * @return list of items
	 */
	public List<Item> getInventoryItems() {
		return inventoryItems;
	}
}
