/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andrevent.server.jpacontroller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import andrevent.server.model.Recherches;

/**
 * 
 * @author Alex
 */
@Component
public class RecherchesJpaController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5263272724967302877L;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void create(Recherches recherches) {
		em.persist(recherches);
	}

	@Transactional
	public void edit(Recherches recherches) {
		recherches = em.merge(recherches);
	}

	@Transactional
	public void destroy(Integer recherches) {
		em.remove(recherches);
	}

	public List<Recherches> findRecherchesEntities() {
		return findRecherchesEntities(true, -1, -1);
	}

	public List<Recherches> findRecherchesEntities(int maxResults,
			int firstResult) {
		return findRecherchesEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Recherches> findRecherchesEntities(boolean all,
			int maxResults, int firstResult) {
		CriteriaQuery<Recherches> cq = em.getCriteriaBuilder().createQuery(Recherches.class);
		cq.select(cq.from(Recherches.class));
		Query q = em.createQuery(cq);
		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	public Recherches findRecherches(Integer id) {
		return em.find(Recherches.class, id);
	}

	public int getRecherchesCount() {
		CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Recherches> rt = cq.from(Recherches.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
