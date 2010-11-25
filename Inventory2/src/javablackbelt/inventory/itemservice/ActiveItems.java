package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ActiveItems {
	
	private Map<Long, List<Item>> userListActiveItemsMap;
	private Map<Long, List<Item>> groupActiveItemsMap;
	private List<Item> globalItems;

	private static ActiveItems instance = new ActiveItems();

	/** Singleton. */
	
	private ActiveItems(){
		userListActiveItemsMap=new HashMap<Long, List<Item>>();
		groupActiveItemsMap=new HashMap<Long, List<Item>>();
		globalItems=new ArrayList<Item>();
	}

	public static ActiveItems getInstance() {
		return instance;
	}
	
	/**
	 * returns the active item list for a specific user. Return null if no list
	 * update of the user's list of active items (suppress the non-active items
	 * thanks to the removal date)
	 */
	synchronized public List<Item> getActiveItems(User user) {
		
		// given a map of users + a list of items (loaded below)  , 
		// we shall check for a given user , whether its items are still active : removal date is before today    
		// a new list is build with the today-active items , the old one is cleaned and replaced with the new 
		// if no more active items for this user , the list is cleaned then the user is cleaned from the map . 
		
		List<Item> activeItemList = new ArrayList<Item>();
		Long mapKey = user.getUserId();

		// if the list in the Map(userListActiveItemsMap) for the specified mapKey(User) is null, return null
		if (userListActiveItemsMap.get(mapKey) == null) {
			return null;
			
		} 
		else {

			// Call to the method itemStillActivated with the user list in parameter
			activeItemList = itemsStillActivated(userListActiveItemsMap
					.get(mapKey));
			
			// Wipe the list in the Map(userListActiveItemsMap) for the specified mapKey(User)
			userListActiveItemsMap.get(mapKey).clear(); 

			
			if (activeItemList != null) {

				// if the activeItemList is not null, add this one in the userListActiveItemsMap on the specified mapKey(User)
				userListActiveItemsMap.put(mapKey, activeItemList);
				return activeItemList;
			} 
			else {

				// remove the User with the specified mapKey from the userListActiveItemsMap
				userListActiveItemsMap.remove(mapKey);
				return null;
			}

		}
	}

	/**
	 * returns the active item list for a specific group. Return null if no list
	 * update of the group's list of active items (suppress the non-active items
	 * thanks to the removal date)
	 */
	synchronized public List<Item> getActiveItems(Group group) {
		
		// same as above for a group 

		List<Item> activeItemList = new ArrayList<Item>();
		Long mapKey = group.getGroupId();

		// if the list in the Map(groupActiveItemsMap) for the specified mapKey(Group) is null, return null
		if (groupActiveItemsMap.get(group.getGroupId()) == null) {
			return null;
		}	
		else {
			List<Item> genericItemList = new ArrayList<Item>();
			genericItemList.addAll(groupActiveItemsMap.get(mapKey));

			// Wipe the list in the Map(groupActiveItemsMap) for the specified mapKey(Group)
			activeItemList = itemsStillActivated(genericItemList);
			groupActiveItemsMap.get(mapKey).clear();

			if (activeItemList != null) {

				// if the activeItemList is not null, add this one in the groupActiveItemsMap on the specified mapKey(Group)
				groupActiveItemsMap.put(mapKey, activeItemList);
				return activeItemList;
			} 
			else {

				// remove the Group with the specified mapKey from the groupActiveItemsMap
				groupActiveItemsMap.remove(mapKey);
				return null;
			}
		}
	}

	
	/** returns the globally active item. Return null if no list */
	synchronized public List<Item> getGlobalActiveItems(ItemType itemType) {
		
		// same as above for all global items 

		List<Item> activeItemList = new ArrayList<Item>();
		activeItemList = itemsStillActivated(globalItems);

		if (activeItemList == null) {
			return null;
		} else {
			return activeItemList;
		}
	}

	
	/** print active item on the itemTypeGroup (Background or image_home) */
	synchronized public List<Item> getActiveItems(ItemType.Group itemTypeGroup) {
		
		// same as above for all items with a given item-type-group  

		// get the list with the items which are still active

		List<Item> activeItemList = new ArrayList<Item>();
		activeItemList = itemsStillActivated(globalItems);
		
		return activeItemList;
	}

	
	/** returns the items still activated */
	synchronized public List<Item> itemsStillActivated(List<Item> ListOfItemsFromMap) {

		// Creating a new list to get active items
		
		List<Item> activeItemList = new ArrayList<Item>();
		
		for (Item i : ListOfItemsFromMap) {

			// if the date of today is higher than the removalDate of the items in the ListOfItemsFromMap

			if (!(new Date().after(i.getRemovalDate()))) {
				activeItemList.add(i);
			}
		}
		return activeItemList;
	}
	
	
	///////////// Methods to add an item in a Map (User or Group) or in the globalList ////////
	///////////// Methods to add an item in a Map (User or Group) or in the globalList ////////
	///////////// Methods to add an item in a Map (User or Group) or in the globalList ////////
	///////////// Methods to add an item in a Map (User or Group) or in the globalList ////////

	/** Don't call this directly, go through ItemService.activateItemOn... */
	synchronized public void addItemToUserListActiveItemsMap(Item item,
			User user) {
		Long userId = user.getUserId();

		List<Item> userItemsList = new ArrayList<Item>();
		userItemsList = userListActiveItemsMap.get(userId);

		// Create list if no list yet.
		if (userItemsList == null) {

			// ... /** creation of userId in the Map */

			userItemsList = new ArrayList<Item>();
			userListActiveItemsMap.put(userId, userItemsList);
		}
		userItemsList.add(item);

	}

	
	/** Don't call this directly, go through ItemService.activateItemOn... */
	synchronized public void addItemToGroupActiveItemsMap(Item item, Group group) {
		long groupId = group.getGroupId();
		List<Item> groupItemsList = groupActiveItemsMap.get(groupId);
		if (groupItemsList == null) {

			/** creation of userId in the Map */

			groupItemsList = new ArrayList<Item>();
			groupActiveItemsMap.put(groupId, groupItemsList);
		}
		groupItemsList.add(item);
	}

	
	
	/** Don't call this directly, go through ItemService.activateItemOn... */
	/** Replace active item by a new active item (background or image_home) */
	synchronized public void addItemToGloballyActiveItems(Item item) {
		
		if(globalItems == null){
			globalItems.add(item);
		} 
		
		else{
			
			for (Item i : globalItems) {
				if (i.getItemType().getItemTypeGroup() == item.getItemType()
						.getItemTypeGroup()) {
					globalItems.set(globalItems.indexOf(i), item);
				}
				
				else{
					globalItems.add(i); 
				}
			}
		}
	}
	
	public List<Item> getGlobalItemsList(){
		return globalItems;
	}

	
	
}