package blackbelt.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.pdf.Section;

@Repository 
@Transactional
public class Dao {

	@PersistenceContext
	private EntityManager em;
	
	public Section loadSection(Long id){
		Section section= em.find(Section.class, id);
		return section;
	}
	
	public void save4Test(Section section){
		if(section!=null){
		
			em.persist(section);
		}
		

	}

	
}
