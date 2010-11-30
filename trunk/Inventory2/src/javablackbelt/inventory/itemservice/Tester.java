package javablackbelt.inventory.itemservice;

import java.util.GregorianCalendar;

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
public class Tester {
	
	@Autowired
	// From Java v6, use @Resource
	private InventoryDao inventoryDao; // Injected by Spring.
	
	@Autowired
	private ItemService itemService;
		
	/** 
	 * Creation of data in Database
	 */
	
	public void createData() {
		
			// Groups creation
			Group g1 = new Group("Tech No Future Team");
			Group g2 = new Group("Tech No Future Team Dissidents");

			// Users creation
					
			GregorianCalendar cal = new GregorianCalendar(2010,10,25);		
			GregorianCalendar cal1 = new GregorianCalendar(1953,10,24);			
			GregorianCalendar cal2 = new GregorianCalendar(1998,10,26);	
			
			User pierre = new User("Pierre",cal.getTime());
			User mat = new User("Mat",cal.getTime());
			User andre = new User("André",cal.getTime());
			User lebrun = new User("Lebrun",cal1.getTime());
			User gaetan = new User("Gaetan",cal1.getTime());
			User julien = new User("Julien",cal2.getTime());
			User jerome = new User("Jerome",cal2.getTime());
			User johan = new User("Johan",cal2.getTime());

			// Add users into the group
			g1.addUserToGroup(pierre);
			g1.addUserToGroup(mat);
			g1.addUserToGroup(andre);
			g1.addUserToGroup(johan);
			g2.addUserToGroup(lebrun);
			g2.addUserToGroup(julien);
			g2.addUserToGroup(jerome);

			pierre.addGroupToUser(g1);
			mat.addGroupToUser(g1);
			andre.addGroupToUser(g1);		
			johan.addGroupToUser(g1);

			lebrun.addGroupToUser(g2);
			julien.addGroupToUser(g2);
			jerome.addGroupToUser(g2);

			// persist users and relation
			inventoryDao.persist(pierre);
			inventoryDao.persist(mat);
			inventoryDao.persist(andre);
			inventoryDao.persist(lebrun);
			inventoryDao.persist(gaetan);
			inventoryDao.persist(julien);
			inventoryDao.persist(jerome);
			inventoryDao.persist(johan);
			inventoryDao.persist(g1);
			inventoryDao.persist(g2);
	}
	
	
	public void performTests() {
				
		// dropItem method
		Item specificItem1 = itemService.dropItem(inventoryDao.findUser(1L),ItemType.BEER_BARREL,"Congratulations for you green belt");	
		Item specificItem2 = itemService.dropItem(inventoryDao.findUser(2L),ItemType.CHAMPAIN_GLASS,"Congratulations for you green belt");	
		
		Item specificItem3 = itemService.dropItem(inventoryDao.findUser(3L), ItemType.BEER_BARREL, ""); // for activeGroup		  
		Item specificItem4 = itemService.dropItem(inventoryDao.findUser(5L),ItemType.BEER_PINT,"Congratulations for you green belt"); // for activeOnUser // 		
		
		Item specificItemGlobal = itemService.dropItem(inventoryDao.findUser(4L), ItemType.BAKG_NAKED, "Japan girl naked !"); // for activeGlobally
		Item specificItemGlobal2 = itemService.dropItem(inventoryDao.findUser(5L), ItemType.IMAGE_HOME_24, "Home page for 24h!"); // for activeGlobally
		
		Item specificItemUser = itemService.dropItem(inventoryDao.findUser(6L),ItemType.BEER_PINT, "I just felt like it"); // for activeOnUser 
		Item specificItemGroup = itemService.dropItem(inventoryDao.findUser(7L),ItemType.CHAMPAIN_BARREL,"Congratulations for you green belt"); // for activeOnGroup 
		Item specificItemUser2 = itemService.dropItem(inventoryDao.findUser(7L), ItemType.BEER_PINT, "Happy birthday !"); // for activeOnUser
		
		
		//dropRandomItem method 
		Item randomItem1 = itemService.dropRandomItem(inventoryDao.findUser(2L), 1,"Welcome to BlackBeltFactory"); 
		Item randomItem2 = itemService.dropRandomItem(inventoryDao.findUser(3L), 2, "Exam succeeded...");
		 
		Item randomItem3 = itemService.dropRandomItem(inventoryDao.findUser(4L), 3, "Exam succeeded..."); 
		Item randomItem4 = itemService.dropRandomItem(inventoryDao.findUser(5L), 4, "Welcome ...");
		  
		// dropRandomItem method (with % of chance to get an item)
		Item randomItemWithPerc = itemService.dropRandomItem(inventoryDao.findUser(3L), 1, 0, "Happy birthday");
		  
				  
		// Send and activate methods 
		itemService.sendItemTo(inventoryDao.findUser(2L),randomItem1,inventoryDao.findUser(4L));
		
		itemService.activateItemOnUser(inventoryDao.findUser(2L),specificItemUser,inventoryDao.findUser(5L));
		itemService.activateItemOnUser(inventoryDao.findUser(1L),specificItemUser2,inventoryDao.findUser(2L));
		
		itemService.activateItemOnGroup(inventoryDao.findUser(2L),specificItemGroup,inventoryDao.findGroup(9L)); 
		
		itemService.activateItemGlobally(inventoryDao.findUser(2L),specificItemGlobal);
		itemService.activateItemGlobally(inventoryDao.findUser(3L),specificItemGlobal2);
			  
	
		// getActiveItems methods 
		System.out.println("Active Items of user : " +inventoryDao.findUser(2L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(2L)));
		
		System.out.println("Active Items of user : "+inventoryDao.findUser(1L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(1L)));
		
		System.out.println("Active Items of user : "+inventoryDao.findUser(3L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(3L)));
		System.out.println("Active Items of user : "+inventoryDao.findUser(4L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(4L)));
		System.out.println("Active Items of user : "+inventoryDao.findUser(5L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(5L)));
		System.out.println("Active Items of user : "+inventoryDao.findUser(6L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(6L)));
		System.out.println("Active Items of user : "+inventoryDao.findUser(7L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(7L)));
		// Groups ActiveItems
		System.out.println("Active Items of group : "+inventoryDao.findGroup(9L).getGroupName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findGroup(9L)));
		System.out.println("Active Items of group : "+inventoryDao.findGroup(10L).getGroupName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findGroup(10L)));
		  
		// Global ActiveItems
		// TODO : Add a message to see what happens in the getActiveItems method
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(ItemType.Group.BACKGROUND)); 
		
		// getGlobalActiveItems method  
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getGlobalActiveItems(ItemType.BAKG_NAKED));		
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getGlobalActiveItems(ItemType.IMAGE_HOME_24));		
		
		itemService.dropItemForBirthDay();
		itemService.displayItemsUsedOrNot(true);	
		
		// new item activation 
		
		itemService.activateItemOnUser(inventoryDao.findUser(2L),specificItem2,inventoryDao.findUser(2L));
		
		// display again
		
		System.out.println("Active Items of user : "+inventoryDao.findUser(2L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(2L)));
		
		System.out.println("Active Items of user : "+inventoryDao.findUser(5L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(5L)));
	}
	
	public void performTestsFor1User() {
		
		// dropItem method
		
		Item specificItemGroup = itemService.dropItem(inventoryDao.findUser(1L), ItemType.BEER_BARREL, "A beer barrel for a group"); // for activeGroup		  
		
		Item specificItemGlobal = itemService.dropItem(inventoryDao.findUser(1L), ItemType.BAKG_NAKED, "Japan girl naked !"); // for activeGlobally
		
		Item specificItemUser = itemService.dropItem(inventoryDao.findUser(1L),ItemType.BEER_PINT, "I just felt like it"); // for activeOnUser 
		Item specificItemUser2 = itemService.dropItem(inventoryDao.findUser(1L), ItemType.CHAMPAIN_GLASS, "Happy birthday !"); // for activeOnUser
				  
				  
		// Activate methods 
		itemService.activateItemOnUser(inventoryDao.findUser(1L),specificItemUser,inventoryDao.findUser(1L));
		itemService.activateItemOnUser(inventoryDao.findUser(1L),specificItemUser2,inventoryDao.findUser(1L));
		
		itemService.activateItemOnGroup(inventoryDao.findUser(1L),specificItemGroup,inventoryDao.findGroup(9L)); 
		
		itemService.activateItemGlobally(inventoryDao.findUser(1L),specificItemGlobal);
			  
	
		// getActiveItems methods 
		System.out.println("Active Items of user : " +inventoryDao.findUser(1L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(1L)));
		System.out.println("");
			
		System.out.println("Active Items of user : "+inventoryDao.findUser(2L).getNickName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findUser(2L)));
		System.out.println("");
		
		// Groups ActiveItems
		System.out.println("Active Items of group : "+inventoryDao.findGroup(9L).getGroupName());
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(inventoryDao.findGroup(9L)));
		System.out.println("");
		  
		// Global ActiveItems
		System.out.println("Active items of type BACKGROUND");
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getActiveItems(ItemType.Group.BACKGROUND));
		System.out.println("");
		
		// getGlobalActiveItems method  
		System.out.println("Global active items");
		itemService.displayListOfActiveItems(ActiveItems.getInstance().getGlobalActiveItems(ItemType.BAKG_NAKED));
		System.out.println("");
	}
}
