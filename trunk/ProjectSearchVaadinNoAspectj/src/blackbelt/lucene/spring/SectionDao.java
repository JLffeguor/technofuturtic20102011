package blackbelt.lucene.spring;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import blackbelt.lucene.SectionText;

@Repository
public class SectionDao {
	@PersistenceContext
	EntityManager em;
	
	// TODO: move to SectionTextDao
	@Transactional
	public List<SectionText> findLastVersionOfEachSectionTexts(){
		return em.createQuery("select s1 from SectionText s1 where s1.version=(" +
				"select max(s2.version) from SectionText s2 where s2.sectionid=s1.sectionid and s2.language=s1.language " +
				"group by s2.sectionid)" +
				"order by s1.sectionid").getResultList();
	}
}
