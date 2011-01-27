package ctfTesting;

import org.apache.commons.lang.StringUtils;

public class Course{
	
	public static final String DEFAULT_WORKSHOP_TEXT = "This workshop gives you extensive practice on the topics covered in the <a href='%2$s'>%1$s</a>. It helps you assimilate the concepts.\n" +
			"You will be asked to develop small projects from scratch to finish, while being guided by your coach. The small projects bring you a step closer to real projects and are more difficult than the small exercises contained in each chapter of the course.\n" +
			"In order to build the solution to the projects you will be required to mix your knowledge gained during the various chapters of the corresponding course.\n" +
			"The workshop is ideally suited if you:[code]Code test[/code]" +
			"<ul>" +
			"<li>want to move on to the next course before being involved on a real project</li>" +
			"<li>need a challenge to figure out parts of the course you may have missed are not currently on a project where you can utilize the skills gained in the course</li>" +
			"</ul>" +
			"If you did the course, but have difficulties to pass the exam, the workshop will help you reach the objective.[image]test2[/image]";
	
	public static final long JAVA_AND_OO_FUNDAMENTALS_ID = 11180411;
	public static final long REFERENCE_MANUAL = 11261984L;
	
	public enum Type {
		COURSE    ("Course", "Classical course mixing theory and exercises"),  
		WORKSHOP ("Workshop", "Hands on training very practical and focused on exercises");

		String name;
		String description;
		Type(String aName, String aDescription) {
			this.name = aName;
			this.description = aDescription;
		}
		public String getName() {
			return name;
		}
		public String getDescription() {
			return description;
		}
	}	
	
	
	public enum Status {
		RELEASED("released", "The course is built and can be taken by students."),
		BETA("beta", "The course is underconstruction.");

		String name;
		String description;
		Status(String aName, String aDescription) {
			this.name = aName;
			this.description = aDescription;
		}
		public String getName() {
			return name;
		}
		public String getDescription() {
			return description;
		}
	}
	
    private	Long id;
    
	
	private String name;     // e.g. "Java & OO - Fundamentals Course"
    
    private String description;  // text for CourseScreen, above the text of each chapter. To be formatted with CourseTextFormatter.
    
    private Status status = Status.BETA;
 
    private Type type = Type.COURSE;
 
    
    private int coachLevel = 1;   // This course can be taught by coaches having at least that level.
    
    // Can the course be given without any revenue for the either BB or the coach
    private boolean potentiallyFree;

    private boolean publicAccess; // If true anyone may access the course content without registration
    
    // SEO : May be used for friendly urls instead of using ids
    private String urlFragment;

    
	public Course(){}
	
	public Course(String name){
		this.name = name;
		this.urlFragment = StringUtils.remove(this.name,' ').toLowerCase();
	}

	public Course(String name, String urlFragment){
		this.name = name;
		this.urlFragment = urlFragment.toLowerCase();
	}
		
	public Long getId() {
		return id;
	}
	// For testing, delete me.
	public void setId(Long id) {
		this.id = id;
	}
	
	/** You probably prefer using the getTypedName() method */
	public String getName() {
		return name;
	}
    public String getTypedName() {
        if (isCourse()) {
            return name + " course";
        } else if (isWorkshop()) {
            return name + " workshop";
        }
        return name;
    }
	
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Class<?> getConcreteClass() {
		return Course.class;
	}
	
	public String toString() {
		return super.toString() + getName();
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}
	
	public String getUrlFragment() {
		return urlFragment;
	}

	public void setUrlFragment(String urlFragment) {
		this.urlFragment = urlFragment.toLowerCase();
	}

	public int getCoachLevel() {
		return coachLevel;
	}

	public void setCoachLevel(int coachLevel) {
		this.coachLevel = coachLevel;
	}

	public boolean isPotentiallyFree() {
		return potentiallyFree;
	}

	public void setPotentiallyFree(boolean potentiallyFree) {
		this.potentiallyFree = potentiallyFree;
	}

	public void setPublicAccess(boolean publicAccess) {
		this.publicAccess = publicAccess;
	}

	public boolean isPublicAccess() {
		return publicAccess;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	
	public boolean isCourse(){
		return this.type == Type.COURSE;
	}

	public boolean isWorkshop(){
		return this.type == Type.WORKSHOP;
	}
}
