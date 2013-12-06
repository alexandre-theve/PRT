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

import andrevent.server.jpacontroller.exceptions.NonexistentEntityException;
import andrevent.server.jpacontroller.exceptions.PreexistingEntityException;
import andrevent.server.jpacontroller.exceptions.RollbackFailureException;
import andrevent.server.model.Recherches;

/**
 *
 * @author Alex
 */
public class RecherchesJpaController implements Serializable {

    public RecherchesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recherches recherches) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (recherches.getTagsList() == null) {
            recherches.setTagsList(new ArrayList<Tags>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User userid = recherches.getUserid();
            if (userid != null) {
                userid = em.getReference(userid.getClass(), userid.getId());
                recherches.setUserid(userid);
            }
            List<Tags> attachedTagsList = new ArrayList<Tags>();
            for (Tags tagsListTagsToAttach : recherches.getTagsList()) {
                tagsListTagsToAttach = em.getReference(tagsListTagsToAttach.getClass(), tagsListTagsToAttach.getId());
                attachedTagsList.add(tagsListTagsToAttach);
            }
            recherches.setTagsList(attachedTagsList);
            em.persist(recherches);
            if (userid != null) {
                userid.getRecherchesList().add(recherches);
                userid = em.merge(userid);
            }
            for (Tags tagsListTags : recherches.getTagsList()) {
                tagsListTags.getRecherchesList().add(recherches);
                tagsListTags = em.merge(tagsListTags);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRecherches(recherches.getId()) != null) {
                throw new PreexistingEntityException("Recherches " + recherches + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recherches recherches) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Recherches persistentRecherches = em.find(Recherches.class, recherches.getId());
            User useridOld = persistentRecherches.getUserid();
            User useridNew = recherches.getUserid();
            List<Tags> tagsListOld = persistentRecherches.getTagsList();
            List<Tags> tagsListNew = recherches.getTagsList();
            if (useridNew != null) {
                useridNew = em.getReference(useridNew.getClass(), useridNew.getId());
                recherches.setUserid(useridNew);
            }
            List<Tags> attachedTagsListNew = new ArrayList<Tags>();
            for (Tags tagsListNewTagsToAttach : tagsListNew) {
                tagsListNewTagsToAttach = em.getReference(tagsListNewTagsToAttach.getClass(), tagsListNewTagsToAttach.getId());
                attachedTagsListNew.add(tagsListNewTagsToAttach);
            }
            tagsListNew = attachedTagsListNew;
            recherches.setTagsList(tagsListNew);
            recherches = em.merge(recherches);
            if (useridOld != null && !useridOld.equals(useridNew)) {
                useridOld.getRecherchesList().remove(recherches);
                useridOld = em.merge(useridOld);
            }
            if (useridNew != null && !useridNew.equals(useridOld)) {
                useridNew.getRecherchesList().add(recherches);
                useridNew = em.merge(useridNew);
            }
            for (Tags tagsListOldTags : tagsListOld) {
                if (!tagsListNew.contains(tagsListOldTags)) {
                    tagsListOldTags.getRecherchesList().remove(recherches);
                    tagsListOldTags = em.merge(tagsListOldTags);
                }
            }
            for (Tags tagsListNewTags : tagsListNew) {
                if (!tagsListOld.contains(tagsListNewTags)) {
                    tagsListNewTags.getRecherchesList().add(recherches);
                    tagsListNewTags = em.merge(tagsListNewTags);
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
                Integer id = recherches.getId();
                if (findRecherches(id) == null) {
                    throw new NonexistentEntityException("The recherches with id " + id + " no longer exists.");
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
            Recherches recherches;
            try {
                recherches = em.getReference(Recherches.class, id);
                recherches.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recherches with id " + id + " no longer exists.", enfe);
            }
            User userid = recherches.getUserid();
            if (userid != null) {
                userid.getRecherchesList().remove(recherches);
                userid = em.merge(userid);
            }
            List<Tags> tagsList = recherches.getTagsList();
            for (Tags tagsListTags : tagsList) {
                tagsListTags.getRecherchesList().remove(recherches);
                tagsListTags = em.merge(tagsListTags);
            }
            em.remove(recherches);
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

    public List<Recherches> findRecherchesEntities() {
        return findRecherchesEntities(true, -1, -1);
    }

    public List<Recherches> findRecherchesEntities(int maxResults, int firstResult) {
        return findRecherchesEntities(false, maxResults, firstResult);
    }

    private List<Recherches> findRecherchesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recherches.class));
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

    public Recherches findRecherches(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recherches.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecherchesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recherches> rt = cq.from(Recherches.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
