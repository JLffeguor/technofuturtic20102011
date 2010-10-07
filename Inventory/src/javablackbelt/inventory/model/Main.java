package javablackbelt.inventory.model;

import java.util.Date;
import java.util.GregorianCalendar;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Date activationDate = new Date();
		Date removalDate = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(removalDate);
		// add the item duration (in hours) to the activation date.
		//cal.add(GregorianCalendar.HOUR_OF_DAY, ItemType.IMAGE_HOME_6.getItemDuration());
		cal.roll(GregorianCalendar.HOUR_OF_DAY, ItemType.IMAGE_HOME_6.getItemDuration());
		//cal.setTime(removalDate);
		//Date removalDate = cal.getGregorianChange();
		removalDate = cal.getTime();
		System.out.println("activation date : " + activationDate);
		System.out.println("removal date : " + removalDate);
	}
}
