/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andrevent.server.jpacontroller;

import java.io.Serializable;

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

    public TypenotificationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Typenotification typenotification) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (typenotification.getNotificationsList() == null) {
            typenotification.setNotificationsList(new ArrayList<Notifications>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Notifications> attachedNotificationsList = new ArrayList<Notifications>();
            for (Notifications notificationsListNotificationsToAttach : typenotification.getNotificationsList()) {
                notificationsListNotificationsToAttach = em.getReference(notificationsListNotificationsToAttach.getClass(), notificationsListNotificationsToAttach.getId());
                attachedNotificationsList.add(notificationsListNotificationsToAttach);
            }
            typenotification.setNotificationsList(attachedNotificationsList);
            em.persist(typenotification);
            for (Notifications notificationsListNotifications : typenotification.getNotificationsList()) {
                Typenotification oldTypeOfNotificationsListNotifications = notificationsListNotifications.getType();
                notificationsListNotifications.setType(typenotification);
                notificationsListNotifications = em.merge(notificationsListNotifications);
                if (oldTypeOfNotificationsListNotifications != null) {
                    oldTypeOfNotificationsListNotifications.getNotificationsList().remove(notificationsListNotifications);
                    oldTypeOfNotificationsListNotifications = em.merge(oldTypeOfNotificationsListNotifications);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTypenotification(typenotification.getId()) != null) {
                throw new PreexistingEntityException("Typenotification " + typenotification + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Typenotification typenotification) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Typenotification persistentTypenotification = em.find(Typenotification.class, typenotification.getId());
            List<Notifications> notificationsListOld = persistentTypenotification.getNotificationsList();
            List<Notifications> notificationsListNew = typenotification.getNotificationsList();
            List<String> illegalOrphanMessages = null;
            for (Notifications notificationsListOldNotifications : notificationsListOld) {
                if (!notificationsListNew.contains(notificationsListOldNotifications)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notifications " + notificationsListOldNotifications + " since its type field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Notifications> attachedNotificationsListNew = new ArrayList<Notifications>();
            for (Notifications notificationsListNewNotificationsToAttach : notificationsListNew) {
                notificationsListNewNotificationsToAttach = em.getReference(notificationsListNewNotificationsToAttach.getClass(), notificationsListNewNotificationsToAttach.getId());
                attachedNotificationsListNew.add(notificationsListNewNotificationsToAttach);
            }
            notificationsListNew = attachedNotificationsListNew;
            typenotification.setNotificationsList(notificationsListNew);
            typenotification = em.merge(typenotification);
            for (Notifications notificationsListNewNotifications : notificationsListNew) {
                if (!notificationsListOld.contains(notificationsListNewNotifications)) {
                    Typenotification oldTypeOfNotificationsListNewNotifications = notificationsListNewNotifications.getType();
                    notificationsListNewNotifications.setType(typenotification);
                    notificationsListNewNotifications = em.merge(notificationsListNewNotifications);
                    if (oldTypeOfNotificationsListNewNotifications != null && !oldTypeOfNotificationsListNewNotifications.equals(typenotification)) {
                        oldTypeOfNotificationsListNewNotifications.getNotificationsList().remove(notificationsListNewNotifications);
                        oldTypeOfNotificationsListNewNotifications = em.merge(oldTypeOfNotificationsListNewNotifications);
                    }
                }
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
                Integer id = typenotification.getId();
                if (findTypenotification(id) == null) {
                    throw new NonexistentEntityException("The typenotification with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Typenotification typenotification;
            try {
                typenotification = em.getReference(Typenotification.class, id);
                typenotification.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The typenotification with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Notifications> notificationsListOrphanCheck = typenotification.getNotificationsList();
            for (Notifications notificationsListOrphanCheckNotifications : notificationsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Typenotification (" + typenotification + ") cannot be destroyed since the Notifications " + notificationsListOrphanCheckNotifications + " in its notificationsList field has a non-nullable type field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(typenotification);
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

    public List<Typenotification> findTypenotificationEntities() {
        return findTypenotificationEntities(true, -1, -1);
    }

    public List<Typenotification> findTypenotificationEntities(int maxResults, int firstResult) {
        return findTypenotificationEntities(false, maxResults, firstResult);
    }

    private List<Typenotification> findTypenotificationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Typenotification.class));
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

    public Typenotification findTypenotification(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Typenotification.class, id);
        } finally {
            em.close();
        }
    }

    public int getTypenotificationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Typenotification> rt = cq.from(Typenotification.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
