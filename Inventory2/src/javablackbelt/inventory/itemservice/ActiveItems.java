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

	private ActiveItems() {
		userListActiveItemsMap = new HashMap<Long, List<Item>>();
		groupActiveItemsMap = new HashMap<Long, List<Item>>();
		globalItems = new ArrayList<Item>();
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

		List<Item> activeItemList = new ArrayList<Item>();
		Long mapKey = user.getUserId();

		// if the list in the Map(userListActiveItemsMap) for the specified mapKey(User) is null, return null
		if (userListActiveItemsMap.get(mapKey) == null) {
			return null;
		} 
		
		else {

			// Call to the method itemStillActivated which will return a cleaned list (only active items)
			// with the user list(userListActiveItemsMap.get(mapKey)) in parameter
			activeItemList = itemsStillActivated(userListActiveItemsMap.get(mapKey));
			
			// if the list returned is not empty
			if(!(activeItemList.isEmpty())){

				
				// clear the list in the Map and add into this one the activeItemList
				userListActiveItemsMap.get(mapKey).clear();
				userListActiveItemsMap.get(mapKey).addAll(activeItemList);

				// return the list
				return userListActiveItemsMap.get(mapKey);
			}

			else{
				
				// remove the user's mapKey from the Map (remove the user)
				userListActiveItemsMap.remove(mapKey);
				System.out.println("getActiveItems output is null");
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

		// Instantiate the list is not useful but better for reading
		List<Item> activeItemList = new ArrayList<Item>();
		Long mapKey = group.getGroupId();

		// if the list in the Map(groupActiveItemsMap) for the specified mapKey(Group) is null, return null
		if (groupActiveItemsMap.get(mapKey) == null) {
			return null;
		} 
		
		else {
			
			// Call to the method itemStillActivated which will return a cleaned list (only active items)
			// with the group list(groupActiveItemsMap(mapKey)) in parameter
			activeItemList = itemsStillActivated(groupActiveItemsMap.get(mapKey));
			
			// if the list is not empty
			if(!(activeItemList.isEmpty())){
				
				// clear the list in the Map and add into this one the activeItemList
				groupActiveItemsMap.get(mapKey).clear();
				groupActiveItemsMap.get(mapKey).addAll(activeItemList);
				
				// return the cleaned list
				return groupActiveItemsMap.get(mapKey);
			}
			
			else{
				
				// remove the group's mapKey from the Map (remove the user)
				groupActiveItemsMap.remove(mapKey);
				System.out.println("getActiveItems output is null");
				return null;
			}
		}
	}

	/** returns the globally active item. Return null if no list */
	synchronized public List<Item> getGlobalActiveItems(ItemType itemType) {

		// same as above for all global items
		System.out.println("Global Items list - size = " + globalItems.size());
		for (Item g : globalItems) {
			System.out.println("Item type" + g.getItemType() + " Creation : "
					+ g.getCreationDate());

		}
		
		// if the list returned by the itemsStillActivated method is null, return null
		if (itemsStillActivated(globalItems) == null) {
			return null;
		} 
		
		// else return the global list cleaned
		else {
			return itemsStillActivated(globalItems);
		}
	}

	/** print active item on the itemTypeGroup (Background or image_home) */
	synchronized public List<Item> getActiveItems(ItemType.Group itemTypeGroup) {

		// same as above for all items with a given item-type-group

		// get the list with the items which are still active

		System.out.println("Global Items list - size = " + globalItems.size());
		for (Item g : globalItems) {
			System.out.println("Item type" + g.getItemType() + " Creation : "
					+ g.getCreationDate());
		}

		// return the global list cleaned
		return itemsStillActivated(globalItems);
	}

	/** returns the items still activated */
	synchronized public List<Item> itemsStillActivated(
			List<Item> ListOfItemsFromMap) {

		// Creating a new list to get active items

		List<Item> activeItemList = new ArrayList<Item>();

		for (Item i : ListOfItemsFromMap) {

			// if the date of today is higher than the removalDate of the items in the ListOfItemsFromMap

			if ((new Date().before(i.getRemovalDate()))) {
				activeItemList.add(i);
			}
		}
		return activeItemList;
	}

	/////////// Methods to add an item in a Map (User or Group) or in the globalList ////////
	/////////// Methods to add an item in a Map (User or Group) or in the globalList ////////
	/////////// Methods to add an item in a Map (User or Group) or in the globalList ////////
	/////////// Methods to add an item in a Map (User or Group) or in the globalList ////////

	/** Don't call this directly, go through ItemService.activateItemOn... */
	synchronized public void addItemToUserListActiveItemsMap(Item item,	User user) {
		
		Long userId = user.getUserId();
		
		// Create list if no list yet.
		if (userListActiveItemsMap.get(userId) == null) {

			// ... /** creation of userId in the Map */

			List<Item> userItemsList = new ArrayList<Item>();
			userItemsList.add(item);
			
			userListActiveItemsMap.put(userId, userItemsList);
		}
		
		else{
			userListActiveItemsMap.get(userId).add(item);
		}
	}

	/** Don't call this directly, go through ItemService.activateItemOn... */
	synchronized public void addItemToGroupActiveItemsMap(Item item, Group group) {
		
		long groupId = group.getGroupId();
		
		if (groupActiveItemsMap.get(groupId) == null) {

			/** creation of userId in the Map */

			List<Item> groupItemsList = new ArrayList<Item>();
			groupItemsList.add(item);
			
			groupActiveItemsMap.put(groupId, groupItemsList);
		}
		
		else{
			groupActiveItemsMap.get(groupId).add(item);
		}
	}

	/** Don't call this directly, go through ItemService.activateItemOn... */
	/** Replace active item by a new active item (background or image_home) */
	synchronized public void addItemToGloballyActiveItems(Item item) {

		// Creating a temporary list
		
		List<Item> tempActiveItemsList = new ArrayList<Item>();

		// if the global list is empty, add the first item
		
		if (globalItems == null) {
			globalItems.add(item);
		}

		else {

			// Copy the global list into the tempActiveItemsList
			// to avoid ConcurrentModificationException in the loop below
			
			for (Item i : globalItems) {
				tempActiveItemsList.add(i);
			}

			for (Item i : tempActiveItemsList) {

				// If the Item in the tempActiveItemsList is of the same type as the input Item, overwrite it
				
				if (i.getItemType().getItemTypeGroup() == item.getItemType()
						.getItemTypeGroup()) {
					globalItems.set(globalItems.indexOf(i), item);
				}

				else {
					globalItems.add(item); // add into the global list
				}
			}
		}
	}

	public List<Item> getGlobalItemsList() {
		return globalItems;
	}
}