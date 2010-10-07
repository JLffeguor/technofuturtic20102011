package javablackbelt.inventory.model;

public class Item {

	ItemType itemType;
	
	Item(ItemType itemTyp){
		this.itemType = itemTyp;
	}
	
	public void displayItemInfos(){
		
		System.out.println(itemType.getItemName());
		System.out.println(itemType.getItemDescription());	
		System.out.println(itemType.getItemLevel());	
		System.out.println(itemType.getItemDuration());	
	}
}
