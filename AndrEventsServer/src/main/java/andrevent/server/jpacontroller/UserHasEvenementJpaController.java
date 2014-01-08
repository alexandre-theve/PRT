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

import andrevent.server.model.UserHasEvenement;
import andrevent.server.model.UserHasEvenementPK;

/**
 * 
 * @author Alex
 */
@Component
public class UserHasEvenementJpaController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4790209002037541948L;
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void create(UserHasEvenement userHasEvenement) {
		em.persist(userHasEvenement);
	}

	@Transactional
	public void edit(UserHasEvenement userHasEvenement) {
		userHasEvenement = em.merge(userHasEvenement);
	}

	@Transactional
	public void destroy(UserHasEvenementPK userHasEvenement) {
		em.remove(userHasEvenement);
	}

	public List<UserHasEvenement> findUserHasEvenementEntities() {
		return findUserHasEvenementEntities(true, -1, -1);
	}

	public List<UserHasEvenement> findUserHasEvenementEntities(int maxResults,
			int firstResult) {
		return findUserHasEvenementEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<UserHasEvenement> findUserHasEvenementEntities(boolean all,
			int maxResults, int firstResult) {
		CriteriaQuery<UserHasEvenement> cq = em.getCriteriaBuilder().createQuery(UserHasEvenement.class);
		cq.select(cq.from(UserHasEvenement.class));
		Query q = em.createQuery(cq);
		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	public UserHasEvenement findUserHasEvenement(UserHasEvenementPK id) {
		return em.find(UserHasEvenement.class, id);

	}

	public int getUserHasEvenementCount() {
		CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
		Root<UserHasEvenement> rt = cq.from(UserHasEvenement.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
