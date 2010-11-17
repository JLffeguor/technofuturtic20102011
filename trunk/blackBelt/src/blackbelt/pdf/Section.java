package blackbelt.pdf;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import org.springframework.beans.factory.annotation.Autowired;

import blackbelt.dao.Dao;
import blackbelt.dao.DaoServices;
@Entity
public class Section {
	@Id @GeneratedValue
	Long id;
	
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

}
