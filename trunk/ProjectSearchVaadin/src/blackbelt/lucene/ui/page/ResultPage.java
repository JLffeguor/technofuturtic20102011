package blackbelt.lucene.ui.page;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.uri.Param;

import blackbelt.lucene.IndexManager;

import com.vaadin.ui.VerticalLayout;

@Page
@Configurable(preConstruction = true)
public class ResultPage extends VerticalLayout {
	
	@Autowired IndexManager indexManager;
	
	@Param(pos=0)
	private String keyWord;
	@Param(pos=1)
	private String language;
	
	public ResultPage() {
		// TODO Auto-generated constructor stub
		try {
			indexManager.searchByKeyWordAndLanguage("jpa", "en");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
}
