package javablackbelt.inventory.model;

/**
 * 
 * @author forma711
 *
 */

public enum ItemType {

	// ENUM
	 
	BEER_PINT("Beer", 1, 12, "Refreshing pint of Belgian beer. Attention, it's not alchol free."),
	BEER_BARREL("Beer Barrel", 2, 12, "Barrel of beer for a group"),
	CHAMPAIN_BLASS("Champain", 2, 24, "Glass of excellent French Champain"),
	CHAMPAIN_BARREL("Champain Barrel", 3, 24, "Barrel of champain for a group"),
	REDUCER("Reducer", 2, 48, "Reduces a user lists and on his page"),
	ENLARGER("Enlarger", 2, 48,"..... idem reducer but 200%"),
	BAKG_BLOND_GIRL("Blond girl", 3, 24, "Blonde girl with sword to replace and give holidays to the asian girl on site background"),
	BAKG_SILK("Red silk", 2, 24, "Soft red silk laces to gently attach the asian girl in the site background"),
	BAKG_ROPE("Rope", 3, 24, "Rope to tightly attach the asian girl on the site background"),
	BAKG_CHAIN("Chains", 4, 24, "Chains to  violently attach the asian girl in the site background. It hurts."),
	BAKG_FLOWERS("Flowers", 2, 24, "Flowers that you can offer to the asian girl on the site background."),
	BAKG_MAN("Hairy man", 3, 24, "Man to replace the asian girl as site background"),
	BAKG_IAI_DO("Iai-Do Course", 3, 24, "Teach the asian girl on the site background how to handle a katana correctly"),
	BAKG_COAT("Coat", 3, 24, "Give a coat to the poor cold asian girl on the background"),
	BAKG_SLEEP("Sleep", 3, 8, "Enable the asian girl to sleep 8h on the background."),
	BAKG_BIKINI("Bikini", 3, 24, "It's hot, give the asian background girl a bikini"),
	BAKG_RICE("Rice", 3, 24, "Give a bowl of rice to the background asian girl"),
	BAKG_TEA("Tea", 3, 24, "Give a cup of tea to the background asian girl"),
	BAKG_TOPLESS("Top less", 4, 24, "Put the background asian girl top less!"),
	BAKG_NAKED("Naked", 4, 24, "Remove every clothes of the asian background girl, except her head band and her belt"),
	BAKG_NUNCHAKU("Nunchaku", 3, 24, "Replace the katana by a nunchaku for the background asian girl"),
	HONOR("Honor", 3, 6, "Put the target user in the top right corner of the home page, at the honor place, and tweet about it on BlackBeltFactory's twitter account."),
	FLOWERS("Flowers", 1, 6, "Flowers and lovely birds dancing around the target name"),
	KARATEKA("Karateka", 1, 24, "Karateka to help someone's training will display beside target's name"),
	GYM("Gym", 1, 24, "Gym training. Gets the target user to the gym. Enlages muscles"),
	MILK("Milk", 1, 12, "Healthy glas of milk. It will give strength to one of your student."),
	COURSE_CHAPTER("Course chapter", 4, 24*7, "Access to any course chapter for 7 days"),
	IMAGE_HOME_24("Image on home page 24h", 4, 24, "Hack the BlackBeltFactory.com home page with any image of your choice for 24h. Image size should be 980x200. A text will mention that you are the author of this, with a link to your profile. Please don't put no hate message."),
	IMAGE_HOME_6("Image on home page 6h", 4, 6, "Hack the BlackBeltFactory.com home page with any image of your choice for 24h. Image size should be 980x200. A text will mention that you are the author of this, with a link to your profile. Please don't put no hate message."),
	IMAGE_HOME_1("Image on home page 1h", 4, 1, "Hack the BlackBeltFactory.com home page with any image of your choice for 24h. Image size should be 980x200. A text will mention that you are the author of this, with a link to your profile. Please don't put no hate message.");


	private String itemName;
	private String itemDescription;
	private int itemLevel;
	private int itemDuration;
	
	/**
	 * 
	 * @param itemNam
	 * @param itemLvl
	 * @param itemDur
	 * @param itemDesc
	 */
	
	ItemType(String itemNam, int itemLvl, int itemDur, String itemDesc) {
		
		this.itemName = itemNam;
		this.itemDescription = itemDesc;
		this.itemLevel = itemLvl;
		this.itemDuration = itemDur;
	}

	/**
	 * 
	 * @return itemName
	 */
	
	public String getItemName() {
		return itemName;
	}

	/**
	 * 
	 * @return itemDescription
	 */
	
	public String getItemDescription() {
		return itemDescription;
	}

	/**
	 * 
	 * @return itemLevel
	 */
	
	public int getItemLevel() {
		return itemLevel;
	}
	
	/**
	 * 
	 * @return itemDuration
	 */

	public int getItemDuration() {
		return itemDuration;
	}
	
	
}
