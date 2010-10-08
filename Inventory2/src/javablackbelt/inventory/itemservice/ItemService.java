package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.List;

import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ItemService {
	
	/**
	 * @param user
	 * @param level
	 * @param cause
	 */
	
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

		int random = (int) ((Math.random() * (tempItemList.size() - 0)) + 0);

		Item userItem = new Item(user, tempItemList.get(random));
		userItem.setCause(cause);
		user.addItem(userItem);

		System.out.println("l'utilisateur : " + user.getNickName()
				+ " a reçu : " + userItem.getItemType().getItemName());

	}
	
	/**
	 * @param user
	 * @param level
	 * @param percent
	 * @param cause
	 */

	public static void dropRandomItem(User user, int level, int percent,
			String cause) {

		/** generate random number between 0 and 100 */
		
		int random = (int) ((Math.random() * (100 - 0)) + 0);

		System.out.println(random);
		
		if (random <= percent) {
			dropRandomItem(user, level, cause);
		}

		else
			System.out.println("You loose...");
	}

	/**
	 * @param user
	 * @param itemType
	 * @param cause
	 */
	
	public static void dropItem(User user, ItemType itemType, String cause) {

		/** add an item to the user list */ 

		Item userItem = new Item(user, itemType);
		userItem.setCause(cause);
		user.addItem(userItem);

		System.out.println("L'utilisateur : " + user.getNickName()
				+ " a reçu : " + userItem.getItemType().getItemName());
	}
}
