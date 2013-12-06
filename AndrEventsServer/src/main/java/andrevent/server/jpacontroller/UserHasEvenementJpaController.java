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
import andrevent.server.model.User;
import andrevent.server.model.UserHasEvenement;
import andrevent.server.model.UserHasEvenementPK;

/**
 *
 * @author Alex
 */
public class UserHasEvenementJpaController implements Serializable {

    public UserHasEvenementJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserHasEvenement userHasEvenement) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (userHasEvenement.getUserHasEvenementPK() == null) {
            userHasEvenement.setUserHasEvenementPK(new UserHasEvenementPK());
        }
        userHasEvenement.getUserHasEvenementPK().setUserid(userHasEvenement.getUser().getId());
        userHasEvenement.getUserHasEvenementPK().setEvenementid(userHasEvenement.getEvenement().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Evenement evenement = userHasEvenement.getEvenement();
            if (evenement != null) {
                evenement = em.getReference(evenement.getClass(), evenement.getId());
                userHasEvenement.setEvenement(evenement);
            }
            User user = userHasEvenement.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getId());
                userHasEvenement.setUser(user);
            }
            em.persist(userHasEvenement);
            if (evenement != null) {
                evenement.getUserHasEvenementList().add(userHasEvenement);
                evenement = em.merge(evenement);
            }
            if (user != null) {
                user.getUserHasEvenementList().add(userHasEvenement);
                user = em.merge(user);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUserHasEvenement(userHasEvenement.getUserHasEvenementPK()) != null) {
                throw new PreexistingEntityException("UserHasEvenement " + userHasEvenement + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserHasEvenement userHasEvenement) throws NonexistentEntityException, RollbackFailureException, Exception {
        userHasEvenement.getUserHasEvenementPK().setUserid(userHasEvenement.getUser().getId());
        userHasEvenement.getUserHasEvenementPK().setEvenementid(userHasEvenement.getEvenement().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UserHasEvenement persistentUserHasEvenement = em.find(UserHasEvenement.class, userHasEvenement.getUserHasEvenementPK());
            Evenement evenementOld = persistentUserHasEvenement.getEvenement();
            Evenement evenementNew = userHasEvenement.getEvenement();
            User userOld = persistentUserHasEvenement.getUser();
            User userNew = userHasEvenement.getUser();
            if (evenementNew != null) {
                evenementNew = em.getReference(evenementNew.getClass(), evenementNew.getId());
                userHasEvenement.setEvenement(evenementNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getId());
                userHasEvenement.setUser(userNew);
            }
            userHasEvenement = em.merge(userHasEvenement);
            if (evenementOld != null && !evenementOld.equals(evenementNew)) {
                evenementOld.getUserHasEvenementList().remove(userHasEvenement);
                evenementOld = em.merge(evenementOld);
            }
            if (evenementNew != null && !evenementNew.equals(evenementOld)) {
                evenementNew.getUserHasEvenementList().add(userHasEvenement);
                evenementNew = em.merge(evenementNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getUserHasEvenementList().remove(userHasEvenement);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getUserHasEvenementList().add(userHasEvenement);
                userNew = em.merge(userNew);
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
                UserHasEvenementPK id = userHasEvenement.getUserHasEvenementPK();
                if (findUserHasEvenement(id) == null) {
                    throw new NonexistentEntityException("The userHasEvenement with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UserHasEvenementPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UserHasEvenement userHasEvenement;
            try {
                userHasEvenement = em.getReference(UserHasEvenement.class, id);
                userHasEvenement.getUserHasEvenementPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userHasEvenement with id " + id + " no longer exists.", enfe);
            }
            Evenement evenement = userHasEvenement.getEvenement();
            if (evenement != null) {
                evenement.getUserHasEvenementList().remove(userHasEvenement);
                evenement = em.merge(evenement);
            }
            User user = userHasEvenement.getUser();
            if (user != null) {
                user.getUserHasEvenementList().remove(userHasEvenement);
                user = em.merge(user);
            }
            em.remove(userHasEvenement);
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

    public List<UserHasEvenement> findUserHasEvenementEntities() {
        return findUserHasEvenementEntities(true, -1, -1);
    }

    public List<UserHasEvenement> findUserHasEvenementEntities(int maxResults, int firstResult) {
        return findUserHasEvenementEntities(false, maxResults, firstResult);
    }

    private List<UserHasEvenement> findUserHasEvenementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserHasEvenement.class));
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

    public UserHasEvenement findUserHasEvenement(UserHasEvenementPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserHasEvenement.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserHasEvenementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserHasEvenement> rt = cq.from(UserHasEvenement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
