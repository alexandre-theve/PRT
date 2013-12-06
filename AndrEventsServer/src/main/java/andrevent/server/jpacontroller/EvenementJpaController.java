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

import andrevent.server.model.User;
import andrevent.server.model.Tags;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

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

    public EvenementJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evenement evenement) throws RollbackFailureException, Exception {
        if (evenement.getTagsList() == null) {
            evenement.setTagsList(new ArrayList<Tags>());
        }
        if (evenement.getListediffusionList() == null) {
            evenement.setListediffusionList(new ArrayList<Listediffusion>());
        }
        if (evenement.getNotificationsList() == null) {
            evenement.setNotificationsList(new ArrayList<Notifications>());
        }
        if (evenement.getUserHasEvenementList() == null) {
            evenement.setUserHasEvenementList(new ArrayList<UserHasEvenement>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User createur = evenement.getCreateur();
            if (createur != null) {
                createur = em.getReference(createur.getClass(), createur.getId());
                evenement.setCreateur(createur);
            }
            List<Tags> attachedTagsList = new ArrayList<Tags>();
            for (Tags tagsListTagsToAttach : evenement.getTagsList()) {
                tagsListTagsToAttach = em.getReference(tagsListTagsToAttach.getClass(), tagsListTagsToAttach.getId());
                attachedTagsList.add(tagsListTagsToAttach);
            }
            evenement.setTagsList(attachedTagsList);
            List<Listediffusion> attachedListediffusionList = new ArrayList<Listediffusion>();
            for (Listediffusion listediffusionListListediffusionToAttach : evenement.getListediffusionList()) {
                listediffusionListListediffusionToAttach = em.getReference(listediffusionListListediffusionToAttach.getClass(), listediffusionListListediffusionToAttach.getId());
                attachedListediffusionList.add(listediffusionListListediffusionToAttach);
            }
            evenement.setListediffusionList(attachedListediffusionList);
            List<Notifications> attachedNotificationsList = new ArrayList<Notifications>();
            for (Notifications notificationsListNotificationsToAttach : evenement.getNotificationsList()) {
                notificationsListNotificationsToAttach = em.getReference(notificationsListNotificationsToAttach.getClass(), notificationsListNotificationsToAttach.getId());
                attachedNotificationsList.add(notificationsListNotificationsToAttach);
            }
            evenement.setNotificationsList(attachedNotificationsList);
            List<UserHasEvenement> attachedUserHasEvenementList = new ArrayList<UserHasEvenement>();
            for (UserHasEvenement userHasEvenementListUserHasEvenementToAttach : evenement.getUserHasEvenementList()) {
                userHasEvenementListUserHasEvenementToAttach = em.getReference(userHasEvenementListUserHasEvenementToAttach.getClass(), userHasEvenementListUserHasEvenementToAttach.getUserHasEvenementPK());
                attachedUserHasEvenementList.add(userHasEvenementListUserHasEvenementToAttach);
            }
            evenement.setUserHasEvenementList(attachedUserHasEvenementList);
            em.persist(evenement);
            if (createur != null) {
                createur.getEvenementList().add(evenement);
                createur = em.merge(createur);
            }
            for (Tags tagsListTags : evenement.getTagsList()) {
                tagsListTags.getEvenementList().add(evenement);
                tagsListTags = em.merge(tagsListTags);
            }
            for (Listediffusion listediffusionListListediffusion : evenement.getListediffusionList()) {
                listediffusionListListediffusion.getEvenementList().add(evenement);
                listediffusionListListediffusion = em.merge(listediffusionListListediffusion);
            }
            for (Notifications notificationsListNotifications : evenement.getNotificationsList()) {
                Evenement oldEvenementidOfNotificationsListNotifications = notificationsListNotifications.getEvenementid();
                notificationsListNotifications.setEvenementid(evenement);
                notificationsListNotifications = em.merge(notificationsListNotifications);
                if (oldEvenementidOfNotificationsListNotifications != null) {
                    oldEvenementidOfNotificationsListNotifications.getNotificationsList().remove(notificationsListNotifications);
                    oldEvenementidOfNotificationsListNotifications = em.merge(oldEvenementidOfNotificationsListNotifications);
                }
            }
            for (UserHasEvenement userHasEvenementListUserHasEvenement : evenement.getUserHasEvenementList()) {
                Evenement oldEvenementOfUserHasEvenementListUserHasEvenement = userHasEvenementListUserHasEvenement.getEvenement();
                userHasEvenementListUserHasEvenement.setEvenement(evenement);
                userHasEvenementListUserHasEvenement = em.merge(userHasEvenementListUserHasEvenement);
                if (oldEvenementOfUserHasEvenementListUserHasEvenement != null) {
                    oldEvenementOfUserHasEvenementListUserHasEvenement.getUserHasEvenementList().remove(userHasEvenementListUserHasEvenement);
                    oldEvenementOfUserHasEvenementListUserHasEvenement = em.merge(oldEvenementOfUserHasEvenementListUserHasEvenement);
                }
            }
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

    public void edit(Evenement evenement) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Evenement persistentEvenement = em.find(Evenement.class, evenement.getId());
            User createurOld = persistentEvenement.getCreateur();
            User createurNew = evenement.getCreateur();
            List<Tags> tagsListOld = persistentEvenement.getTagsList();
            List<Tags> tagsListNew = evenement.getTagsList();
            List<Listediffusion> listediffusionListOld = persistentEvenement.getListediffusionList();
            List<Listediffusion> listediffusionListNew = evenement.getListediffusionList();
            List<Notifications> notificationsListOld = persistentEvenement.getNotificationsList();
            List<Notifications> notificationsListNew = evenement.getNotificationsList();
            List<UserHasEvenement> userHasEvenementListOld = persistentEvenement.getUserHasEvenementList();
            List<UserHasEvenement> userHasEvenementListNew = evenement.getUserHasEvenementList();
            List<String> illegalOrphanMessages = null;
            for (Notifications notificationsListOldNotifications : notificationsListOld) {
                if (!notificationsListNew.contains(notificationsListOldNotifications)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notifications " + notificationsListOldNotifications + " since its evenementid field is not nullable.");
                }
            }
            for (UserHasEvenement userHasEvenementListOldUserHasEvenement : userHasEvenementListOld) {
                if (!userHasEvenementListNew.contains(userHasEvenementListOldUserHasEvenement)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserHasEvenement " + userHasEvenementListOldUserHasEvenement + " since its evenement field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (createurNew != null) {
                createurNew = em.getReference(createurNew.getClass(), createurNew.getId());
                evenement.setCreateur(createurNew);
            }
            List<Tags> attachedTagsListNew = new ArrayList<Tags>();
            for (Tags tagsListNewTagsToAttach : tagsListNew) {
                tagsListNewTagsToAttach = em.getReference(tagsListNewTagsToAttach.getClass(), tagsListNewTagsToAttach.getId());
                attachedTagsListNew.add(tagsListNewTagsToAttach);
            }
            tagsListNew = attachedTagsListNew;
            evenement.setTagsList(tagsListNew);
            List<Listediffusion> attachedListediffusionListNew = new ArrayList<Listediffusion>();
            for (Listediffusion listediffusionListNewListediffusionToAttach : listediffusionListNew) {
                listediffusionListNewListediffusionToAttach = em.getReference(listediffusionListNewListediffusionToAttach.getClass(), listediffusionListNewListediffusionToAttach.getId());
                attachedListediffusionListNew.add(listediffusionListNewListediffusionToAttach);
            }
            listediffusionListNew = attachedListediffusionListNew;
            evenement.setListediffusionList(listediffusionListNew);
            List<Notifications> attachedNotificationsListNew = new ArrayList<Notifications>();
            for (Notifications notificationsListNewNotificationsToAttach : notificationsListNew) {
                notificationsListNewNotificationsToAttach = em.getReference(notificationsListNewNotificationsToAttach.getClass(), notificationsListNewNotificationsToAttach.getId());
                attachedNotificationsListNew.add(notificationsListNewNotificationsToAttach);
            }
            notificationsListNew = attachedNotificationsListNew;
            evenement.setNotificationsList(notificationsListNew);
            List<UserHasEvenement> attachedUserHasEvenementListNew = new ArrayList<UserHasEvenement>();
            for (UserHasEvenement userHasEvenementListNewUserHasEvenementToAttach : userHasEvenementListNew) {
                userHasEvenementListNewUserHasEvenementToAttach = em.getReference(userHasEvenementListNewUserHasEvenementToAttach.getClass(), userHasEvenementListNewUserHasEvenementToAttach.getUserHasEvenementPK());
                attachedUserHasEvenementListNew.add(userHasEvenementListNewUserHasEvenementToAttach);
            }
            userHasEvenementListNew = attachedUserHasEvenementListNew;
            evenement.setUserHasEvenementList(userHasEvenementListNew);
            evenement = em.merge(evenement);
            if (createurOld != null && !createurOld.equals(createurNew)) {
                createurOld.getEvenementList().remove(evenement);
                createurOld = em.merge(createurOld);
            }
            if (createurNew != null && !createurNew.equals(createurOld)) {
                createurNew.getEvenementList().add(evenement);
                createurNew = em.merge(createurNew);
            }
            for (Tags tagsListOldTags : tagsListOld) {
                if (!tagsListNew.contains(tagsListOldTags)) {
                    tagsListOldTags.getEvenementList().remove(evenement);
                    tagsListOldTags = em.merge(tagsListOldTags);
                }
            }
            for (Tags tagsListNewTags : tagsListNew) {
                if (!tagsListOld.contains(tagsListNewTags)) {
                    tagsListNewTags.getEvenementList().add(evenement);
                    tagsListNewTags = em.merge(tagsListNewTags);
                }
            }
            for (Listediffusion listediffusionListOldListediffusion : listediffusionListOld) {
                if (!listediffusionListNew.contains(listediffusionListOldListediffusion)) {
                    listediffusionListOldListediffusion.getEvenementList().remove(evenement);
                    listediffusionListOldListediffusion = em.merge(listediffusionListOldListediffusion);
                }
            }
            for (Listediffusion listediffusionListNewListediffusion : listediffusionListNew) {
                if (!listediffusionListOld.contains(listediffusionListNewListediffusion)) {
                    listediffusionListNewListediffusion.getEvenementList().add(evenement);
                    listediffusionListNewListediffusion = em.merge(listediffusionListNewListediffusion);
                }
            }
            for (Notifications notificationsListNewNotifications : notificationsListNew) {
                if (!notificationsListOld.contains(notificationsListNewNotifications)) {
                    Evenement oldEvenementidOfNotificationsListNewNotifications = notificationsListNewNotifications.getEvenementid();
                    notificationsListNewNotifications.setEvenementid(evenement);
                    notificationsListNewNotifications = em.merge(notificationsListNewNotifications);
                    if (oldEvenementidOfNotificationsListNewNotifications != null && !oldEvenementidOfNotificationsListNewNotifications.equals(evenement)) {
                        oldEvenementidOfNotificationsListNewNotifications.getNotificationsList().remove(notificationsListNewNotifications);
                        oldEvenementidOfNotificationsListNewNotifications = em.merge(oldEvenementidOfNotificationsListNewNotifications);
                    }
                }
            }
            for (UserHasEvenement userHasEvenementListNewUserHasEvenement : userHasEvenementListNew) {
                if (!userHasEvenementListOld.contains(userHasEvenementListNewUserHasEvenement)) {
                    Evenement oldEvenementOfUserHasEvenementListNewUserHasEvenement = userHasEvenementListNewUserHasEvenement.getEvenement();
                    userHasEvenementListNewUserHasEvenement.setEvenement(evenement);
                    userHasEvenementListNewUserHasEvenement = em.merge(userHasEvenementListNewUserHasEvenement);
                    if (oldEvenementOfUserHasEvenementListNewUserHasEvenement != null && !oldEvenementOfUserHasEvenementListNewUserHasEvenement.equals(evenement)) {
                        oldEvenementOfUserHasEvenementListNewUserHasEvenement.getUserHasEvenementList().remove(userHasEvenementListNewUserHasEvenement);
                        oldEvenementOfUserHasEvenementListNewUserHasEvenement = em.merge(oldEvenementOfUserHasEvenementListNewUserHasEvenement);
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
                Integer id = evenement.getId();
                if (findEvenement(id) == null) {
                    throw new NonexistentEntityException("The evenement with id " + id + " no longer exists.");
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
            Evenement evenement;
            try {
                evenement = em.getReference(Evenement.class, id);
                evenement.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evenement with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Notifications> notificationsListOrphanCheck = evenement.getNotificationsList();
            for (Notifications notificationsListOrphanCheckNotifications : notificationsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Evenement (" + evenement + ") cannot be destroyed since the Notifications " + notificationsListOrphanCheckNotifications + " in its notificationsList field has a non-nullable evenementid field.");
            }
            List<UserHasEvenement> userHasEvenementListOrphanCheck = evenement.getUserHasEvenementList();
            for (UserHasEvenement userHasEvenementListOrphanCheckUserHasEvenement : userHasEvenementListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Evenement (" + evenement + ") cannot be destroyed since the UserHasEvenement " + userHasEvenementListOrphanCheckUserHasEvenement + " in its userHasEvenementList field has a non-nullable evenement field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User createur = evenement.getCreateur();
            if (createur != null) {
                createur.getEvenementList().remove(evenement);
                createur = em.merge(createur);
            }
            List<Tags> tagsList = evenement.getTagsList();
            for (Tags tagsListTags : tagsList) {
                tagsListTags.getEvenementList().remove(evenement);
                tagsListTags = em.merge(tagsListTags);
            }
            List<Listediffusion> listediffusionList = evenement.getListediffusionList();
            for (Listediffusion listediffusionListListediffusion : listediffusionList) {
                listediffusionListListediffusion.getEvenementList().remove(evenement);
                listediffusionListListediffusion = em.merge(listediffusionListListediffusion);
            }
            em.remove(evenement);
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

    public List<Evenement> findEvenementEntities() {
        return findEvenementEntities(true, -1, -1);
    }

    public List<Evenement> findEvenementEntities(int maxResults, int firstResult) {
        return findEvenementEntities(false, maxResults, firstResult);
    }

    private List<Evenement> findEvenementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evenement.class));
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

    public Evenement findEvenement(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evenement.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvenementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evenement> rt = cq.from(Evenement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
