package dao.domainModel;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Section {
	@Id @GeneratedValue
	Long id;
	
	private String categorie;//TODO
	
	private String title;
	@Lob
	@Column 
	private String body;
	@OneToMany (fetch=FetchType.EAGER)
	private List<Section> subSections = new ArrayList<Section>();
	@ManyToOne
	private Section sParent;
	public Section(){}
	public Section(String title, String body, Section parent) {
		this.title = title;
		this.body = body;
		this.sParent = parent;
		System.out.println(this.getTitle());
	}
	public Section(Section section){
		this.id=section.getId();
		this.categorie="JPA/Hibernate Fundamentals";// HARD CODED FOR MOMENT
		this.title = section.getTitle();
		this.body = section.getBody();
		this.sParent = section.getParent();
		this.subSections = section.getSubSections();
	}

	// getter && setter
	public Long getId(){
		return id;
	}
	public List<Section> getSubSections() {
		return subSections;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}
	
	public Section getParent(){
		return this.sParent;
	}
	public String getCategoryTitle(){
		return this.categorie;
	}

}
