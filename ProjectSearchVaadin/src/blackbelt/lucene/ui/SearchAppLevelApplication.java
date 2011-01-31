package blackbelt.lucene.ui;

import org.vaadin.navigator7.window.HeaderFooterFixedAppLevelWindow;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class SearchAppLevelApplication extends HeaderFooterFixedAppLevelWindow{

	public SearchAppLevelApplication() {
		setTheme("myTheme");
	}
	
	@Override
	protected Component createHeader() {
		// TODO Auto-generated method stub
		return new Label("HEADER");
	}

	@Override
	protected Component createFooter() {
		// TODO Auto-generated method stub
		return new Label("FOOTER");
	}

}
