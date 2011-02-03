package blackbelt.lucene.ui.page;

import org.vaadin.navigator7.Navigator.NavigationEvent;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.ParamChangeListener;
import org.vaadin.navigator7.uri.Param;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Page
public class CoursePage extends VerticalLayout implements ParamChangeListener{
    @Param(pos=0, required=true)
    String sectionId;
    @Param(pos=1, required=true)
    String language;
    
    public CoursePage() {
        // TODO Auto-generated constructor stub
        this.addComponent(new Label("Course page"));
    }
    
    @Override
    public void paramChanged(NavigationEvent navigationEvent) {
        // TODO Auto-generated method stub
        
    }

}
