package blackbelt.pdf;

import java.util.ArrayList;
import java.util.List;

public class Section {

	private String title;
	private String body;
	private List<Section> subSections = new ArrayList<Section>();

	public Section(String title, String body) {
		this.title = title;
		this.body = body;
	}

	// getter && setter
	public List<Section> getSubSections() {
		return subSections;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}
}
