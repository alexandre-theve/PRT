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

import andrevent.server.jpacontroller.exceptions.IllegalOrphanException;
import andrevent.server.jpacontroller.exceptions.NonexistentEntityException;
import andrevent.server.jpacontroller.exceptions.PreexistingEntityException;
import andrevent.server.jpacontroller.exceptions.RollbackFailureException;
import andrevent.server.model.User;

/**
 * 
 * @author Alex
 */
@Component
public class UserJpaController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8596178501405393351L;
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void create(User user) {
		em.persist(user);
	}

	@Transactional
	public void edit(User user) {
		user = em.merge(user);
	}

	@Transactional
	public void destroy(User user) {
		em.remove(user);
	}

	public List<User> findUserEntities() {
		return findUserEntities(true, -1, -1);
	}

	public List<User> findUserEntities(int maxResults, int firstResult) {
		return findUserEntities(false, maxResults, firstResult);
	}

	private List<User> findUserEntities(boolean all, int maxResults,
			int firstResult) {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(User.class));
		Query q = em.createQuery(cq);
		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	public User findUser(Integer id) {
		return em.find(User.class, id);
	}
	
	public User findUserByLogin(String login) {
		return (User) em.createNamedQuery("User.findByLogin").setParameter("login", login).setMaxResults(1).getSingleResult();
	}

	public int getUserCount() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		Root<User> rt = cq.from(User.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
