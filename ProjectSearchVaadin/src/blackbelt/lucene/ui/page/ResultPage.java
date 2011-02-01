package blackbelt.lucene.ui.page;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.Navigator.NavigationEvent;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.ParamChangeListener;
import org.vaadin.navigator7.uri.Param;

import blackbelt.lucene.IndexManager;

import com.vaadin.ui.VerticalLayout;

@Page
@Configurable(preConstruction = true)
public class ResultPage extends VerticalLayout implements ParamChangeListener {
	
	@Autowired IndexManager indexManager;
	
	@Param(pos=0, required=true)
	private String keyWord;
	@Param(pos=1, required=true)
	private String language;
	
	public ResultPage() {
		// TODO Auto-generated constructor stub
		
	}
	
    @Override
    public void paramChanged(NavigationEvent navigationEvent) {
        // TODO Auto-generated method stub
        try {
            System.out.println(keyWord +" "+ language);
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
