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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This page display each results found by Lucene  
 *
 */
@Page
// TODO @Configurable(preConpile=true);
public class ResultPage extends VerticalLayout implements ParamChangeListener, ClickListener {

    @Param(pos = 0, required = true)
    private String keyWord;
    @Param(pos = 1, required = true)
    private String language;
    
    private static final int HIT_PER_PAGE = 3;
    private VerticalLayout layoutResult;
    private Button buttonBack,buttonNext;
    private List<CourseSearchResult> resultList;
    private int numberOfResults,currentPage,numberTotalOfPages;
    private ResultNavigator resultNavigator;

    public ResultPage() {
        this.setSpacing(true);
    }

    @Override
    public void paramChanged(NavigationEvent navigationEvent) {
        try {
            //Start a search
        	currentPage=1;
        	layoutResult=new VerticalLayout();
        	layoutResult.setSpacing(true);
        	layoutResult.setMargin(true);
            resultList = SpringUtil.getBean().searchByKeyWordAndLanguage(keyWord, language);  // TODO Autowired
            numberOfResults=resultList.size();
            this.addComponent(new Label("Number of results: "+numberOfResults));
            this.addComponent(layoutResult);
            
//            displayAll();
            displayPageOfResult(currentPage);
            
    		resultNavigator=new ResultNavigator();
    		this.addComponent(resultNavigator);
    		this.setComponentAlignment(resultNavigator, Alignment.MIDDLE_CENTER);
            
        } catch (ParseException e) {
           throw new RuntimeException(e);
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }
    }
    
    //TADO remove at the integration
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
    
//    private void createResultNavigator() {
//    	HorizontalLayout resultNavigator=new HorizontalLayout();
//    	resultNavigator.setSpacing(true);
//		numberTotalOfPages=(int)Math.ceil((double)numberOfResults/HIT_PER_PAGE);
//		
//		buttonBack=new Button("< Back",this);
//		resultNavigator.addComponent(buttonBack);
//		resultNavigator.addComponent(new Label("-"));
//		
//		for(int i=1;i<=numberTotalOfPages;i++){
//			resultNavigator.addComponent(new Button(String.valueOf(i),this));
//		}
//		
//		resultNavigator.addComponent(new Label("-"));
//		buttonNext=new Button("Next >",this);
//		resultNavigator.addComponent(buttonNext);
//		
//		this.addComponent(resultNavigator);
//		this.setComponentAlignment(resultNavigator, Alignment.MIDDLE_CENTER);
//    }
    
	private void displayPageOfResult(int page) {
		
		int hitStart=(page-1)*HIT_PER_PAGE;
		int hitEnd=page*HIT_PER_PAGE;
		
		if (hitEnd>numberOfResults){
			hitEnd=numberOfResults;
		}

		layoutResult.removeAllComponents();
		for (int i = hitStart; i < hitEnd; i++) {
			layoutResult.addComponent(new ResultDisplayer(resultList.get(i)));
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		System.out.println("ok");
		if(event.getButton()==buttonBack){
			System.out.println("Back");
			if(currentPage!=1){
				currentPage--;
				displayPageOfResult(currentPage);
				resultNavigator.reorganizeButtonsPage();
			}
		} else if (event.getButton()==buttonNext){
			System.out.println("Next");
			if(currentPage<numberTotalOfPages){
				currentPage++;
				displayPageOfResult(currentPage);
				resultNavigator.reorganizeButtonsPage();
			}
		} else {
			int pageSelected=Integer.valueOf(event.getButton().getCaption());
			if(currentPage!=pageSelected){
				currentPage=pageSelected;
				displayPageOfResult(pageSelected);
				resultNavigator.reorganizeButtonsPage();
			}
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
    
    private class ResultNavigator extends HorizontalLayout{
    	private static final int NBR_BUTTON_PAGE = 9;
    	private static final int BUTTON_PAGE_EDGE = 4;
    	private List<Button> listButtons;
    	
    	public ResultNavigator() {
        	this.setSpacing(true);
    		numberTotalOfPages=(int)Math.ceil((double)numberOfResults/HIT_PER_PAGE);
    		
    		buttonBack=new Button("< Back",ResultPage.this);
    		this.addComponent(buttonBack);
    		this.addComponent(new Label("-"));
    		
    		listButtons=new ArrayList<Button>();
    		
    		for(int i=1;i<=numberTotalOfPages && i<=NBR_BUTTON_PAGE;i++){
    			Button buttonPage=new Button(String.valueOf(i),ResultPage.this);
    			buttonPage.setImmediate(true);
    			listButtons.add(buttonPage);
    			this.addComponent(buttonPage);
    		}
    		
    		this.addComponent(new Label("-"));
    		buttonNext=new Button("Next >",ResultPage.this);
    		this.addComponent(buttonNext);
		}
    	
    	public void reorganizeButtonsPage(){
    		
    		if(currentPage<=BUTTON_PAGE_EDGE){
    		    for(int i=0;i<HIT_PER_PAGE;i++){
                    listButtons.get(i).setCaption(String.valueOf(i+1));
                }
    		}else if(currentPage>=numberTotalOfPages-BUTTON_PAGE_EDGE){
                for(int i=numberTotalOfPages;i<HIT_PER_PAGE;i--){
                    listButtons.get(i).setCaption(String.valueOf(i+1));
                }
            }
    	    
    	    if (currentPage>BUTTON_PAGE_EDGE && currentPage<(numberTotalOfPages-BUTTON_PAGE_EDGE-1)){
    			listButtons.get(4).setCaption(String.valueOf(currentPage));
//    			for(int i=0;i<BUTTON_PAGE_EDGE;i++){
//    				listButtons.get(i).setCaption(String.valueOf(currentPage+i));
//    				listButtons.get(NBR_BUTTON_PAGE-i-1).setCaption(String.valueOf(currentPage-i));
//    			}
    			for(int i=currentPage-BUTTON_PAGE_EDGE,j=0;i<=currentPage+BUTTON_PAGE_EDGE;i++,j++){
    				listButtons.get(j).setCaption(String.valueOf(i));
    			}
    		}
    	}
    }
}