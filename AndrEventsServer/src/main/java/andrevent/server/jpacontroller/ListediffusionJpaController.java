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

import andrevent.server.model.Evenement;

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
import andrevent.server.model.Listediffusion;
import andrevent.server.model.ListediffusionHasUser;

/**
 * 
 * @author Alex
 */
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

	private List<Listediffusion> findListediffusionEntities(boolean all,
			int maxResults, int firstResult) {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
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
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		Root<Listediffusion> rt = cq.from(Listediffusion.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
