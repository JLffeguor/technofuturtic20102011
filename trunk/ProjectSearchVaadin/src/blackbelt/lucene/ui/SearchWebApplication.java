package blackbelt.lucene.ui;

import java.io.Serializable;

import org.vaadin.navigator7.WebApplication;

import blackbelt.lucene.ui.page.ResultPage;
import blackbelt.lucene.ui.page.SearchPage;

public class SearchWebApplication extends WebApplication implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4855188634505534113L;

	public SearchWebApplication() {
		registerPages(new Class[] {
                SearchPage.class, ResultPage.class
        });
	}
}
