package dao.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dao.domainModel.Section;


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
