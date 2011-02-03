package blackbelt.lucene.ui.page;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.vaadin.navigator7.Navigator.NavigationEvent;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.PageResource;
import org.vaadin.navigator7.ParamChangeListener;
import org.vaadin.navigator7.ParamPageLink;
import org.vaadin.navigator7.uri.Param;

import blackbelt.lucene.IndexManager.CourseSearchResult;
import blackbelt.lucene.spring.SpringUtil;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

/**
 * This page display each results found by Lucene 
 *
 */
@Page
// TODO @Configurable(preConpile=true);
public class ResultPage extends VerticalLayout implements ParamChangeListener {

    @Param(pos = 0, required = true)
    private String keyWord;
    @Param(pos = 1, required = true)
    private String language;
    private List<CourseSearchResult> resultList;

    public ResultPage() {
        this.setSpacing(true);
    }

    @Override
    public void paramChanged(NavigationEvent navigationEvent) {
        try {
            //Start a search 
            resultList = SpringUtil.getBean().searchByKeyWordAndLanguage(keyWord, language);  // TODO Autowired
            //if results found -> display all results. Else display a message "no result"
            if(resultList.size()>0){
                for(CourseSearchResult courseSearchResult : resultList){
                    this.addComponent(new ResultDisplayer(courseSearchResult));
                }
            }else
                this.addComponent(new Label("No result"));
        } catch (ParseException e) {
           throw new RuntimeException(e);
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }
    }

    /**
     * Create a layout to display a result  
     *
     */
    private static class ResultDisplayer extends VerticalLayout {
        
        public ResultDisplayer(CourseSearchResult courseSearchResult) {
            HorizontalLayout titleLinkWithScore =new HorizontalLayout();
            titleLinkWithScore.addComponent(new ParamPageLink(courseSearchResult.getTitle(), CoursePage.class, courseSearchResult.getSectionId(), courseSearchResult.getLanguage()));
            //titleLinkWithScore.addComponent(new Label());
            Label score=new Label("Score: " + Math.round(courseSearchResult.getScore()*10) + " %");//Display the score of the result in % without decimal.
            score.setStyleName("rightAlign");
            titleLinkWithScore.addComponent(score); //Display the score of the result in % without decimal.
            titleLinkWithScore.setWidth("100%");
            
            Label labelText=new Label(courseSearchResult.getText());
            labelText.setContentMode(Label.CONTENT_XHTML);
            
            this.addComponent(titleLinkWithScore);
            this.addComponent(labelText);
        }

    }
    
}