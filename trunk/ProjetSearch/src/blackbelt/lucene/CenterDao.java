package blackbelt.lucene;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class CenterDao {
	@PersistenceContext
	EntityManager em;
	
	public List myQuerry(String querry){
		return em.createQuery(querry).getResultList();
	}
}
