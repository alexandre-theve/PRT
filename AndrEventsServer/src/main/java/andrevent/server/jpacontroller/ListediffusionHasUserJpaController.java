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
import andrevent.server.model.User;
import andrevent.server.model.Listediffusion;
import andrevent.server.model.ListediffusionHasUser;
import andrevent.server.model.ListediffusionHasUserPK;

/**
 *
 * @author Alex
 */
public class ListediffusionHasUserJpaController implements Serializable {

    public ListediffusionHasUserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListediffusionHasUser listediffusionHasUser) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (listediffusionHasUser.getListediffusionHasUserPK() == null) {
            listediffusionHasUser.setListediffusionHasUserPK(new ListediffusionHasUserPK());
        }
        listediffusionHasUser.getListediffusionHasUserPK().setUserid(listediffusionHasUser.getUser().getId());
        listediffusionHasUser.getListediffusionHasUserPK().setListeDiffusionid(listediffusionHasUser.getListediffusion().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User user = listediffusionHasUser.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getId());
                listediffusionHasUser.setUser(user);
            }
            Listediffusion listediffusion = listediffusionHasUser.getListediffusion();
            if (listediffusion != null) {
                listediffusion = em.getReference(listediffusion.getClass(), listediffusion.getId());
                listediffusionHasUser.setListediffusion(listediffusion);
            }
            em.persist(listediffusionHasUser);
            if (user != null) {
                user.getListediffusionHasUserList().add(listediffusionHasUser);
                user = em.merge(user);
            }
            if (listediffusion != null) {
                listediffusion.getListediffusionHasUserList().add(listediffusionHasUser);
                listediffusion = em.merge(listediffusion);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findListediffusionHasUser(listediffusionHasUser.getListediffusionHasUserPK()) != null) {
                throw new PreexistingEntityException("ListediffusionHasUser " + listediffusionHasUser + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListediffusionHasUser listediffusionHasUser) throws NonexistentEntityException, RollbackFailureException, Exception {
        listediffusionHasUser.getListediffusionHasUserPK().setUserid(listediffusionHasUser.getUser().getId());
        listediffusionHasUser.getListediffusionHasUserPK().setListeDiffusionid(listediffusionHasUser.getListediffusion().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ListediffusionHasUser persistentListediffusionHasUser = em.find(ListediffusionHasUser.class, listediffusionHasUser.getListediffusionHasUserPK());
            User userOld = persistentListediffusionHasUser.getUser();
            User userNew = listediffusionHasUser.getUser();
            Listediffusion listediffusionOld = persistentListediffusionHasUser.getListediffusion();
            Listediffusion listediffusionNew = listediffusionHasUser.getListediffusion();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getId());
                listediffusionHasUser.setUser(userNew);
            }
            if (listediffusionNew != null) {
                listediffusionNew = em.getReference(listediffusionNew.getClass(), listediffusionNew.getId());
                listediffusionHasUser.setListediffusion(listediffusionNew);
            }
            listediffusionHasUser = em.merge(listediffusionHasUser);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getListediffusionHasUserList().remove(listediffusionHasUser);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getListediffusionHasUserList().add(listediffusionHasUser);
                userNew = em.merge(userNew);
            }
            if (listediffusionOld != null && !listediffusionOld.equals(listediffusionNew)) {
                listediffusionOld.getListediffusionHasUserList().remove(listediffusionHasUser);
                listediffusionOld = em.merge(listediffusionOld);
            }
            if (listediffusionNew != null && !listediffusionNew.equals(listediffusionOld)) {
                listediffusionNew.getListediffusionHasUserList().add(listediffusionHasUser);
                listediffusionNew = em.merge(listediffusionNew);
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
                ListediffusionHasUserPK id = listediffusionHasUser.getListediffusionHasUserPK();
                if (findListediffusionHasUser(id) == null) {
                    throw new NonexistentEntityException("The listediffusionHasUser with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ListediffusionHasUserPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ListediffusionHasUser listediffusionHasUser;
            try {
                listediffusionHasUser = em.getReference(ListediffusionHasUser.class, id);
                listediffusionHasUser.getListediffusionHasUserPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listediffusionHasUser with id " + id + " no longer exists.", enfe);
            }
            User user = listediffusionHasUser.getUser();
            if (user != null) {
                user.getListediffusionHasUserList().remove(listediffusionHasUser);
                user = em.merge(user);
            }
            Listediffusion listediffusion = listediffusionHasUser.getListediffusion();
            if (listediffusion != null) {
                listediffusion.getListediffusionHasUserList().remove(listediffusionHasUser);
                listediffusion = em.merge(listediffusion);
            }
            em.remove(listediffusionHasUser);
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

    public List<ListediffusionHasUser> findListediffusionHasUserEntities() {
        return findListediffusionHasUserEntities(true, -1, -1);
    }

    public List<ListediffusionHasUser> findListediffusionHasUserEntities(int maxResults, int firstResult) {
        return findListediffusionHasUserEntities(false, maxResults, firstResult);
    }

    private List<ListediffusionHasUser> findListediffusionHasUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListediffusionHasUser.class));
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

    public ListediffusionHasUser findListediffusionHasUser(ListediffusionHasUserPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListediffusionHasUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getListediffusionHasUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListediffusionHasUser> rt = cq.from(ListediffusionHasUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
