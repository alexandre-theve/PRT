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
import andrevent.server.model.Listediffusion;
import andrevent.server.model.ListediffusionHasUser;
import andrevent.server.model.ListediffusionHasUserPK;
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
	public User subscribeToEvent(Integer idUser, Integer idEvent, Boolean push) {
		User user = em.find(User.class, idUser);
		Evenement evenement = em.find(Evenement.class, idEvent);
		
		if (user != null && evenement != null) {
			UserHasEvenement userHasEvenement = new UserHasEvenement(idUser, idEvent);
			if(!user.getUserHasEvenementList().contains(userHasEvenement)) {
				userHasEvenement.setNotifications(push);
				userHasEvenement.setCode(UUID.randomUUID().toString());
				user.getUserHasEvenementList().add(userHasEvenement);
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

	@Transactional
	public Boolean editSubscriptionToEvent(Integer idUser, Integer idEvent,
			Boolean push) {
		User user = em.find(User.class, idUser);
		
		if(user != null) {
			int index = user.getUserHasEvenementList().indexOf(new UserHasEvenement(idUser, idEvent));
			if(index >= 0 && index < user.getUserHasEvenementList().size()) {
				user.getUserHasEvenementList().get(index).setNotifications(push);
				em.merge(user);
				
				return true;
			}
		}
		
		return false;
	}

	@Transactional
	public User subscribeToListe(Integer idUser, Integer idListe, Boolean push) {
		User user = em.find(User.class, idUser);
		Listediffusion listediffusion = em.find(Listediffusion.class, idListe);
		
		if (user != null && listediffusion != null) {
			ListediffusionHasUser listediffusionHasUser = new ListediffusionHasUser(idListe, idUser);
			if(!user.getListediffusionHasUserList().contains(listediffusionHasUser)) {
				listediffusionHasUser.setNotifications(push);
				user.getListediffusionHasUserList().add(listediffusionHasUser);
				user = em.merge(user);
			}
		}

		return user;
	}

	@Transactional
	public User unsubscribeToListe(Integer idUser, Integer idListe) {
		User user = em.find(User.class, idUser);
		Listediffusion listediffusion = em.find(Listediffusion.class, idListe);
		
		if (user != null && listediffusion != null) {
			ListediffusionHasUser listediffusionHasUser = em.find(ListediffusionHasUser.class, new ListediffusionHasUserPK(idListe, idUser));

			user.getListediffusionHasUserList().remove(listediffusionHasUser);
			listediffusion.getListediffusionHasUserList().remove(listediffusionHasUser);
			
			user = em.merge(user);
		}

		return user;
	}

	@Transactional
	public Boolean editSubscriptionToListe(Integer idUser, Integer idListe,
			Boolean push) {
		User user = em.find(User.class, idUser);
		
		if(user != null) {
			int index = user.getListediffusionHasUserList().indexOf(new ListediffusionHasUser(idListe, idUser));
			if(index >= 0 && index < user.getListediffusionHasUserList().size()) {
				user.getListediffusionHasUserList().get(index).setNotifications(push);
				em.merge(user);
				
				return true;
			}
		}
		
		return false;
	}
	
}
