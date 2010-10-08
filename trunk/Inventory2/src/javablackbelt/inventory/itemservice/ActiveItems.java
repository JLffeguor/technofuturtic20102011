package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
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
	 
    private static ActiveItems instance = new ActiveItems();  // Singleton.
    
    
    public void getActiveItems(ItemType.Group itemTypeGroup){   	
    }
    
    public void getActiveItems(User user){
    	
    }
        
    public void getGlobalActiveItems(ItemType itemType){
    	
    }
    
    public ActiveItems getInstance() {
    	return instance;
    }
}
