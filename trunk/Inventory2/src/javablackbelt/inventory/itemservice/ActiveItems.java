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

//
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

		List<Item> tempItemList = new ArrayList<Item>();
		Long mapKey = user.getUserId();

		// if no list, return null
		if (userListActiveItemsMap.get(mapKey) == null) {
			return null;
		} else {

			// List<Item> genericItemList = new ArrayList<Item>();
			// genericItemList.addAll(userListActiveItemsMap.get(mapKey));

			// get items which are still active

			tempItemList = itemsStillActivated(userListActiveItemsMap
					.get(mapKey));
			userListActiveItemsMap.get(mapKey).clear(); // Wipe the list of the
														// "mapKey user"

			if (tempItemList != null) {

				// add the tempItemList into the userListActiveItemsMap on the
				// "mapKey user" occurrence

				userListActiveItemsMap.put(mapKey, tempItemList);
				return tempItemList;
			} else {

				// removes the "mapKey user" from the map

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

		List<Item> tempItemList = new ArrayList<Item>();
		Long mapKey = group.getGroupId();

		// if no list, return null
		if (groupActiveItemsMap.get(group.getGroupId()) == null) {
			return null;
		} else {
			List<Item> genericItemList = new ArrayList<Item>();
			genericItemList.addAll(groupActiveItemsMap.get(mapKey));

			// get the list with the items which are still active

			tempItemList = itemsStillActivated(genericItemList);
			groupActiveItemsMap.get(mapKey).clear(); // Wipe the map of the
														// "mapKey user"

			if (tempItemList != null) {

				// add the list(tempItemList) with active items into the
				// map(groupActiveItemsMap) on the specified key

				groupActiveItemsMap.put(mapKey, tempItemList);
				return tempItemList;
			} else {

				// delete the key associated with the list(tempItemList) if this
				// one is null

				groupActiveItemsMap.remove(mapKey);
				return null;
			}
		}
	}

	
	/** returns the globally active item. Return null if no list */
	synchronized public List<Item> getGlobalActiveItems(ItemType itemType) {

		List<Item> tempItemList = new ArrayList<Item>();
		tempItemList = itemsStillActivated(globalItems);

		if (tempItemList == null) {
			return null;
		} else {
			return tempItemList;
		}
	}

	
	/** print active item on the itemTypeGroup (Background or image_home) */
	synchronized public void getActiveItems(ItemType.Group itemTypeGroup) {

		// get the list with the items which are still active

		List<Item> tempItemList = new ArrayList<Item>();
		tempItemList = itemsStillActivated(globalItems);

		for (Item i : tempItemList) {

			// if the type from items in tempItemList is == to the parameter
			// type

			if (i.getItemType().getItemTypeGroup() == itemTypeGroup) {
				System.out.println(i.getItemType().getItemDescription());
			}
		}
	}

	
	/** returns the items still activated */
	synchronized public List<Item> itemsStillActivated(List<Item> genericItemList) {

		List<Item> tempItemList = new ArrayList<Item>();

		for (Item i : genericItemList) {

			// if the date of items into the genericList is not exceeded

			if (!(new Date().after(i.getRemovalDate()))) {
				tempItemList.add(i);
			}
		}
		return tempItemList;
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
		
		for (Item i : globalItems) {
			if (i.getItemType().getItemTypeGroup() == item.getItemType()
					.getItemTypeGroup()) {
				globalItems.set(globalItems.indexOf(i), item);
			}
		}
	}
	
	public List<Item> getGlobalItemsList(){
		return globalItems;
	}

	
	
}