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

import andrevent.server.model.Evenement;

/**
 * 
 * @author Alex
 */
@Component
public class EvenementJpaController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6063435764580818901L;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void create(Evenement evenement) {
		em.persist(evenement);
	}

	@Transactional
	public void edit(Evenement evenement) {
		evenement = em.merge(evenement);
	}

	@Transactional
	public void destroy(Integer evenement) {
		em.remove(evenement);
	}

	public List<Evenement> findEvenementEntities() {
		return findEvenementEntities(true, -1, -1);
	}

	public List<Evenement> findEvenementEntities(int maxResults, int firstResult) {
		return findEvenementEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Evenement> findEvenementEntities(boolean all, int maxResults,
			int firstResult) {
		CriteriaQuery<Evenement> cq = em.getCriteriaBuilder().createQuery(Evenement.class);
		cq.select(cq.from(Evenement.class));
		Query q = em.createQuery(cq);
		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	public Evenement findEvenement(Integer id) {
		return em.find(Evenement.class, id);
	}

	public int getEvenementCount() {
		CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Evenement> rt = cq.from(Evenement.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	public List<Evenement> findEvenementByLocation(Double latitude,
			Double longitude, int rayon) {
		return em.createNamedQuery("Evenement.findByLocation", Evenement.class).setParameter("latitude", latitude)
							.setParameter("longitude", longitude).setParameter("rayon", rayon).getResultList();
	}

}
