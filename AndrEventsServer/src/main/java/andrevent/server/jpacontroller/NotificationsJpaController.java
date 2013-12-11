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

import andrevent.server.model.Notifications;

/**
 * 
 * @author Alex
 */
public class NotificationsJpaController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2297986025679716593L;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void create(Notifications notifications) {
		em.persist(notifications);
	}

	@Transactional
	public void edit(Notifications notifications) {
		notifications = em.merge(notifications);
	}

	@Transactional
	public void destroy(Integer notifications) {
		em.remove(notifications);
	}

	public List<Notifications> findNotificationsEntities() {
		return findNotificationsEntities(true, -1, -1);
	}

	public List<Notifications> findNotificationsEntities(int maxResults,
			int firstResult) {
		return findNotificationsEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Notifications> findNotificationsEntities(boolean all,
			int maxResults, int firstResult) {
		CriteriaQuery<Notifications> cq = em.getCriteriaBuilder().createQuery(Notifications.class);
		cq.select(cq.from(Notifications.class));
		Query q = em.createQuery(cq);
		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	public Notifications findNotifications(Integer id) {
		return em.find(Notifications.class, id);
	}

	public int getNotificationsCount() {
		CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Notifications> rt = cq.from(Notifications.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
