package dao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.domainModel.Section;










@Service
@Transactional
public class DaoServices {
	@Autowired
	private Dao rep;
	
//	public void saveDataTest(){
//		TestDataGenerator dataGenerator = new TestDataGenerator();
//		dataGenerator.generate();
//		Section section = dataGenerator.getRoot();
//		recursivSaving(section);
//		section = dataGenerator.getRootDeux();
//		recursivSaving(section);
//	}

	public Section load(Long id){
		Section section = rep.loadSection(id);
		return section;
	
	}
	public void recursivSaving(Section section){
		rep.save4Test(section);
		for(Section s : section.getSubSections()){
			recursivSaving(s);
		}		
	}


}
