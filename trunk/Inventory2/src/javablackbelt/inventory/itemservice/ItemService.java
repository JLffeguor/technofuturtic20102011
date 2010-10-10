package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.List;

import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ItemService {
	
	public static void dropRandomItem(User user, int level, String cause) {

		/** temporary lists */
		List<ItemType> tempItemList = new ArrayList<ItemType>();
		
		/** add items in the tempItemList if their level is >= than enumItems level */ 
		for (ItemType enumItems : ItemType.values()) {
			if (level >= enumItems.getItemLevel()) {
				tempItemList.add(enumItems);
			}
		}
		/** generate random number between 0 and list size */
		int random = (int) (Math.random() * tempItemList.size());
		Item userItem = new Item(user, tempItemList.get(random));
		userItem.setCause(cause);
		user.addItem(userItem);
		System.out.println(user.getNickName()
				+ " revieved : " + userItem.getItemType().getItemName());
	}

	public static void dropRandomItem(User user, int level, int percent, String cause) {

		if (((int) Math.random()*100) <= percent)
			dropRandomItem(user, level, cause);
	}

	public static void dropItem(User user, ItemType itemType, String cause) {

		/** add an item to the user list */ 
		Item userItem = new Item(user, itemType);
		userItem.setCause(cause);
		user.addItem(userItem);
		System.out.println(user.getNickName()
				+ " recieved : " + userItem.getItemType().getItemName());
	}
}
