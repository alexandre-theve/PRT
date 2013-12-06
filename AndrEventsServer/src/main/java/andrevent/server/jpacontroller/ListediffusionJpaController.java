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

import andrevent.server.model.Evenement;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import andrevent.server.jpacontroller.exceptions.IllegalOrphanException;
import andrevent.server.jpacontroller.exceptions.NonexistentEntityException;
import andrevent.server.jpacontroller.exceptions.PreexistingEntityException;
import andrevent.server.jpacontroller.exceptions.RollbackFailureException;
import andrevent.server.model.Listediffusion;
import andrevent.server.model.ListediffusionHasUser;

/**
 *
 * @author Alex
 */
public class ListediffusionJpaController implements Serializable {

    public ListediffusionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Listediffusion listediffusion) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (listediffusion.getEvenementList() == null) {
            listediffusion.setEvenementList(new ArrayList<Evenement>());
        }
        if (listediffusion.getListediffusionHasUserList() == null) {
            listediffusion.setListediffusionHasUserList(new ArrayList<ListediffusionHasUser>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Evenement> attachedEvenementList = new ArrayList<Evenement>();
            for (Evenement evenementListEvenementToAttach : listediffusion.getEvenementList()) {
                evenementListEvenementToAttach = em.getReference(evenementListEvenementToAttach.getClass(), evenementListEvenementToAttach.getId());
                attachedEvenementList.add(evenementListEvenementToAttach);
            }
            listediffusion.setEvenementList(attachedEvenementList);
            List<ListediffusionHasUser> attachedListediffusionHasUserList = new ArrayList<ListediffusionHasUser>();
            for (ListediffusionHasUser listediffusionHasUserListListediffusionHasUserToAttach : listediffusion.getListediffusionHasUserList()) {
                listediffusionHasUserListListediffusionHasUserToAttach = em.getReference(listediffusionHasUserListListediffusionHasUserToAttach.getClass(), listediffusionHasUserListListediffusionHasUserToAttach.getListediffusionHasUserPK());
                attachedListediffusionHasUserList.add(listediffusionHasUserListListediffusionHasUserToAttach);
            }
            listediffusion.setListediffusionHasUserList(attachedListediffusionHasUserList);
            em.persist(listediffusion);
            for (Evenement evenementListEvenement : listediffusion.getEvenementList()) {
                evenementListEvenement.getListediffusionList().add(listediffusion);
                evenementListEvenement = em.merge(evenementListEvenement);
            }
            for (ListediffusionHasUser listediffusionHasUserListListediffusionHasUser : listediffusion.getListediffusionHasUserList()) {
                Listediffusion oldListediffusionOfListediffusionHasUserListListediffusionHasUser = listediffusionHasUserListListediffusionHasUser.getListediffusion();
                listediffusionHasUserListListediffusionHasUser.setListediffusion(listediffusion);
                listediffusionHasUserListListediffusionHasUser = em.merge(listediffusionHasUserListListediffusionHasUser);
                if (oldListediffusionOfListediffusionHasUserListListediffusionHasUser != null) {
                    oldListediffusionOfListediffusionHasUserListListediffusionHasUser.getListediffusionHasUserList().remove(listediffusionHasUserListListediffusionHasUser);
                    oldListediffusionOfListediffusionHasUserListListediffusionHasUser = em.merge(oldListediffusionOfListediffusionHasUserListListediffusionHasUser);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findListediffusion(listediffusion.getId()) != null) {
                throw new PreexistingEntityException("Listediffusion " + listediffusion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Listediffusion listediffusion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Listediffusion persistentListediffusion = em.find(Listediffusion.class, listediffusion.getId());
            List<Evenement> evenementListOld = persistentListediffusion.getEvenementList();
            List<Evenement> evenementListNew = listediffusion.getEvenementList();
            List<ListediffusionHasUser> listediffusionHasUserListOld = persistentListediffusion.getListediffusionHasUserList();
            List<ListediffusionHasUser> listediffusionHasUserListNew = listediffusion.getListediffusionHasUserList();
            List<String> illegalOrphanMessages = null;
            for (ListediffusionHasUser listediffusionHasUserListOldListediffusionHasUser : listediffusionHasUserListOld) {
                if (!listediffusionHasUserListNew.contains(listediffusionHasUserListOldListediffusionHasUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ListediffusionHasUser " + listediffusionHasUserListOldListediffusionHasUser + " since its listediffusion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Evenement> attachedEvenementListNew = new ArrayList<Evenement>();
            for (Evenement evenementListNewEvenementToAttach : evenementListNew) {
                evenementListNewEvenementToAttach = em.getReference(evenementListNewEvenementToAttach.getClass(), evenementListNewEvenementToAttach.getId());
                attachedEvenementListNew.add(evenementListNewEvenementToAttach);
            }
            evenementListNew = attachedEvenementListNew;
            listediffusion.setEvenementList(evenementListNew);
            List<ListediffusionHasUser> attachedListediffusionHasUserListNew = new ArrayList<ListediffusionHasUser>();
            for (ListediffusionHasUser listediffusionHasUserListNewListediffusionHasUserToAttach : listediffusionHasUserListNew) {
                listediffusionHasUserListNewListediffusionHasUserToAttach = em.getReference(listediffusionHasUserListNewListediffusionHasUserToAttach.getClass(), listediffusionHasUserListNewListediffusionHasUserToAttach.getListediffusionHasUserPK());
                attachedListediffusionHasUserListNew.add(listediffusionHasUserListNewListediffusionHasUserToAttach);
            }
            listediffusionHasUserListNew = attachedListediffusionHasUserListNew;
            listediffusion.setListediffusionHasUserList(listediffusionHasUserListNew);
            listediffusion = em.merge(listediffusion);
            for (Evenement evenementListOldEvenement : evenementListOld) {
                if (!evenementListNew.contains(evenementListOldEvenement)) {
                    evenementListOldEvenement.getListediffusionList().remove(listediffusion);
                    evenementListOldEvenement = em.merge(evenementListOldEvenement);
                }
            }
            for (Evenement evenementListNewEvenement : evenementListNew) {
                if (!evenementListOld.contains(evenementListNewEvenement)) {
                    evenementListNewEvenement.getListediffusionList().add(listediffusion);
                    evenementListNewEvenement = em.merge(evenementListNewEvenement);
                }
            }
            for (ListediffusionHasUser listediffusionHasUserListNewListediffusionHasUser : listediffusionHasUserListNew) {
                if (!listediffusionHasUserListOld.contains(listediffusionHasUserListNewListediffusionHasUser)) {
                    Listediffusion oldListediffusionOfListediffusionHasUserListNewListediffusionHasUser = listediffusionHasUserListNewListediffusionHasUser.getListediffusion();
                    listediffusionHasUserListNewListediffusionHasUser.setListediffusion(listediffusion);
                    listediffusionHasUserListNewListediffusionHasUser = em.merge(listediffusionHasUserListNewListediffusionHasUser);
                    if (oldListediffusionOfListediffusionHasUserListNewListediffusionHasUser != null && !oldListediffusionOfListediffusionHasUserListNewListediffusionHasUser.equals(listediffusion)) {
                        oldListediffusionOfListediffusionHasUserListNewListediffusionHasUser.getListediffusionHasUserList().remove(listediffusionHasUserListNewListediffusionHasUser);
                        oldListediffusionOfListediffusionHasUserListNewListediffusionHasUser = em.merge(oldListediffusionOfListediffusionHasUserListNewListediffusionHasUser);
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
                Integer id = listediffusion.getId();
                if (findListediffusion(id) == null) {
                    throw new NonexistentEntityException("The listediffusion with id " + id + " no longer exists.");
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
            Listediffusion listediffusion;
            try {
                listediffusion = em.getReference(Listediffusion.class, id);
                listediffusion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listediffusion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ListediffusionHasUser> listediffusionHasUserListOrphanCheck = listediffusion.getListediffusionHasUserList();
            for (ListediffusionHasUser listediffusionHasUserListOrphanCheckListediffusionHasUser : listediffusionHasUserListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Listediffusion (" + listediffusion + ") cannot be destroyed since the ListediffusionHasUser " + listediffusionHasUserListOrphanCheckListediffusionHasUser + " in its listediffusionHasUserList field has a non-nullable listediffusion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Evenement> evenementList = listediffusion.getEvenementList();
            for (Evenement evenementListEvenement : evenementList) {
                evenementListEvenement.getListediffusionList().remove(listediffusion);
                evenementListEvenement = em.merge(evenementListEvenement);
            }
            em.remove(listediffusion);
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

    public List<Listediffusion> findListediffusionEntities() {
        return findListediffusionEntities(true, -1, -1);
    }

    public List<Listediffusion> findListediffusionEntities(int maxResults, int firstResult) {
        return findListediffusionEntities(false, maxResults, firstResult);
    }

    private List<Listediffusion> findListediffusionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Listediffusion.class));
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

    public Listediffusion findListediffusion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Listediffusion.class, id);
        } finally {
            em.close();
        }
    }

    public int getListediffusionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Listediffusion> rt = cq.from(Listediffusion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
