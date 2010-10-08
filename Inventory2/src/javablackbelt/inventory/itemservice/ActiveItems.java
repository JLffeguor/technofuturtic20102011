package javablackbelt.inventory.itemservice;

public class ActiveItems {
	
	/*Map.....
	Map<Long (useid), List<Item>>
	Map<Long (groupid), List<Item>>
	List<Item> (global items as background)
	*/
	
    private static ActiveItems instance = new ActiveItems();  // Singleton.
    
    public ActiveItems getInstance() {
    	return instance;
    }
}
