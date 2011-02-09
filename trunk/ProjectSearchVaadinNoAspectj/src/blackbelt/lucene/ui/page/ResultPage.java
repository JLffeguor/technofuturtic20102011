package blackbelt.lucene.ui.page;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.navigator7.Navigator.NavigationEvent;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.ParamChangeListener;
import org.vaadin.navigator7.ParamPageLink;
import org.vaadin.navigator7.uri.Param;

import blackbelt.lucene.CourseSearchResultsManager;
import blackbelt.lucene.CourseSearchResultsManager.CourseSearchResult;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

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

	private static final int HIT_PER_PAGE = 15;
    private VerticalLayout layoutResult;
    private CourseSearchResultsManager courseSearchResultsManager;
    private int numberOfResults,currentPageOfResults;

    public ResultPage() {
        this.setSpacing(true);
    }

    @Override
    public void paramChanged(NavigationEvent navigationEvent) {
    	//Start a search
    	currentPageOfResults=1;
    	layoutResult=new VerticalLayout();
    	layoutResult.setSpacing(true);
    	layoutResult.setMargin(true);
    	courseSearchResultsManager = new CourseSearchResultsManager();  // TODO Autowired
    	courseSearchResultsManager.search(keyWord, language);
    	numberOfResults=courseSearchResultsManager.getTotalHits();
    	this.addComponent(new Label("Number of results: "+numberOfResults));
    	this.addComponent(layoutResult);

    	displayActualPageOfResult();

    	ResultNavigator resultNavigator=new ResultNavigator();
    	this.addComponent(resultNavigator);
    	this.setComponentAlignment(resultNavigator, Alignment.MIDDLE_CENTER);
    }
    
	private void displayActualPageOfResult() {
		
		int hitStart=(currentPageOfResults-1)*HIT_PER_PAGE;
		int hitEnd=currentPageOfResults*HIT_PER_PAGE;
		
		if (hitEnd>numberOfResults){
			hitEnd=numberOfResults;
		}

		layoutResult.removeAllComponents();
		for (int i = hitStart; i < hitEnd; i++) {
			layoutResult.addComponent(new ResultDisplayer(courseSearchResultsManager.getResult(i)));
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
    		
    		buttonBack=new ButtonNavigator("< Previous",this);
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
    	
    	private void reorganizeButtonsPage(int previousPage){
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
    		} else {
    			listButtons.get(currentPageOfResults-1).setCaptionBold(currentPageOfResults);
    			listButtons.get(previousPage-1).setCaption(previousPage);
    		}
    	}
    	
    	@Override
    	public void buttonClick(ClickEvent event) {
    		int previousPage;
    		
    		if(event.getButton()==buttonBack){
    			if(currentPageOfResults!=1){
    				previousPage=currentPageOfResults;
    				currentPageOfResults--;
    				displayActualPageOfResult();
    				reorganizeButtonsPage(previousPage);
    			}
    		} else if (event.getButton()==buttonNext){
    			if(currentPageOfResults<numberTotalOfPages){
    				previousPage=currentPageOfResults;
    				currentPageOfResults++;
    				displayActualPageOfResult();
    				reorganizeButtonsPage(previousPage);
    			}
    		} else {
    			ButtonPageNavigator button=(ButtonPageNavigator)event.getButton();
    			if(currentPageOfResults!=button.getPage()){
    				previousPage=currentPageOfResults;
    				currentPageOfResults=button.getPage();
    				displayActualPageOfResult();
    				reorganizeButtonsPage(previousPage);
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
    	
    	public void setCaption(int page){
    		this.page=page;
    		setCaption(String.valueOf(page));
    	}
    	
    	public int getPage(){
    		return page;
    	}
    }
}