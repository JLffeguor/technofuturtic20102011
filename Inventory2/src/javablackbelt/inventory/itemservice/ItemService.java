package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.List;

import javablackbelt.inventory.dao.InventoryDao;
import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ItemService {

	@Autowired
	// From Java v6, use @Resource
	private InventoryDao inventoryDao; // Injected by Spring.
	
	// Method to display users with a specified birthDate
	
	public void displayUsersByBirthDate(){
		
		for(User us : inventoryDao.getUsersByBirthDate()){
					
			System.out.println("\nUser name : " + us.getNickName() + "  / birthDate : " + us.getBirthDate());
			dropRandomItem(us,1,"Happy birthday");
		}
	}
	
	public void displayUsedItems(){
		
		for(Item it : inventoryDao.getUsedItems()){
			System.out.println("\nItem type : " + it.getItemType() + " is already used");
		}
	}

	
	// Method to display list of active items
	public void displayListOfActiveItems(List<Item> list) {

		if (list != null) {
			for (Item it : list) {
				System.out.println("ItemType : " + it.getItemType());
			}
		} else {
			System.out.println("Empty List ");
		}

	}

	/**
	 * Selects an ItemType with level = given level. Creates a new Item and puts
	 * it in the inventory of the given user.
	 */
	public Item dropRandomItem(User user, int level, String cause) {

		// temporary lists
		List<ItemType> itemsWithLevel = new ArrayList<ItemType>();

		// add items in the tempItemList if their level is >= than enumItems
		// level
		for (ItemType enumItems : ItemType.values()) {
			if (level == enumItems.getItemLevel()) {
				itemsWithLevel.add(enumItems);
			}
		}

		// Randomly selects the itemType
		ItemType itemType = itemsWithLevel
				.get((int) (Math.random() * itemsWithLevel.size()));

		// Creates the item.
		Item userItem = new Item(user, itemType);
		userItem.setCause(cause);
		user.addItem(userItem);

		// TODO: delete me.
		System.out.println(user.getNickName() + " recieved : "
				+ userItem.getItemType().getItemName() + " of the type :"
				+ userItem.getItemType() + " for cause : " + userItem.getCause());

		inventoryDao.persist(userItem);

		return userItem;
	}

	/**
	 * Selects an ItemType with level = given level.
	 * 
	 * @param percent
	 *            between 1 and 100, chances to drop something. Creates a new
	 *            Item and puts it in the inventory of the given user.
	 */
	public Item dropRandomItem(User user, int level, int percent,
			String cause) {

		if (((int) (Math.random() * 100) + 1) <= percent) {

			return dropRandomItem(user, level, cause);
		} else {
			return null;
		}
	}

	// doc .... when is it typically used ?
	public Item dropItem(User user, ItemType itemType, String cause) {

		/** add an item to the user itemset */
		Item userItem = new Item(user, itemType);
		userItem.setCause(cause);
		user.addItem(userItem);
		System.out.println(user.getNickName() + " recieved : "
				+ userItem.getItemType().getItemName());
		inventoryDao.persist(userItem);
		return userItem;
	}

	/** send an user's item or a globally item to a user */
	public void sendItemTo(User sender, Item item, User receiver) {

		/** Check of Item destination (GLOBAL, GROUP, USER) */
		if (item.getItemType().getTargetType() == ItemType.TargetType.GROUP) {
			throw new RuntimeException(
					"Group items cannot be sent to a user ! ");
		}
		/** add item in User's Inventory */
		receiver.addItem(item);
		System.out.println(receiver.getNickName()
				+ " just received the object "
				+ item.getItemType().getItemName() + " from "
				+ sender.getNickName());
		/** remove item from sender'inventory */
		sender.removeItem(item);
		
		inventoryDao.merge(item);
		// return item ;
	}

	/** activate a globally item by a user */
	public void activateItemGlobally(User sender, Item item) {

		if (item.getItemType().getTargetType() == ItemType.TargetType.USER) {
			throw new IllegalArgumentException(
					"Global items cannot be activated on a user ! ");
		}
		if (item.getItemType().getTargetType() == ItemType.TargetType.GROUP) {
			throw new IllegalArgumentException(
					"Global items cannot be activated on a group ! ");
		}

		/** initialization of activationDate and removalDate */
		item.setActivationDate();

		/** add an actived item to the globalItemsList */
		ActiveItems.getInstance().addItemToGloballyActiveItems(item);

		/** activation item on the site (background or home page) */
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName() + " on the "
				+ item.getItemType().getItemTypeGroup() + "\n");
		inventoryDao.merge(item);
	}

	/** activate an item on a group by a user */
	public void activateItemOnGroup(User sender, Item item,
			Group receiver) {

		if (item.getItemType().getTargetType() == ItemType.TargetType.USER) {
			throw new IllegalArgumentException(
					"Group items cannot be activated on a user ! ");
		}
		if (item.getItemType().getTargetType() == ItemType.TargetType.GLOBAL) {
			throw new IllegalArgumentException(
					"Group items cannot be activated globally ! ");
		}
		/** initialization of activationDate and removalDate */
		item.setActivationDate();

		/** initialization of groupTarget */
		item.setGroupTarget(receiver);

		/** add an actived item to the groupActiveItemsMap */
		ActiveItems.getInstance().addItemToGroupActiveItemsMap(item, receiver);

		/** activation item on the site */
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName()
				+ " on group " + receiver.getGroupName() + "\n");
		
		inventoryDao.merge(item);
	}

	/** activate an item on a user by another user */
	public void activateItemOnUser(User sender, Item item, User receiver) {

		if (item.getItemType().getTargetType() == ItemType.TargetType.GLOBAL) {
			throw new IllegalArgumentException(
					"User items cannot be activated globally ! ");
		}
		if (item.getItemType().getTargetType() == ItemType.TargetType.GROUP) {
			throw new IllegalArgumentException(
					"User items cannot be activated on a group ! ");
		}
		/** initialization of activationDate and removalDate */
		item.setActivationDate();

		/** initialization of userTarget */
		item.setUserTarget(receiver);

		/** add an actived item to the UsertActiveItemsMap */
		ActiveItems.getInstance().addItemToUserListActiveItemsMap(item,
				receiver);
		/** activation item on the site */
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName() + " on "
				+ receiver.getNickName() + "\n");
		
		inventoryDao.merge(item);
	}
}
