/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andrevent.server.jpacontroller;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import andrevent.server.model.Evenement;
import andrevent.server.model.User;
import andrevent.server.model.UserHasEvenement;
import andrevent.server.model.UserHasEvenementPK;

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
	public void destroy(Integer id) {
		em.remove(findUser(id));
	}

	public void destroy(User user) {
		em.remove(user);
	}

	public List<User> findUserEntities() {
		return findUserEntities(true, -1, -1);
	}

	public List<User> findUserEntities(int maxResults, int firstResult) {
		return findUserEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<User> findUserEntities(boolean all, int maxResults,
			int firstResult) {
		CriteriaQuery<User> cq = em.getCriteriaBuilder()
				.createQuery(User.class);
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
		try {
			return (User) em.createNamedQuery("User.findByLogin")
					.setParameter("login", login).setMaxResults(1)
					.getSingleResult();
		} catch (Exception e) {
			return new User();
		}

	}

	public int getUserCount() {
		CriteriaQuery<Long> cq = em.getCriteriaBuilder()
				.createQuery(Long.class);
		Root<User> rt = cq.from(User.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	@Transactional
	public User subscribeToEvent(Integer idUser, Integer idEvent) {
		User user = em.find(User.class, idUser);
		Evenement evenement = em.find(Evenement.class, idEvent);
		
		if (user != null && evenement != null) {
			UserHasEvenement userHasEvenement = new UserHasEvenement(idUser, idEvent);
			if(!user.getUserHasEvenementList().contains(userHasEvenement)) {
				userHasEvenement.setCode(UUID.randomUUID().toString());
				user.getUserHasEvenementList().add(userHasEvenement);
				evenement.getUserHasEvenementList().add(userHasEvenement);
				user = em.merge(user);
			}
		}

		return user;
	}
	
	@Transactional
	public User unsubscribeToEvent(Integer idUser, Integer idEvent) {
		User user = em.find(User.class, idUser);
		Evenement evenement = em.find(Evenement.class, idEvent);
		
		if (user != null && evenement != null) {
			UserHasEvenement userHasEvenement = em.find(UserHasEvenement.class, new UserHasEvenementPK(idUser, idEvent));
			if(userHasEvenement != null) {
				user.getUserHasEvenementList().remove(userHasEvenement);
				evenement.getUserHasEvenementList().remove(userHasEvenement);
				
				user = em.merge(user);
			}
		}

		return user;
	}

}
