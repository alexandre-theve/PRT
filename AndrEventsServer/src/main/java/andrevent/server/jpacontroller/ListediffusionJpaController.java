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

import andrevent.server.model.Listediffusion;

/**
 * 
 * @author Alex
 */
@Component
public class ListediffusionJpaController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6161967678119634210L;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void create(Listediffusion listediffusion) {
		em.persist(listediffusion);
	}

	@Transactional
	public void edit(Listediffusion listediffusion) {
		listediffusion = em.merge(listediffusion);
	}

	@Transactional
	public void destroy(Integer listediffusion) {
		em.remove(listediffusion);
	}

	public List<Listediffusion> findListediffusionEntities() {
		return findListediffusionEntities(true, -1, -1);
	}

	public List<Listediffusion> findListediffusionEntities(int maxResults,
			int firstResult) {
		return findListediffusionEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Listediffusion> findListediffusionEntities(boolean all,
			int maxResults, int firstResult) {
		CriteriaQuery<Listediffusion> cq = em.getCriteriaBuilder().createQuery(Listediffusion.class);
		cq.select(cq.from(Listediffusion.class));
		Query q = em.createQuery(cq);
		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	public Listediffusion findListediffusion(Integer id) {
		return em.find(Listediffusion.class, id);
	}

	public int getListediffusionCount() {
		CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Listediffusion> rt = cq.from(Listediffusion.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
