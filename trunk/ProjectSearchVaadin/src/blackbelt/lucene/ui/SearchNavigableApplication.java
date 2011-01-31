package blackbelt.lucene.ui;

import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.window.NavigableAppLevelWindow;

public class SearchNavigableApplication extends NavigableApplication{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1958163503925029861L;

	@Override
	public NavigableAppLevelWindow createNewNavigableAppLevelWindow() {
		// TODO Auto-generated method stub
		return new SearchAppLevelApplication();
	}

}
