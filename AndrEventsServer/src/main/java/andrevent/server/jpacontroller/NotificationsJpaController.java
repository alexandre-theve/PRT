/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andrevent.server.jpacontroller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import andrevent.server.jpacontroller.exceptions.NonexistentEntityException;
import andrevent.server.jpacontroller.exceptions.PreexistingEntityException;
import andrevent.server.jpacontroller.exceptions.RollbackFailureException;
import andrevent.server.model.Evenement;
import andrevent.server.model.Notifications;
import andrevent.server.model.Typenotification;

/**
 *
 * @author Alex
 */
public class NotificationsJpaController implements Serializable {

    public NotificationsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notifications notifications) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Evenement evenementid = notifications.getEvenementid();
            if (evenementid != null) {
                evenementid = em.getReference(evenementid.getClass(), evenementid.getId());
                notifications.setEvenementid(evenementid);
            }
            Typenotification type = notifications.getType();
            if (type != null) {
                type = em.getReference(type.getClass(), type.getId());
                notifications.setType(type);
            }
            em.persist(notifications);
            if (evenementid != null) {
                evenementid.getNotificationsList().add(notifications);
                evenementid = em.merge(evenementid);
            }
            if (type != null) {
                type.getNotificationsList().add(notifications);
                type = em.merge(type);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findNotifications(notifications.getId()) != null) {
                throw new PreexistingEntityException("Notifications " + notifications + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notifications notifications) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Notifications persistentNotifications = em.find(Notifications.class, notifications.getId());
            Evenement evenementidOld = persistentNotifications.getEvenementid();
            Evenement evenementidNew = notifications.getEvenementid();
            Typenotification typeOld = persistentNotifications.getType();
            Typenotification typeNew = notifications.getType();
            if (evenementidNew != null) {
                evenementidNew = em.getReference(evenementidNew.getClass(), evenementidNew.getId());
                notifications.setEvenementid(evenementidNew);
            }
            if (typeNew != null) {
                typeNew = em.getReference(typeNew.getClass(), typeNew.getId());
                notifications.setType(typeNew);
            }
            notifications = em.merge(notifications);
            if (evenementidOld != null && !evenementidOld.equals(evenementidNew)) {
                evenementidOld.getNotificationsList().remove(notifications);
                evenementidOld = em.merge(evenementidOld);
            }
            if (evenementidNew != null && !evenementidNew.equals(evenementidOld)) {
                evenementidNew.getNotificationsList().add(notifications);
                evenementidNew = em.merge(evenementidNew);
            }
            if (typeOld != null && !typeOld.equals(typeNew)) {
                typeOld.getNotificationsList().remove(notifications);
                typeOld = em.merge(typeOld);
            }
            if (typeNew != null && !typeNew.equals(typeOld)) {
                typeNew.getNotificationsList().add(notifications);
                typeNew = em.merge(typeNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notifications.getId();
                if (findNotifications(id) == null) {
                    throw new NonexistentEntityException("The notifications with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Notifications notifications;
            try {
                notifications = em.getReference(Notifications.class, id);
                notifications.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notifications with id " + id + " no longer exists.", enfe);
            }
            Evenement evenementid = notifications.getEvenementid();
            if (evenementid != null) {
                evenementid.getNotificationsList().remove(notifications);
                evenementid = em.merge(evenementid);
            }
            Typenotification type = notifications.getType();
            if (type != null) {
                type.getNotificationsList().remove(notifications);
                type = em.merge(type);
            }
            em.remove(notifications);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notifications> findNotificationsEntities() {
        return findNotificationsEntities(true, -1, -1);
    }

    public List<Notifications> findNotificationsEntities(int maxResults, int firstResult) {
        return findNotificationsEntities(false, maxResults, firstResult);
    }

    private List<Notifications> findNotificationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notifications.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Notifications findNotifications(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notifications.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notifications> rt = cq.from(Notifications.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
