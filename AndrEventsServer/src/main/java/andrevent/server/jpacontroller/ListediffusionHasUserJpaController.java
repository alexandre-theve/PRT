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

import org.springframework.transaction.annotation.Transactional;

import andrevent.server.model.ListediffusionHasUser;
import andrevent.server.model.ListediffusionHasUserPK;

/**
 * 
 * @author Alex
 */
public class ListediffusionHasUserJpaController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6504240800407163553L;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void create(ListediffusionHasUser listediffusionHasUser) {
		em.persist(listediffusionHasUser);
	}

	@Transactional
	public void edit(ListediffusionHasUser listediffusionHasUser) {
		listediffusionHasUser = em.merge(listediffusionHasUser);
	}

	@Transactional
	public void destroy(ListediffusionHasUserPK listediffusionHasUser) {
		em.remove(listediffusionHasUser);
	}

	public List<ListediffusionHasUser> findListediffusionHasUserEntities() {
		return findListediffusionHasUserEntities(true, -1, -1);
	}

	public List<ListediffusionHasUser> findListediffusionHasUserEntities(
			int maxResults, int firstResult) {
		return findListediffusionHasUserEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<ListediffusionHasUser> findListediffusionHasUserEntities(
			boolean all, int maxResults, int firstResult) {
		CriteriaQuery<ListediffusionHasUser> cq = em.getCriteriaBuilder().createQuery(ListediffusionHasUser.class);
		cq.select(cq.from(ListediffusionHasUser.class));
		Query q = em.createQuery(cq);
		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	public ListediffusionHasUser findListediffusionHasUser(
			ListediffusionHasUserPK id) {
		return em.find(ListediffusionHasUser.class, id);
	}

	public int getListediffusionHasUserCount() {
		CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
		Root<ListediffusionHasUser> rt = cq.from(ListediffusionHasUser.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
