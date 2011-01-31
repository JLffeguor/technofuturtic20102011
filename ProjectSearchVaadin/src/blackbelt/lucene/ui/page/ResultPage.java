package blackbelt.lucene.ui.page;

import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.uri.Param;

import com.vaadin.ui.VerticalLayout;

@Page
public class ResultPage extends VerticalLayout {
	
	@Param(pos=0)
	private String keyWord;
	@Param(pos=1)
	private String language;
	
	public ResultPage() {
		// TODO Auto-generated constructor stub
	}
	
}
