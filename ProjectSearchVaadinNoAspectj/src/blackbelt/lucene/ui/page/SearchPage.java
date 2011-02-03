package blackbelt.lucene.ui.page;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.vaadin.navigator7.Navigator;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.PageResource;

import blackbelt.lucene.spring.SpringUtil;
import blackbelt.lucene.ui.SearchNavigableApplication;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Page
public class SearchPage extends VerticalLayout {
	
	private static final List<String> LANGUAGES=Arrays.asList(
				"FR", "EN", "IT", "RU" 
			);
	private TextField tfKeyWord;
	private NativeSelect listLanguages;
	
	public SearchPage() {
		HorizontalLayout horizontalLayout=new HorizontalLayout();
		
		tfKeyWord =new TextField();
		tfKeyWord.setHeight("25px");
		listLanguages=new NativeSelect(null,LANGUAGES);
		listLanguages.setValue(LANGUAGES.get(1));
		listLanguages.setNullSelectionAllowed(false);
		listLanguages.setHeight("25px");
		Button btnSearch=new Button("Search");
		btnSearch.setClickShortcut(KeyCode.ENTER);
		btnSearch.addListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				PageResource pageResource=new PageResource(ResultPage.class);
				
				if("".equals(tfKeyWord.getValue())){
					System.out.println("no good");
				}else{
				    System.out.println("****************"+tfKeyWord.getValue());
					Navigator navigator=SearchNavigableApplication.getCurrentNavigableAppLevelWindow().getNavigator();
					navigator.navigateTo(ResultPage.class, (String)tfKeyWord.getValue()+"/"+(String)listLanguages.getValue());
				}
			}
		});
		
		horizontalLayout.setSpacing(true);
		horizontalLayout.addComponent(tfKeyWord);
		horizontalLayout.addComponent(listLanguages);
		horizontalLayout.addComponent(btnSearch);
		
		Button btnCreateIndex = new Button("Create index");
		btnCreateIndex.addListener(new Button.ClickListener() {
            
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    SpringUtil.getBean().createIndexes();
                } catch (CorruptIndexException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }                
            }
        });
		
		this.setSpacing(true);
		this.addComponent(horizontalLayout);
		this.addComponent(btnCreateIndex);
	}

}
