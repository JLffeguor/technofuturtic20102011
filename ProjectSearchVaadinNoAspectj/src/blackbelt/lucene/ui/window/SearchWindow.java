package blackbelt.lucene.ui.window;

import java.util.Arrays;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SearchWindow extends Window{
    
    public SearchWindow(String keyWork,String actualCourse) {
        // TODO Auto-generated constructor stub
        super("Search...");
        this.center();
        this.setResizable(false);
        this.setModal(true);
        
        TextField tfKeyWord=new TextField();
        Button buttonSearch=new Button("Search");
        
        //TODO at the implmentation, use Language
        List<String> listLanguages=Arrays.asList("FR","EN","IT","RU");
        
        OptionGroup lang = new OptionGroup("Select language(s)", listLanguages);
        lang.setMultiSelect(true);
        lang.setNullSelectionAllowed(false); // user can not 'unselect'
        lang.select("Berlin"); // select this by default
        lang.setImmediate(true); // send the change to the server at once

        
        List<String> listField=Arrays.asList("Title","Text");
        OptionGroup field = new OptionGroup("On the field(s)", listField);
        field.setMultiSelect(true);
        field.setNullSelectionAllowed(false); // user can not 'unselect'
        field.select("Berlin"); // select this by default
        field.setImmediate(true); // send the change to the server at once
        
        OptionGroup course = new OptionGroup();
        course.addItem("Search in "+actualCourse);
        course.addItem("Search in all course");
        field.setMultiSelect(true);
        
        
        
        VerticalLayout content=new VerticalLayout();
        content.setMargin(true);
        content.setSpacing(true);
        content.addComponent(tfKeyWord);
        content.addComponent(buttonSearch);
        content.addComponent(lang);
        content.addComponent(field);
        content.addComponent(course);
        

        this.setHeight("800px");
        this.setWidth("600px");
        this.setContent(content);
    }

}
