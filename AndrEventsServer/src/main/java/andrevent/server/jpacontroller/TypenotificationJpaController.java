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

import andrevent.server.model.Notifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import org.springframework.transaction.annotation.Transactional;

import andrevent.server.jpacontroller.exceptions.IllegalOrphanException;
import andrevent.server.jpacontroller.exceptions.NonexistentEntityException;
import andrevent.server.jpacontroller.exceptions.PreexistingEntityException;
import andrevent.server.jpacontroller.exceptions.RollbackFailureException;
import andrevent.server.model.Typenotification;

/**
 * 
 * @author Alex
 */
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

	private List<Typenotification> findTypenotificationEntities(boolean all,
			int maxResults, int firstResult) {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
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
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		Root<Typenotification> rt = cq.from(Typenotification.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
