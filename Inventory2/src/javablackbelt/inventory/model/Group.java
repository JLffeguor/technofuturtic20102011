package javablackbelt.inventory.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Group.
 * A group is composed of different users.
 * @author forma710
 *
 */
public class Group {
	   
	
	private static long countGroup = 0 ;
	
	/////////////////////////
	// VARIABLE DECLARATION
	
	private String groupName;
	private List<User> listOfUsers = new ArrayList<User>();
	private long groupId;
	//private List<Item> inventoryItems = new ArrayList<Item>();
	
	///////////////////////////
	// CONSTRUCTOR AND METHODS
	
	/**
	 * Constructor.
	 * @param groupName the name of the group
	 */
	public Group(String groupName) {
		this.groupName = groupName;
		this.groupId = Group.countGroup++;	
	}
	
	
	
	///////////////////////
	// GETTERS AND SETTERS
	
	/**
	 * To get the name of the group.
	 * @return the group name
	 */
	public String getGroupName() {
		return groupName;
	}



	public long getGroupId() {
		return groupId;
	}
	

}
