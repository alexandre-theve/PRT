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

import andrevent.server.model.ListediffusionHasUser;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import andrevent.server.jpacontroller.exceptions.IllegalOrphanException;
import andrevent.server.jpacontroller.exceptions.NonexistentEntityException;
import andrevent.server.jpacontroller.exceptions.PreexistingEntityException;
import andrevent.server.jpacontroller.exceptions.RollbackFailureException;
import andrevent.server.model.Evenement;
import andrevent.server.model.Recherches;
import andrevent.server.model.User;
import andrevent.server.model.UserHasEvenement;

/**
 *
 * @author Alex
 */
public class UserJpaController implements Serializable {

    public UserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (user.getListediffusionHasUserList() == null) {
            user.setListediffusionHasUserList(new ArrayList<ListediffusionHasUser>());
        }
        if (user.getEvenementList() == null) {
            user.setEvenementList(new ArrayList<Evenement>());
        }
        if (user.getRecherchesList() == null) {
            user.setRecherchesList(new ArrayList<Recherches>());
        }
        if (user.getUserHasEvenementList() == null) {
            user.setUserHasEvenementList(new ArrayList<UserHasEvenement>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<ListediffusionHasUser> attachedListediffusionHasUserList = new ArrayList<ListediffusionHasUser>();
            for (ListediffusionHasUser listediffusionHasUserListListediffusionHasUserToAttach : user.getListediffusionHasUserList()) {
                listediffusionHasUserListListediffusionHasUserToAttach = em.getReference(listediffusionHasUserListListediffusionHasUserToAttach.getClass(), listediffusionHasUserListListediffusionHasUserToAttach.getListediffusionHasUserPK());
                attachedListediffusionHasUserList.add(listediffusionHasUserListListediffusionHasUserToAttach);
            }
            user.setListediffusionHasUserList(attachedListediffusionHasUserList);
            List<Evenement> attachedEvenementList = new ArrayList<Evenement>();
            for (Evenement evenementListEvenementToAttach : user.getEvenementList()) {
                evenementListEvenementToAttach = em.getReference(evenementListEvenementToAttach.getClass(), evenementListEvenementToAttach.getId());
                attachedEvenementList.add(evenementListEvenementToAttach);
            }
            user.setEvenementList(attachedEvenementList);
            List<Recherches> attachedRecherchesList = new ArrayList<Recherches>();
            for (Recherches recherchesListRecherchesToAttach : user.getRecherchesList()) {
                recherchesListRecherchesToAttach = em.getReference(recherchesListRecherchesToAttach.getClass(), recherchesListRecherchesToAttach.getId());
                attachedRecherchesList.add(recherchesListRecherchesToAttach);
            }
            user.setRecherchesList(attachedRecherchesList);
            List<UserHasEvenement> attachedUserHasEvenementList = new ArrayList<UserHasEvenement>();
            for (UserHasEvenement userHasEvenementListUserHasEvenementToAttach : user.getUserHasEvenementList()) {
                userHasEvenementListUserHasEvenementToAttach = em.getReference(userHasEvenementListUserHasEvenementToAttach.getClass(), userHasEvenementListUserHasEvenementToAttach.getUserHasEvenementPK());
                attachedUserHasEvenementList.add(userHasEvenementListUserHasEvenementToAttach);
            }
            user.setUserHasEvenementList(attachedUserHasEvenementList);
            em.persist(user);
            for (ListediffusionHasUser listediffusionHasUserListListediffusionHasUser : user.getListediffusionHasUserList()) {
                User oldUserOfListediffusionHasUserListListediffusionHasUser = listediffusionHasUserListListediffusionHasUser.getUser();
                listediffusionHasUserListListediffusionHasUser.setUser(user);
                listediffusionHasUserListListediffusionHasUser = em.merge(listediffusionHasUserListListediffusionHasUser);
                if (oldUserOfListediffusionHasUserListListediffusionHasUser != null) {
                    oldUserOfListediffusionHasUserListListediffusionHasUser.getListediffusionHasUserList().remove(listediffusionHasUserListListediffusionHasUser);
                    oldUserOfListediffusionHasUserListListediffusionHasUser = em.merge(oldUserOfListediffusionHasUserListListediffusionHasUser);
                }
            }
            for (Evenement evenementListEvenement : user.getEvenementList()) {
                User oldCreateurOfEvenementListEvenement = evenementListEvenement.getCreateur();
                evenementListEvenement.setCreateur(user);
                evenementListEvenement = em.merge(evenementListEvenement);
                if (oldCreateurOfEvenementListEvenement != null) {
                    oldCreateurOfEvenementListEvenement.getEvenementList().remove(evenementListEvenement);
                    oldCreateurOfEvenementListEvenement = em.merge(oldCreateurOfEvenementListEvenement);
                }
            }
            for (Recherches recherchesListRecherches : user.getRecherchesList()) {
                User oldUseridOfRecherchesListRecherches = recherchesListRecherches.getUserid();
                recherchesListRecherches.setUserid(user);
                recherchesListRecherches = em.merge(recherchesListRecherches);
                if (oldUseridOfRecherchesListRecherches != null) {
                    oldUseridOfRecherchesListRecherches.getRecherchesList().remove(recherchesListRecherches);
                    oldUseridOfRecherchesListRecherches = em.merge(oldUseridOfRecherchesListRecherches);
                }
            }
            for (UserHasEvenement userHasEvenementListUserHasEvenement : user.getUserHasEvenementList()) {
                User oldUserOfUserHasEvenementListUserHasEvenement = userHasEvenementListUserHasEvenement.getUser();
                userHasEvenementListUserHasEvenement.setUser(user);
                userHasEvenementListUserHasEvenement = em.merge(userHasEvenementListUserHasEvenement);
                if (oldUserOfUserHasEvenementListUserHasEvenement != null) {
                    oldUserOfUserHasEvenementListUserHasEvenement.getUserHasEvenementList().remove(userHasEvenementListUserHasEvenement);
                    oldUserOfUserHasEvenementListUserHasEvenement = em.merge(oldUserOfUserHasEvenementListUserHasEvenement);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUser(user.getId()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getId());
            List<ListediffusionHasUser> listediffusionHasUserListOld = persistentUser.getListediffusionHasUserList();
            List<ListediffusionHasUser> listediffusionHasUserListNew = user.getListediffusionHasUserList();
            List<Evenement> evenementListOld = persistentUser.getEvenementList();
            List<Evenement> evenementListNew = user.getEvenementList();
            List<Recherches> recherchesListOld = persistentUser.getRecherchesList();
            List<Recherches> recherchesListNew = user.getRecherchesList();
            List<UserHasEvenement> userHasEvenementListOld = persistentUser.getUserHasEvenementList();
            List<UserHasEvenement> userHasEvenementListNew = user.getUserHasEvenementList();
            List<String> illegalOrphanMessages = null;
            for (ListediffusionHasUser listediffusionHasUserListOldListediffusionHasUser : listediffusionHasUserListOld) {
                if (!listediffusionHasUserListNew.contains(listediffusionHasUserListOldListediffusionHasUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ListediffusionHasUser " + listediffusionHasUserListOldListediffusionHasUser + " since its user field is not nullable.");
                }
            }
            for (Evenement evenementListOldEvenement : evenementListOld) {
                if (!evenementListNew.contains(evenementListOldEvenement)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evenement " + evenementListOldEvenement + " since its createur field is not nullable.");
                }
            }
            for (Recherches recherchesListOldRecherches : recherchesListOld) {
                if (!recherchesListNew.contains(recherchesListOldRecherches)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Recherches " + recherchesListOldRecherches + " since its userid field is not nullable.");
                }
            }
            for (UserHasEvenement userHasEvenementListOldUserHasEvenement : userHasEvenementListOld) {
                if (!userHasEvenementListNew.contains(userHasEvenementListOldUserHasEvenement)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserHasEvenement " + userHasEvenementListOldUserHasEvenement + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ListediffusionHasUser> attachedListediffusionHasUserListNew = new ArrayList<ListediffusionHasUser>();
            for (ListediffusionHasUser listediffusionHasUserListNewListediffusionHasUserToAttach : listediffusionHasUserListNew) {
                listediffusionHasUserListNewListediffusionHasUserToAttach = em.getReference(listediffusionHasUserListNewListediffusionHasUserToAttach.getClass(), listediffusionHasUserListNewListediffusionHasUserToAttach.getListediffusionHasUserPK());
                attachedListediffusionHasUserListNew.add(listediffusionHasUserListNewListediffusionHasUserToAttach);
            }
            listediffusionHasUserListNew = attachedListediffusionHasUserListNew;
            user.setListediffusionHasUserList(listediffusionHasUserListNew);
            List<Evenement> attachedEvenementListNew = new ArrayList<Evenement>();
            for (Evenement evenementListNewEvenementToAttach : evenementListNew) {
                evenementListNewEvenementToAttach = em.getReference(evenementListNewEvenementToAttach.getClass(), evenementListNewEvenementToAttach.getId());
                attachedEvenementListNew.add(evenementListNewEvenementToAttach);
            }
            evenementListNew = attachedEvenementListNew;
            user.setEvenementList(evenementListNew);
            List<Recherches> attachedRecherchesListNew = new ArrayList<Recherches>();
            for (Recherches recherchesListNewRecherchesToAttach : recherchesListNew) {
                recherchesListNewRecherchesToAttach = em.getReference(recherchesListNewRecherchesToAttach.getClass(), recherchesListNewRecherchesToAttach.getId());
                attachedRecherchesListNew.add(recherchesListNewRecherchesToAttach);
            }
            recherchesListNew = attachedRecherchesListNew;
            user.setRecherchesList(recherchesListNew);
            List<UserHasEvenement> attachedUserHasEvenementListNew = new ArrayList<UserHasEvenement>();
            for (UserHasEvenement userHasEvenementListNewUserHasEvenementToAttach : userHasEvenementListNew) {
                userHasEvenementListNewUserHasEvenementToAttach = em.getReference(userHasEvenementListNewUserHasEvenementToAttach.getClass(), userHasEvenementListNewUserHasEvenementToAttach.getUserHasEvenementPK());
                attachedUserHasEvenementListNew.add(userHasEvenementListNewUserHasEvenementToAttach);
            }
            userHasEvenementListNew = attachedUserHasEvenementListNew;
            user.setUserHasEvenementList(userHasEvenementListNew);
            user = em.merge(user);
            for (ListediffusionHasUser listediffusionHasUserListNewListediffusionHasUser : listediffusionHasUserListNew) {
                if (!listediffusionHasUserListOld.contains(listediffusionHasUserListNewListediffusionHasUser)) {
                    User oldUserOfListediffusionHasUserListNewListediffusionHasUser = listediffusionHasUserListNewListediffusionHasUser.getUser();
                    listediffusionHasUserListNewListediffusionHasUser.setUser(user);
                    listediffusionHasUserListNewListediffusionHasUser = em.merge(listediffusionHasUserListNewListediffusionHasUser);
                    if (oldUserOfListediffusionHasUserListNewListediffusionHasUser != null && !oldUserOfListediffusionHasUserListNewListediffusionHasUser.equals(user)) {
                        oldUserOfListediffusionHasUserListNewListediffusionHasUser.getListediffusionHasUserList().remove(listediffusionHasUserListNewListediffusionHasUser);
                        oldUserOfListediffusionHasUserListNewListediffusionHasUser = em.merge(oldUserOfListediffusionHasUserListNewListediffusionHasUser);
                    }
                }
            }
            for (Evenement evenementListNewEvenement : evenementListNew) {
                if (!evenementListOld.contains(evenementListNewEvenement)) {
                    User oldCreateurOfEvenementListNewEvenement = evenementListNewEvenement.getCreateur();
                    evenementListNewEvenement.setCreateur(user);
                    evenementListNewEvenement = em.merge(evenementListNewEvenement);
                    if (oldCreateurOfEvenementListNewEvenement != null && !oldCreateurOfEvenementListNewEvenement.equals(user)) {
                        oldCreateurOfEvenementListNewEvenement.getEvenementList().remove(evenementListNewEvenement);
                        oldCreateurOfEvenementListNewEvenement = em.merge(oldCreateurOfEvenementListNewEvenement);
                    }
                }
            }
            for (Recherches recherchesListNewRecherches : recherchesListNew) {
                if (!recherchesListOld.contains(recherchesListNewRecherches)) {
                    User oldUseridOfRecherchesListNewRecherches = recherchesListNewRecherches.getUserid();
                    recherchesListNewRecherches.setUserid(user);
                    recherchesListNewRecherches = em.merge(recherchesListNewRecherches);
                    if (oldUseridOfRecherchesListNewRecherches != null && !oldUseridOfRecherchesListNewRecherches.equals(user)) {
                        oldUseridOfRecherchesListNewRecherches.getRecherchesList().remove(recherchesListNewRecherches);
                        oldUseridOfRecherchesListNewRecherches = em.merge(oldUseridOfRecherchesListNewRecherches);
                    }
                }
            }
            for (UserHasEvenement userHasEvenementListNewUserHasEvenement : userHasEvenementListNew) {
                if (!userHasEvenementListOld.contains(userHasEvenementListNewUserHasEvenement)) {
                    User oldUserOfUserHasEvenementListNewUserHasEvenement = userHasEvenementListNewUserHasEvenement.getUser();
                    userHasEvenementListNewUserHasEvenement.setUser(user);
                    userHasEvenementListNewUserHasEvenement = em.merge(userHasEvenementListNewUserHasEvenement);
                    if (oldUserOfUserHasEvenementListNewUserHasEvenement != null && !oldUserOfUserHasEvenementListNewUserHasEvenement.equals(user)) {
                        oldUserOfUserHasEvenementListNewUserHasEvenement.getUserHasEvenementList().remove(userHasEvenementListNewUserHasEvenement);
                        oldUserOfUserHasEvenementListNewUserHasEvenement = em.merge(oldUserOfUserHasEvenementListNewUserHasEvenement);
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
                Integer id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ListediffusionHasUser> listediffusionHasUserListOrphanCheck = user.getListediffusionHasUserList();
            for (ListediffusionHasUser listediffusionHasUserListOrphanCheckListediffusionHasUser : listediffusionHasUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the ListediffusionHasUser " + listediffusionHasUserListOrphanCheckListediffusionHasUser + " in its listediffusionHasUserList field has a non-nullable user field.");
            }
            List<Evenement> evenementListOrphanCheck = user.getEvenementList();
            for (Evenement evenementListOrphanCheckEvenement : evenementListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Evenement " + evenementListOrphanCheckEvenement + " in its evenementList field has a non-nullable createur field.");
            }
            List<Recherches> recherchesListOrphanCheck = user.getRecherchesList();
            for (Recherches recherchesListOrphanCheckRecherches : recherchesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Recherches " + recherchesListOrphanCheckRecherches + " in its recherchesList field has a non-nullable userid field.");
            }
            List<UserHasEvenement> userHasEvenementListOrphanCheck = user.getUserHasEvenementList();
            for (UserHasEvenement userHasEvenementListOrphanCheckUserHasEvenement : userHasEvenementListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the UserHasEvenement " + userHasEvenementListOrphanCheckUserHasEvenement + " in its userHasEvenementList field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
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

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
