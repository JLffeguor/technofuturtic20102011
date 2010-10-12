package javablackbelt.inventory.test.main;

import javablackbelt.inventory.itemservice.ItemService;
import javablackbelt.inventory.model.Group;
import javablackbelt.inventory.model.ItemType;
import javablackbelt.inventory.model.User;

public class Main {

	public static void main(String[] args) {
			
		Group g1 = new Group("Tech No Future Team");
		
		///// Users creation
		User u1 = new User("Pierre"); 
		User u2 = new User("Mat"); 
		User u3 = new User("André"); 
		User u4 = new User("Lebrun"); 
		User u5 = new User("Gaetan"); 
		User u6 = new User("Julien"); 
		
		
		///// Add users into the group
		g1.addUserToGroup(u1);
		g1.addUserToGroup(u2);
		g1.addUserToGroup(u3);
		g1.addUserToGroup(u4);
		
		
		/////////////// Display group Users
		System.out.println("User of group : " + g1.getGroupName());
		System.out.println("------------------------------------");
		g1.displayListOfUser();

		System.out.println("\n");
		
		/////////////// Display independents Users
		System.out.println("Independent users : ");
		System.out.println("------------------");
		System.out.println(u5.getNickName() + ", " + u5.getUserId());
		System.out.println(u6.getNickName() + ", " + u6.getUserId());
		System.out.println("\n");
		
		/////////////// Test random drop
		System.out.println("Dropping an item randomly :");
		System.out.println("--------------------------");
		
		ItemService.dropRandomItem(u1, 1, "Orange belt!");
		ItemService.dropRandomItem(u2, 1, "Welcome to BlackBeltFactory");
		ItemService.dropRandomItem(u3, 2, "Happy birthday " + u3.getNickName() + "!");
		ItemService.dropRandomItem(u4, 1, "Exam succeded...");
		ItemService.dropRandomItem(u5, 3, "Happy birthday " + u5.getNickName() + "!");
		System.out.println("\n");
		
		///////////// Drop an item randomly, with a percent of chances to win...
		System.out.println("Dropping an item randomly, with a percent of chances to win it :");
		System.out.println("---------------------------------------------------------------");
		
		ItemService.dropRandomItem(u6, 1, 60, "Happy birthday");
		System.out.println("\n");
		
		
		//////////// Drop specific items
		System.out.println("Dropping a specific item on a user :");
		System.out.println("-----------------------------------");
		
		ItemService.dropItem(u1, ItemType.BAKG_RICE, "Welcome to the Java intermediate course"); // to activate on the background
		ItemService.dropItem(u2, ItemType.BEER_PINT, "Yellow belt"); // to activate on a user
		ItemService.dropItem(u3, ItemType.BEER_BARREL, "Whole group get their Yellow belt"); // to activate on a group
		ItemService.dropItem(u4, ItemType.ENLARGER, "To have some fun..."); // to activate on a user
		ItemService.dropItem(u5, ItemType.BAKG_NAKED, "To help you to be a " +
				"better Java developer.."); // to activate on the Background
		System.out.println("\n");
		
		
		////////////Send items from User to User
		ItemService.sendItemTo(u1, u1.getInventoryItems().get(u1.getInventoryItems().size()-1) , u2);
		System.out.println("View of " + u1.getNickName() + " 's inventory : " + u1.);
		
		ItemService.sendItemTo(u1, u1.getInventoryItems().get(u1.getInventoryItems().size()-1) , u2);
	
	}
}
