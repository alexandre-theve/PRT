/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andrevent.server.jpacontroller;

import java.io.Serializable;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import andrevent.server.model.User;
import andrevent.server.model.Tags;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.springframework.transaction.annotation.Transactional;

import andrevent.server.jpacontroller.exceptions.IllegalOrphanException;
import andrevent.server.jpacontroller.exceptions.NonexistentEntityException;
import andrevent.server.jpacontroller.exceptions.RollbackFailureException;
import andrevent.server.model.Evenement;
import andrevent.server.model.Listediffusion;
import andrevent.server.model.Notifications;
import andrevent.server.model.UserHasEvenement;

/**
 * 
 * @author Alex
 */
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

	private List<Evenement> findEvenementEntities(boolean all, int maxResults,
			int firstResult) {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
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
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		Root<Evenement> rt = cq.from(Evenement.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
