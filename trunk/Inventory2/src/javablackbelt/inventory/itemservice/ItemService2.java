package javablackbelt.inventory.itemservice;

import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class ItemService2 {

	public void sendItemTo(User sender,Item item, User receiver){

		//Check of Item destination (GLOBAL, GROUP, USER)
		if (item.getItemType().getTargetType()==ItemType.TargetType.GLOBAL){
			throw new RuntimeException("Global items cannot be sent to a user or a group ! ");
		} 
		if (item.getItemType().getTargetType()==ItemType.TargetType.GROUP){
			throw new RuntimeException("Group items cannot be sent to a user ! ");
		} 
		
		//add item in User's Inventory
		receiver.addItem(item);
		System.out.println(receiver.getNickName()+ " jsut receive the object "
				+item.getItemType().getItemName()+ " from "+sender.getNickName());
		
		//remove item from sender'inventory
		sender.removeItem(item);
	}
	
	
	
	public void activateItemGlobally(User sender, Item item) {
		
		if (item.getItemType().getTargetType()==ItemType.TargetType.USER){
			throw new RuntimeException("Global items cannot be activate on a user ! ");
		} 
		if (item.getItemType().getTargetType()==ItemType.TargetType.GROUP){
			throw new RuntimeException("Group items cannot be activate on a group ! ");
		} 
		
		// initialization of activationDate and removalDate  
		item.setActivationDate();
		ActiveItems2.getInstance().addItemToGloballyActiveItems(item);
		
		
		// activation item on the site
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName());
		
	}

	public void activateItemOnGroup(User sender, Item item, Group receiver) {
		
		if (item.getItemType().getTargetType()==ItemType.TargetType.USER){
			throw new RuntimeException("Group items cannot be activate on a user ! ");
		} 
		if (item.getItemType().getTargetType()==ItemType.TargetType.GLOBAL){
			throw new RuntimeException("Group items cannot be activate globally ! ");
		} 
		
		// initialization of activationDate and removalDate  
		item.setActivationDate();
		ActiveItems2.getInstance().addItemToGroupActiveItemsMap(item, receiver);
		
		// activation item on the site
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName());
		
	}

	public void activateItemOnUser(User sender, Item item, User receiver) {

		if (item.getItemType().getTargetType()==ItemType.TargetType.GLOBAL){
			throw new RuntimeException("User items cannot be activate globally ! ");
		} 
		if (item.getItemType().getTargetType()==ItemType.TargetType.GROUP){
			throw new RuntimeException("User items cannot be activate on a group ! ");
		} 
		
		// initialization of activationDate and removalDate  
		item.setActivationDate();
		ActiveItems2.getInstance().addItemToUserListActiveItemsMap(item, receiver);
		
		// activation item on the site
		System.out.println(item.getItemType().getItemDescription()
				+ " has been activated by " + sender.getNickName());
		
	}

			
		}
