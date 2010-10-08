package javablackbelt.inventory.itemservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javablackbelt.inventory.model.Item;
import javablackbelt.inventory.model.ItemType;

public class ActiveItems {
	

	private Map<String, List<Item>> userMap = new HashMap<String, List<Item>>();
	private Map<String, List<Item>> groupMap = new HashMap<String, List<Item>>();
	private List<Item> globalItems = new ArrayList<Item>();	
	
    private static ActiveItems instance = new ActiveItems();  // Singleton.
    
    
    public void getActiveItems(ItemType.Group itemTypeGroup){
    	
    }
    
    public void getActiveItems(user){
    	
    }
    
    public void getActiveItems(Group group){
    	
    }
    
    public void getGlobalActiveItems(ItemType itemType){
    	
    }
    
    public ActiveItems getInstance() {
    	return instance;
    }
}
