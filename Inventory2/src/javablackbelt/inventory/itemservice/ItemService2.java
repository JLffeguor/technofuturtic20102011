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
		System.out.println(receiver.getNickName()+ " vient de recevoir dans son inventaire "
				+item.getItemType().getItemName()+ " de la part de "+sender.getNickName());
		
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
		
		// activation item on the site
		System.out.println(item.getItemType().getItemDescription()
				+ " vient d'être activé par " + sender.getNickName());
		
	}

	public void activateItemOnGroup(User sender, Item item, Group group) {
		
		if (item.getItemType().getTargetType()==ItemType.TargetType.USER){
			throw new RuntimeException("Group items cannot be activate on a user ! ");
		} 
		if (item.getItemType().getTargetType()==ItemType.TargetType.GLOBAL){
			throw new RuntimeException("Group items cannot be activate globally ! ");
		} 
		
		// initialization of activationDate and removalDate  
		item.setActivationDate();
		
		// activation item on the site
		System.out.println(item.getItemType().getItemDescription()
				+ " vient d'être activé par " + sender.getNickName());
		
	}

	public void activateItemOnUser(User sender, Item item, User user) {

		if (item.getItemType().getTargetType()==ItemType.TargetType.GLOBAL){
			throw new RuntimeException("User items cannot be activate globally ! ");
		} 
		if (item.getItemType().getTargetType()==ItemType.TargetType.GROUP){
			throw new RuntimeException("User items cannot be activate on a group ! ");
		} 
		
		// initialization of activationDate and removalDate  
		item.setActivationDate();
		
		// activation item on the site
		System.out.println(item.getItemType().getItemDescription()
				+ " vient d'être activé par " + sender.getNickName());
		
	}

			
		}
