package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ActiveItems {

	private Map<Long, List<Item>> userListActiveItemsMap = new HashMap<Long, List<Item>>();
	private Map<Long, List<Item>> groupActiveItemsMap = new HashMap<Long, List<Item>>();
	private List<Item> globalItems = new ArrayList<Item>();

	private static ActiveItems instance = new ActiveItems(); // Singleton.

	private Date actualDate = new Date();

	public void getActiveItems(ItemType.Group itemTypeGroup) {

		for(Item i : globalItems) {

		}
	}

	public void getActiveItems(User user) {

		for (Item i : userListActiveItemsMap.get(user.getUserId())) {

			if(actualDate.compareTo(i.getRemovalDate()) <= 0){
				userListActiveItemsMap.remove(i);
				user.removeItem(i);
			} else
				System.out.println(i.getItemType().getItemDescription());
		}
	}

	public void getGlobalActiveItems(ItemType itemType) {

		
		for (Item i : globalItems) {

			if(actualDate.compareTo(i.getRemovalDate()) <= 0){
				globalItems.remove(i);
			} else if (itemType.equals(i.getItemType())) {
				System.out.println(i.getItemType().getItemName());
			}
		}
	}

	public ActiveItems getInstance() {
		return instance;
	}
}
