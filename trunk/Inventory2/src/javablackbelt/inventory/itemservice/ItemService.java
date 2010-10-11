package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.List;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ItemService {

	public static void dropRandomItem(User user, int level, String cause) {

		/** temporary lists */
		List<ItemType> tempItemList = new ArrayList<ItemType>();

		/**
		 * add items in the tempItemList if their level is >= than enumItems
		 * level
		 */
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
		System.out.println(user.getNickName() + " revieved : "
				+ userItem.getItemType().getItemName());
	}

	public static void dropRandomItem(User user, int level, int percent,
			String cause) {

		if (((int) Math.random() * 100) <= percent)
			dropRandomItem(user, level, cause);
	}

	public static void dropItem(User user, ItemType itemType, String cause) {

		/** add an item to the user list */
		Item userItem = new Item(user, itemType);
		userItem.setCause(cause);
		user.addItem(userItem);
		System.out.println(user.getNickName() + " recieved : "
				+ userItem.getItemType().getItemName());
	}

	public void sendItemTo(User sender, Item item, User receiver) {

		/** Check of Item destination (GLOBAL, GROUP, USER) */
		if (item.getItemType().getTargetType() == ItemType.TargetType.GLOBAL) {
			throw new RuntimeException(
					"Global items cannot be sent to a user or a group ! ");
		}
		if (item.getItemType().getTargetType() == ItemType.TargetType.GROUP) {
			throw new RuntimeException(
					"Group items cannot be sent to a user ! ");
		}
		/** add item in User's Inventory */
		receiver.addItem(item);
		System.out.println(receiver.getNickName() + " just receive the object "
				+ item.getItemType().getItemName() + " from "
				+ sender.getNickName());
		/** remove item from sender'inventory */
		sender.removeItem(item);
	}

	public void activateItemGlobally(User sender, Item item) {

		if (item.getItemType().getTargetType() == ItemType.TargetType.USER) {
			throw new RuntimeException(
					"Global items cannot be activate on a user ! ");
		}
		if (item.getItemType().getTargetType() == ItemType.TargetType.GROUP) {
			throw new RuntimeException(
					"Group items cannot be activate on a group ! ");
		}

		/** initialization of activationDate and removalDate */
		item.setActivationDate();
		ActiveItems.getInstance().addItemToGloballyActiveItems(item);
		/** activation item on the site */
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName() + " on the "
				+ item.getItemType().getItemTypeGroup());
	}

	public void activateItemOnGroup(User sender, Item item, Group receiver) {

		if (item.getItemType().getTargetType() == ItemType.TargetType.USER) {
			throw new RuntimeException(
					"Group items cannot be activate on a user ! ");
		}
		if (item.getItemType().getTargetType() == ItemType.TargetType.GLOBAL) {
			throw new RuntimeException(
					"Group items cannot be activate globally ! ");
		}
		/** initialization of activationDate and removalDate */
		item.setActivationDate();
		ActiveItems.getInstance().addItemToGroupActiveItemsMap(item, receiver);
		/** activation item on the site */
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName());
	}

	public void activateItemOnUser(User sender, Item item, User receiver) {

		if (item.getItemType().getTargetType() == ItemType.TargetType.GLOBAL) {
			throw new RuntimeException(
					"User items cannot be activate globally ! ");
		}
		if (item.getItemType().getTargetType() == ItemType.TargetType.GROUP) {
			throw new RuntimeException(
					"User items cannot be activate on a group ! ");
		}
		/** initialization of activationDate and removalDate */
		item.setActivationDate();
		ActiveItems.getInstance().addItemToUserListActiveItemsMap(item,
				receiver);
		/** activation item on the site */
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName());
	}
}
