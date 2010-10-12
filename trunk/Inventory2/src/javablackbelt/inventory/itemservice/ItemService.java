package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.List;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ItemService {

	/** Selects an ItemType with level = given level.
	 * Creates a new Item and puts it in the inventory of the given user.*/
	public static void dropRandomItem(User user, int level, String cause) {

		// temporary lists 
		List<ItemType> itemsWithLevel = new ArrayList<ItemType>();

		// add items in the tempItemList if their level is >= than enumItems level
		for (ItemType enumItems : ItemType.values()) {
			if (level == enumItems.getItemLevel()) {
				itemsWithLevel.add(enumItems);
			}
		}
		
		// Randomly selects the itemType
		ItemType itemType = itemsWithLevel.get( (int) (Math.random() * itemsWithLevel.size()) );
		
		// Creates the item.
		Item userItem = new Item(user, itemType);
		userItem.setCause(cause);
		user.addItem(userItem);
		
		// TODO: delete me.
		System.out.println(user.getNickName() + " revieved : "
				+ userItem.getItemType().getItemName());
	}

	/** Selects an ItemType with level = given level.
	 * @param percent between 1 and 100, chances to drop something.
	 * Creates a new Item and puts it in the inventory of the given user.*/
	public static void dropRandomItem(User user, int level, int percent, String cause) {

		if ( ((int) (Math.random()* 100) + 1) <= percent)
			dropRandomItem(user, level, cause);
	}

	//doc .... when is it typically used ?
	public static void dropItem(User user, ItemType itemType, String cause) {

		/** add an item to the user list */
		Item userItem = new Item(user, itemType);
		userItem.setCause(cause);
		user.addItem(userItem);
		System.out.println(user.getNickName() + " recieved : "
				+ userItem.getItemType().getItemName());
	}

	
	
	
	/** send an user's item or a globally item to a user */
	public static void sendItemTo(User sender, Item item, User receiver) {

		/** Check of Item destination (GLOBAL, GROUP, USER) */
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

	/** activate a globally item by a user */
	public static void activateItemGlobally(User sender, Item item) {

		if (item.getItemType().getTargetType() == ItemType.TargetType.USER) {
			throw new IllegalArgumentException(
					"Global items cannot be activate on a user ! ");
		}
		if (item.getItemType().getTargetType() == ItemType.TargetType.GROUP) {
			throw new IllegalArgumentException(
					"Group items cannot be activate on a group ! ");
		}

		/** initialization of activationDate and removalDate */
		item.setActivationDate();
		
		/** add an actived item to the globalItemsList */
		ActiveItems.getInstance().addItemToGloballyActiveItems(item);
		
		/** activation item on the site (background or home page) */
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName() + " on the "
				+ item.getItemType().getItemTypeGroup() + "\n");
	}

	
	
	/** activate an item on a group by a user */
	public static void activateItemOnGroup(User sender, Item item, Group receiver) {

		if (item.getItemType().getTargetType() == ItemType.TargetType.USER) {
			throw new IllegalArgumentException(
					"Group items cannot be activate on a user ! ");
		}
		if (item.getItemType().getTargetType() == ItemType.TargetType.GLOBAL) {
			throw new IllegalArgumentException(
					"Group items cannot be activate globally ! ");
		}
		/** initialization of activationDate and removalDate */
		item.setActivationDate();
		
		/** add an actived item to the groupActiveItemsMap */
		ActiveItems.getInstance().addItemToGroupActiveItemsMap(item, receiver);
		
		/** activation item on the site */
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName() + " on group " + receiver.getGroupName() + "\n");
	}

	
	/** activate an item on a user by another user */
	public static void activateItemOnUser(User sender, Item item, User receiver) {

		if (item.getItemType().getTargetType() == ItemType.TargetType.GLOBAL) {
			throw new IllegalArgumentException(
					"User items cannot be activate globally ! ");
		}
		if (item.getItemType().getTargetType() == ItemType.TargetType.GROUP) {
			throw new IllegalArgumentException("User items cannot be activate on a group ! ");
		}
		/** initialization of activationDate and removalDate */
		item.setActivationDate();
		
		/** add an actived item to the UsertActiveItemsMap */
		ActiveItems.getInstance().addItemToUserListActiveItemsMap(item,
				receiver);
		/** activation item on the site */
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName() + " on " + receiver.getNickName() + "\n");
	}	
}
