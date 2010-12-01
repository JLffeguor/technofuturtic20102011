package blackbelt.lucene;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sectiontexts")
public class SectionText {

	@Id
	private long id;
	@Column(name = "language")
	private String language;
	@Column(name = "text")
	private String text;
	@Column(name = "version")
	private int version;
	@Column(name = "sectionid")
	private long sectionid;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getText() {
		if (text == null)
			return "";
		else
			return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return id + " " + language+ " "+sectionid;
	}

	public long getSectionid() {
		return sectionid;
	}

	public void setSectionid(long sectionid) {
		this.sectionid = sectionid;
	}

	
}
