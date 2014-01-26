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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import andrevent.server.model.Evenement;
import andrevent.server.model.Tags;

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

	@Autowired (required=true)
	private TagsJpaController tagsJpaController;
	
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
		em.remove(findEvenement(evenement));
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
			Double longitude, Double rayon) {
		return em.createNamedQuery("Evenement.findByLocation2", Evenement.class)
							.setParameter("latitude", latitude)
							.setParameter("longitude", longitude)
							.setParameter("rayon", rayon)
							.getResultList();
	}

	public List<Tags> findTagsForUser(Integer id) {
		return em.createNamedQuery("Evenement.findBestTagsForUser", Tags.class)
				.setParameter("user", id)
				.setMaxResults(3)
				.getResultList();
	}
	
	public List<Evenement> findEvenementsForUser(Integer id, List<Evenement> evenements) {
		List<Tags> tags = this.findTagsForUser(id);
		if(tags.isEmpty())
			tags = tagsJpaController.findTagsEntities();
		
		if(evenements.isEmpty())
			return em.createNamedQuery("Evenement.findBestEventsForUser", Evenement.class)
					.setParameter("tags", tags)
					.getResultList();
		else
			return em.createNamedQuery("Evenement.findBestEventsForUser2", Evenement.class)
					.setParameter("tags", tags)
					.setParameter("evenements", evenements)
					.getResultList();
	}

	public List<Evenement> findByTags(List<Tags> list) {
		return em.createNamedQuery("Evenement.findByTags", Evenement.class)
				.setParameter("tags", list)
				.getResultList();
	}

}
