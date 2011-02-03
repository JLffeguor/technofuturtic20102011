package blackbelt.lucene.ui;

import org.vaadin.navigator7.window.HeaderFooterFixedAppLevelWindow;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
/**
 * This class is only use for demo and test
 * */
public class SearchAppLevelApplication extends HeaderFooterFixedAppLevelWindow{

	public SearchAppLevelApplication() {
		setTheme("myTheme");
	}
	
	@Override
	protected Component createHeader() {
		return new Label("HEADER");
	}

	@Override
	protected Component createFooter() {
		return new Label("FOOTER");
	}

}
