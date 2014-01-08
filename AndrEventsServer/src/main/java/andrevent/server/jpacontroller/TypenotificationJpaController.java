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

import andrevent.server.model.Typenotification;

/**
 * 
 * @author Alex
 */
@Component
public class TypenotificationJpaController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8792308097505542930L;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void create(Typenotification typenotification) {
		em.persist(typenotification);
	}

	@Transactional
	public void edit(Typenotification typenotification) {
		typenotification = em.merge(typenotification);
	}

	@Transactional
	public void destroy(Integer typenotification) {
		em.remove(typenotification);
	}

	public List<Typenotification> findTypenotificationEntities() {
		return findTypenotificationEntities(true, -1, -1);
	}

	public List<Typenotification> findTypenotificationEntities(int maxResults,
			int firstResult) {
		return findTypenotificationEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Typenotification> findTypenotificationEntities(boolean all,
			int maxResults, int firstResult) {
		CriteriaQuery<Typenotification> cq = em.getCriteriaBuilder().createQuery(Typenotification.class);
		cq.select(cq.from(Typenotification.class));
		Query q = em.createQuery(cq);
		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	public Typenotification findTypenotification(Integer id) {
		return em.find(Typenotification.class, id);
	}

	public int getTypenotificationCount() {
		CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Typenotification> rt = cq.from(Typenotification.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
