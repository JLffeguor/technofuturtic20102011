package blackbelt.lucene.ui;

import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.window.NavigableAppLevelWindow;
/**
 * This class is only use for demo and test
 * */
public class SearchNavigableApplication extends NavigableApplication{

	@Override
	public NavigableAppLevelWindow createNewNavigableAppLevelWindow() {
		return new SearchAppLevelApplication();
	}

}
