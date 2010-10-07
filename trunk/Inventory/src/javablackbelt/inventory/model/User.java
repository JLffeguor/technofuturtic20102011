package javablackbelt.inventory.model;

import java.util.ArrayList;
import java.util.List;

// User class.
// A user has a nick name and a list of items
public class User {
	
	private String nickName;
	private List<Item> listOfItems = new ArrayList<Item>();
	
	// Constructor
	public User(String inNickName) {
		nickName = inNickName;
	}
	
	// To add an item in the list
	public void addItem(Item itemToAdd) {
		this.listOfItems.add(itemToAdd);
	}
	
	// Return the name of the user 
	public String getNickName() {
		return nickName;
	}
	
	// Return the list of items
	public List<Item> getListOfItems() {
		return listOfItems;
	}
}
