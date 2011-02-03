package blackbelt.lucene.ui;

import java.io.Serializable;

import org.vaadin.navigator7.WebApplication;

import blackbelt.lucene.ui.page.CoursePage;
import blackbelt.lucene.ui.page.ResultPage;
import blackbelt.lucene.ui.page.SearchPage;

public class SearchWebApplication extends WebApplication implements Serializable{
	
	public SearchWebApplication() {
		registerPages(new Class[] {
                SearchPage.class, ResultPage.class, CoursePage.class
        });
	}
}
