package blackbelt.ui.common;

import org.apache.commons.lang.StringUtils;
import org.vaadin.navigator7.PageResource;

import be.loop.jbb.bo.AuctionItem;
import be.loop.jbb.bo.Belt;
import be.loop.jbb.bo.User;
import be.loop.jbb.bo.questions.Question;
import be.loop.jbb.bo.tests.TestDef;
import be.loop.jbb.security.SecurityContext;
import blackbelt.model.CoachOffering;
import blackbelt.model.Course;
import blackbelt.model.CourseReg;
import blackbelt.model.Group;
import blackbelt.model.Language;
import blackbelt.model.Section;
import blackbelt.model.SectionText;
import blackbelt.model.Topic;
import blackbelt.ui.application.EntityPageResource;
import blackbelt.ui.auction.AuctionPage;
import blackbelt.ui.common.PictureResource.ImageSize;
import blackbelt.ui.common.PictureResource.PicturePath;
import blackbelt.ui.course.CoursePage;
import blackbelt.ui.coursepage.CoursePagePage;
import blackbelt.ui.coursepage.CoursePagePage.CoursePagePageResource;
import blackbelt.ui.coursereg.CourseRegCreateForCoachOfferingWindow;
import blackbelt.ui.coursereg.CourseRegPage;
import blackbelt.ui.group.GroupPage;
import blackbelt.ui.lang.LangOverviewPage;
import blackbelt.ui.lang.LanguageLeadersPage;
import blackbelt.ui.topic.TopicPage;
import blackbelt.ui.user.UserPage;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ComponentFactory {

	public static Label createPageTitle(String text) {
		return createTitle(text, 1, false);
	}

    public static Label createTitle(String text, int level) {
        return createTitle(text, level, true);
    }

	public static Label createTitle(String text, int level, boolean marginOnTop) {
		if (level > 10) {
			throw new IllegalArgumentException("Bug: level "+level+" not supported.");
		}
		if(level > 4){
			level = 4;
		}
		
		Label result = new Label(text, Label.CONTENT_XHTML);
		result.addStyleName("h"+ level);
		if (marginOnTop) {
		    result.addStyleName("h-marginOnTop");
		}
		return result;
	}

	
	// Needed by multiple screen, don't know where else to put that common code... John 2009-09-04
	public static Button createCourseOfferingRegisterButton(String text, final CoachOffering offering) {
		Button b = new Button(text);
		b.addStyleName("smallLink");
		b.addStyleName("link");
		b.addListener(new ClickListener(){
			@Override
			public void buttonClick(ClickEvent event) {
				User student = SecurityContext.getUser();
				if (student == null) {
					NotificationUtil.showNotification("You must be logged in to register a course", "Please login or sign-up");
				} else if(SecurityContext.loggedUserIs(offering.getCoach())){
					NotificationUtil.showNotification("You can't register your own courses", "Please choose another instructor or course");					
				} else {
					final CourseRegCreateForCoachOfferingWindow win = new CourseRegCreateForCoachOfferingWindow(student, offering);
					WindowUtil.open( win );
					win.addListener(new Window.CloseListener() {
						@Override public void windowClose(Window.CloseEvent e) {
							if (!win.isCancelled()) {
								NotificationUtil.showNotification("The coach has been notified.", "He got a mail, and you need to wait for him to accept.");
							}
						}
					});
				}
			}
		});
		return b;
	}
	
	public static Component wrapComponentToBlackStyle(Component input) {
		CssLayout transparentLayout = new CssLayout();  // trick for reindeer black buttons:  http://vaadin.com/forum/-/message_boards/message/66365
		transparentLayout.setStyleName("black");
		transparentLayout.addStyleName("transparent");
		transparentLayout.addComponent(input);
		return transparentLayout;
	}
	

	public static String pageLinkHtml(String caption, ExternalResource resource) {
	    return "<a href='"+ resource.getURL() + "'>"+ caption + "</a>";
	}

	public static String questionLabelWithLinkHtml(Question question) {
		StrutsResource strutsResource = new StrutsResource(question);
		return "<a href='"+ strutsResource.getURL() + "'>"+ question.getCurrentQuestionVersion().getLabel() + "</a>";
	}

	public static String auctionNameWithLinkHtml(AuctionItem auctionItem) {
		EntityPageResource screenResource = (new EntityPageResource(AuctionPage.class, auctionItem));
		return "<a href='"+ screenResource.getURL() + "'>"+ auctionItem.getName() + "</a>";
	}

	public static String topicNameWithLinkHtml(Topic topic) {
	    EntityPageResource screenResource = new EntityPageResource(TopicPage.class, topic);
		return "<a href='"+ screenResource.getURL() + "'>"+ topic.getName() + "</a>";
	}

	public static String courseRegWithLinkHtml(CourseReg courseReg, String text) {
	    EntityPageResource screenResource = new EntityPageResource(CourseRegPage.class, courseReg);
		return "<a href='"+ screenResource.getURL() + "'>"+ text + "</a>";
	}	

	public static String buildScreenLinkHtml(EntityPageResource resource, String text){
		return "<a href='"+ resource.getURL() + "'>"+ text + "</a>";		
	}
	
	public static String courseNameWithLinkHtml(Course course) {
	    EntityPageResource screenResource = new EntityPageResource(CoursePage.class, course);
		return "<a href='"+ screenResource.getURL() + "'>"+ course.getName() + "</a>";
	}

    public static String courseNameWithCourseRegLinkHtml(CourseReg courseReg) {
        EntityPageResource screenResource = new EntityPageResource(CourseRegPage.class, courseReg);
        return "<a href='"+ screenResource.getURL() + "'>"+ courseReg.getCourse().getName() + "</a>";
    }

    public static String examNameWithLinkHtml(TestDef testDef) {
		StrutsResource strutsResource = new StrutsResource(testDef);
		return "<a href='"+ strutsResource.getURL() + "'>"+ testDef.getLabel() + "</a>";
	}
    
	public static String userNameWithLinkHtml(User user) {
	    EntityPageResource screenResource = new EntityPageResource(UserPage.class, user);
		return "<a href='"+ screenResource.getURL() + "'><b>"+ user.getFirstName() + "</b> " + user.getLastName() + "</a>";
	}
	
	public static Label userNameWithLinkLabel(User user, String additionalText) {
		return new Label(userNameWithLinkHtml(user) + additionalText, Label.CONTENT_XHTML);
	}

    public static String userWithLinkHtml(User user, String text) {
        EntityPageResource screenResource = new EntityPageResource(UserPage.class, user);
        return "<a href='"+ screenResource.getURL() + "'>"+ text + "</a>";
    }
    
	public static String userPictureAndNameWithLinkHtml(User user, FloatStyle flt) {
		return userPictureWithLinkHtml(user, flt) + userNameWithLinkHtml(user);
	}

	public static String userMediumPictureAndNameWithLinkHtml(User user, FloatStyle flt) {
		return userMediumPictureWithLinkHtml(user, flt) + userNameWithLinkHtml(user);
	}	

	public static String userMediumPictureWithLinkHtml(User user, FloatStyle flt) {
		if (user.getPictureName() == null) {
			return "";
		}
		String userProfileLink = new EntityPageResource(UserPage.class, user).getURL();
		String imgHtml = "<img src=\"" + (new PictureResource(PicturePath.USER_MEDIUM, user.getPictureName())).getURL() + "\" " +
				"title=\"" + user.getFullName() + "\" border='0' style='"+flt.getStyleStringWithPadding()+"' />";
		return "<a href=\"" + userProfileLink + "\">" + imgHtml + "</a>";
	}	
	
	public static String userPictureWithLinkHtml(User user, FloatStyle flt) {
		if (user.getPictureName() == null) {
			return "";
		}
		String userProfileLink = new EntityPageResource(UserPage.class, user).getURL();
		String imgHtml = "<img src=\"" + (new PictureResource(PicturePath.USER_SMALL, user.getPictureName())).getURL() + "\" title=\"" + user.getFullName() 
		+ "\" border='0' style='"+flt.getStyleStringWithPadding()+"' />";
		return "<a href=\"" + userProfileLink + "\">" + imgHtml + "</a>";
	}

	public static String beltPictureHtml(User user, Belt belt, ImageSize size, FloatStyle flt) {
		String imgHtml = "<img src=\"" + new PictureResource(PicturePath.BELT, PictureResource.getBeltPictureName(belt,
				size)).getURL()
				+ "\" title=\"" + belt.getColorName() + " belt\" border='0' style='"+ flt.getStyleStringWithPadding() +"' />";
		return imgHtml;
	}

	public static String pictureHtml(PictureResource resource, FloatStyle flt) {
		String imgHtml = "<img src='" + resource.getURL()
				+ "' border='0' style='"+ flt.getStyleStringWithPadding() +"' />";
		return imgHtml;
	}

	public static String pictureAndLinkHtml(PictureResource pictureResource, ExternalResource linkTarget, FloatStyle flt, boolean newPage) {
		String targetUrl = linkTarget.getURL();
		String imgHtml = "<img src='" + pictureResource.getURL()
				+ "' border='0' style='"+ flt.getStyleStringWithPadding() +"' />";
		return "<a " + (newPage ? "target=\"blank\"" : "")  + " href=\"" + targetUrl + "\">" + imgHtml + "</a>";
	}
	
	public static String beltPictureHtml(User user, FloatStyle style) {
		return beltPictureHtml(user, user.getBelt(), ImageSize.SMALL, style);
	}
	
    public static String groupPictureAndNameWithLinkHtml(Group group, FloatStyle flt, boolean showNullImage) {
        return groupPictureAndNameWithLinkHtml(group, flt, showNullImage, false);
    }

    public static String groupPictureAndNameWithLinkHtml(Group group, FloatStyle flt, boolean showNullImage, boolean bold) {
        return groupPictureWithLinkHtml(group, flt, showNullImage, PicturePath.GROUP_SMALL) + groupNameWithLinkHtml(group, bold);
    }

    public static String groupNameWithLinkHtml(Group group, boolean bold) {
        EntityPageResource screenResource = new EntityPageResource(GroupPage.class, group);
        String html =  "<a href='"+ screenResource.getURL() + "'>"+ group.getName() + "</a>";
        if (bold) {
            html = "<b>" + html + "</b>";
        }
        return html;
    }

    /** showNullImage == true if we show a default image instead of nothing */
    public static String groupPictureWithLinkHtml(Group group, FloatStyle flt, boolean showNullImage, PicturePath size) {
        if (group.getImageName() == null && !showNullImage) {
            return "";
        }
        
        String groupLink = new EntityPageResource(GroupPage.class, group).getURL();
        String imgHtml = "<img src=\"" + (new PictureResource(size, 
                group.getImageName() == null ? "null.png" : group.getImageName())).getURL() +
            "\" title=\"" + group.getFullName() + " group\" border='0' " +
            "style='"+flt.getStyleStringWithPadding()+"' />";
        return "<a href=\"" + groupLink + "\">" + imgHtml + "</a>";
    }

    /** showNullImage == true if we show a default image instead of nothing */
    public static String auctionPictureWithLinkHtml(AuctionItem auction, FloatStyle flt) {
        if (StringUtils.isBlank(auction.getPictureName())) {
            return "";
        }        
        String auctionLink = new EntityPageResource(AuctionPage.class, auction).getURL();
        String picturUrl = new PictureResource(PicturePath.AUCTION_SMALL, auction.getPictureName()).getURL();
        
        String imgHtml = "<img height='20px' src='" + picturUrl + "' title='" + auction.getName() + " group' border='0' " + "style='"+flt.getStyleStringWithPadding()+"' />";
        return "<a href='" + auctionLink + "'>" + imgHtml + "</a>";
    }
    
    
    public enum FloatStyle {
        LEFT, RIGHT, NONE;
        
        public String getStyleStringWithPadding() {
            if (this == NONE) {
                return "";
            } else if (this == LEFT) {
                return "padding-right:6px; float:left; ";
            } else  if (this == RIGHT) {
                return "padding-left:6px; float:right; ";
            } else throw new IllegalStateException("Unknown FloatStyle value " + this);
        }
    };
    

    public static Link createSectionLink(SectionText sectionText) {
        return new Link(sectionText.getNumberedTitle(),  new CoursePagePageResource(sectionText));
    }
    /** sectionText may be EN, but targetLanguage maybe FR, in the case that section has no FR sectionText yet. */
    public static Link createSectionLink(SectionText sectionText, Language targetLanguage) {
        return new Link(sectionText.getNumberedTitle(),  new CoursePagePageResource(sectionText.getSection(), targetLanguage));
    }

    public static Label createTranslationExplanationLabel(boolean includeLangOverviewPageLink, String additionalText) {
        Label explLabel = new Label("<p>Courses are " + 
        		(includeLangOverviewPageLink ?  ComponentFactory.pageLinkHtml("progressively translated", new PageResource(LangOverviewPage.class))
        		        : "progressively translated") +
        		" in the supported languages. " +
                "See the "+ComponentFactory.pageLinkHtml("internationalization section", new PageResource(CoursePagePage.class, Section.REF_MANUAL_TRANSLATION))+" in the reference manual.</p>" +
                (additionalText != null ? "<p>"+additionalText+"</p>" : "") +
                "<p>Translations are performed by the community and managed by "+
                ComponentFactory.pageLinkHtml("language leaders", new PageResource(LanguageLeadersPage.class)) +".</p>",
                Label.CONTENT_XHTML);
        return explLabel;
    }
}
