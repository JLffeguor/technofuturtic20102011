package blackbelt.lucene.ui.page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.vaadin.navigator7.Navigator.NavigationEvent;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.ParamChangeListener;
import org.vaadin.navigator7.ParamPageLink;
import org.vaadin.navigator7.uri.Param;

import blackbelt.lucene.IndexManager.CourseSearchResult;
import blackbelt.lucene.spring.SpringUtil;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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

	private static final int HIT_PER_PAGE = 3;
    private VerticalLayout layoutResult;
    private List<CourseSearchResult> resultList;
    private int numberOfResults,currentPageOfResults;

    public ResultPage() {
        this.setSpacing(true);
    }

    @Override
    public void paramChanged(NavigationEvent navigationEvent) {
        try {
            //Start a search
        	currentPageOfResults=1;
        	layoutResult=new VerticalLayout();
        	layoutResult.setSpacing(true);
        	layoutResult.setMargin(true);
            resultList = SpringUtil.getBean().searchByKeyWordAndLanguage(keyWord, language);  // TODO Autowired
            numberOfResults=resultList.size();
            this.addComponent(new Label("Number of results: "+numberOfResults));
            this.addComponent(layoutResult);
            
//            displayAll();
            displayActualPageOfResult();
            
    		ResultNavigator resultNavigator=new ResultNavigator();
    		this.addComponent(resultNavigator);
    		this.setComponentAlignment(resultNavigator, Alignment.MIDDLE_CENTER);
            
        } catch (ParseException e) {
           throw new RuntimeException(e);
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }
    }
    
    //TODO remove at the integration
    private void displayAll(){
    	// if results found -> display all results. Else display a message "no result"
    	if (resultList.size() > 0) {
    		for (CourseSearchResult courseSearchResult : resultList) {
    			this.addComponent(new ResultDisplayer(courseSearchResult));
    		}
    	} else {
    		this.addComponent(new Label("No result"));
    	}
    }
    
	private void displayActualPageOfResult() {
		
		int hitStart=(currentPageOfResults-1)*HIT_PER_PAGE;
		int hitEnd=currentPageOfResults*HIT_PER_PAGE;
		
		if (hitEnd>numberOfResults){
			hitEnd=numberOfResults;
		}

		layoutResult.removeAllComponents();
		for (int i = hitStart; i < hitEnd; i++) {
			layoutResult.addComponent(new ResultDisplayer(resultList.get(i)));
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
            titleLinkWithScore.addComponent(score); //Display the score of the result in % without decimal.
            titleLinkWithScore.setWidth("100%");
            
            Label labelText=new Label(courseSearchResult.getText());
            labelText.setContentMode(Label.CONTENT_XHTML);
            
            this.addComponent(titleLinkWithScore);
            this.addComponent(labelText);
        }
    }
    
    private class ResultNavigator extends HorizontalLayout implements ClickListener {
    	private static final int NBR_BUTTONS_PAGE = 9;
    	private static final int BUTTON_PAGE_EDGE = 4;
    	private int numberTotalOfPages;
    	private List<ButtonPageNavigator> listButtons;
        private Button buttonBack,buttonNext;
    	
    	public ResultNavigator() {
        	this.setSpacing(true);
    		numberTotalOfPages=(int)Math.ceil((double)numberOfResults/HIT_PER_PAGE);
    		
    		buttonBack=new ButtonNavigator("< Back",this);
    		this.addComponent(buttonBack);
    		this.addComponent(new Label("-"));
    		
    		listButtons=new ArrayList<ButtonPageNavigator>();
    		
    		for(int i=0;i<numberTotalOfPages && i<NBR_BUTTONS_PAGE;i++){
    			ButtonPageNavigator buttonPage=new ButtonPageNavigator(i+1,this);
    			listButtons.add(buttonPage);
    			this.addComponent(buttonPage);
    		}
    		
    		this.addComponent(new Label("-"));
    		buttonNext=new ButtonNavigator("Next >",this);
    		this.addComponent(buttonNext);
		}
    	
    	private void reorganizeButtonsPage(){
    		if (numberTotalOfPages>NBR_BUTTONS_PAGE){
    			if (currentPageOfResults<=BUTTON_PAGE_EDGE){
    				for(int i=0;i<NBR_BUTTONS_PAGE;i++){
    					listButtons.get(i).setCaptionCkeckActualPage(i+1);
    				}
    			} else if(currentPageOfResults>=numberTotalOfPages-BUTTON_PAGE_EDGE){
    				for(int i=numberTotalOfPages,j=NBR_BUTTONS_PAGE-1;j>=0;i--,j--){
    					listButtons.get(j).setCaptionCkeckActualPage(i);
    				}
    			} else {
    				//if (currentPageOfResults>BUTTON_PAGE_EDGE && currentPageOfResults<(numberTotalOfPages-BUTTON_PAGE_EDGE-1))
    				//listButtons.get(4).setCaption(String.valueOf(currentPage));
//    				for(int i=currentPageOfResults-BUTTON_PAGE_EDGE,j=0;i<=currentPageOfResults+BUTTON_PAGE_EDGE;i++,j++){
//    					listButtons.get(j).setCaption(i==currentPageOfResults ? "<b>"+currentPageOfResults+"</b>" : String.valueOf(i));
//    				}
    				
    				int indexOfMiddelButton=NBR_BUTTONS_PAGE/2;
    				listButtons.get(indexOfMiddelButton).setCaptionBold(currentPageOfResults);
    				
    				for(int i=1,j=indexOfMiddelButton;i<=indexOfMiddelButton;i++){
    					listButtons.get(j+i).setCaptionNotCkeckActualPage(currentPageOfResults+i);
    					listButtons.get(j-i).setCaptionNotCkeckActualPage(currentPageOfResults-i);
    				}
    			}
    		}
    	}
    	
    	@Override
    	public void buttonClick(ClickEvent event) {
    		
    		if(event.getButton()==buttonBack){
    			if(currentPageOfResults!=1){
    				currentPageOfResults--;
    				displayActualPageOfResult();
    				reorganizeButtonsPage();
    			}
    		} else if (event.getButton()==buttonNext){
    			if(currentPageOfResults<numberTotalOfPages){
    				currentPageOfResults++;
    				displayActualPageOfResult();
    				reorganizeButtonsPage();
    			}
    		} else {
    			ButtonPageNavigator button=(ButtonPageNavigator)event.getButton();
    			if(currentPageOfResults!=button.getPage()){
    				currentPageOfResults=button.getPage();
    				displayActualPageOfResult();
    				reorganizeButtonsPage();
    			}
    		}
    	}
    }
    
    private static class ButtonNavigator extends Button {
    	
    	public ButtonNavigator() {
    		setImmediate(true);
    		setStyleName(BaseTheme.BUTTON_LINK);
		}
    	
    	public ButtonNavigator(String caption, ClickListener listener) {
    		super(caption, listener);
    		setImmediate(true);
    		setStyleName(BaseTheme.BUTTON_LINK);
		}
    }
    
    private class ButtonPageNavigator extends ButtonNavigator {
    	private int page;
    	
    	public ButtonPageNavigator(int page, ClickListener listener) {
    		setCaptionCkeckActualPage(page);
    		addListener(listener);
    		setImmediate(true);
    		setStyleName(BaseTheme.BUTTON_LINK);
		}
    	
    	public void setCaptionCkeckActualPage(int page){
    		if (page==currentPageOfResults){
    			setCaptionBold(page);
    		}else{
    			setCaption(String.valueOf(page));
    		}
    		this.page=page;
    	}
    	
    	public void setCaptionNotCkeckActualPage(int page){
    		this.page=page;
    		setCaption(String.valueOf(page));
    	}
    	
    	public void setCaptionBold(int page){
    		this.page=page;
    		setCaption("<b>"+page+"</b>");
    	}
    	
    	public int getPage(){
    		return page;
    	}
    }
}