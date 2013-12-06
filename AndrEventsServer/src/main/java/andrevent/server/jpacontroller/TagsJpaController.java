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

import andrevent.server.jpacontroller.exceptions.NonexistentEntityException;
import andrevent.server.jpacontroller.exceptions.PreexistingEntityException;
import andrevent.server.jpacontroller.exceptions.RollbackFailureException;
import andrevent.server.model.Recherches;
import andrevent.server.model.Tags;

/**
 *
 * @author Alex
 */
public class TagsJpaController implements Serializable {

    public TagsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tags tags) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tags.getEvenementList() == null) {
            tags.setEvenementList(new ArrayList<Evenement>());
        }
        if (tags.getRecherchesList() == null) {
            tags.setRecherchesList(new ArrayList<Recherches>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Evenement> attachedEvenementList = new ArrayList<Evenement>();
            for (Evenement evenementListEvenementToAttach : tags.getEvenementList()) {
                evenementListEvenementToAttach = em.getReference(evenementListEvenementToAttach.getClass(), evenementListEvenementToAttach.getId());
                attachedEvenementList.add(evenementListEvenementToAttach);
            }
            tags.setEvenementList(attachedEvenementList);
            List<Recherches> attachedRecherchesList = new ArrayList<Recherches>();
            for (Recherches recherchesListRecherchesToAttach : tags.getRecherchesList()) {
                recherchesListRecherchesToAttach = em.getReference(recherchesListRecherchesToAttach.getClass(), recherchesListRecherchesToAttach.getId());
                attachedRecherchesList.add(recherchesListRecherchesToAttach);
            }
            tags.setRecherchesList(attachedRecherchesList);
            em.persist(tags);
            for (Evenement evenementListEvenement : tags.getEvenementList()) {
                evenementListEvenement.getTagsList().add(tags);
                evenementListEvenement = em.merge(evenementListEvenement);
            }
            for (Recherches recherchesListRecherches : tags.getRecherchesList()) {
                recherchesListRecherches.getTagsList().add(tags);
                recherchesListRecherches = em.merge(recherchesListRecherches);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTags(tags.getId()) != null) {
                throw new PreexistingEntityException("Tags " + tags + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tags tags) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tags persistentTags = em.find(Tags.class, tags.getId());
            List<Evenement> evenementListOld = persistentTags.getEvenementList();
            List<Evenement> evenementListNew = tags.getEvenementList();
            List<Recherches> recherchesListOld = persistentTags.getRecherchesList();
            List<Recherches> recherchesListNew = tags.getRecherchesList();
            List<Evenement> attachedEvenementListNew = new ArrayList<Evenement>();
            for (Evenement evenementListNewEvenementToAttach : evenementListNew) {
                evenementListNewEvenementToAttach = em.getReference(evenementListNewEvenementToAttach.getClass(), evenementListNewEvenementToAttach.getId());
                attachedEvenementListNew.add(evenementListNewEvenementToAttach);
            }
            evenementListNew = attachedEvenementListNew;
            tags.setEvenementList(evenementListNew);
            List<Recherches> attachedRecherchesListNew = new ArrayList<Recherches>();
            for (Recherches recherchesListNewRecherchesToAttach : recherchesListNew) {
                recherchesListNewRecherchesToAttach = em.getReference(recherchesListNewRecherchesToAttach.getClass(), recherchesListNewRecherchesToAttach.getId());
                attachedRecherchesListNew.add(recherchesListNewRecherchesToAttach);
            }
            recherchesListNew = attachedRecherchesListNew;
            tags.setRecherchesList(recherchesListNew);
            tags = em.merge(tags);
            for (Evenement evenementListOldEvenement : evenementListOld) {
                if (!evenementListNew.contains(evenementListOldEvenement)) {
                    evenementListOldEvenement.getTagsList().remove(tags);
                    evenementListOldEvenement = em.merge(evenementListOldEvenement);
                }
            }
            for (Evenement evenementListNewEvenement : evenementListNew) {
                if (!evenementListOld.contains(evenementListNewEvenement)) {
                    evenementListNewEvenement.getTagsList().add(tags);
                    evenementListNewEvenement = em.merge(evenementListNewEvenement);
                }
            }
            for (Recherches recherchesListOldRecherches : recherchesListOld) {
                if (!recherchesListNew.contains(recherchesListOldRecherches)) {
                    recherchesListOldRecherches.getTagsList().remove(tags);
                    recherchesListOldRecherches = em.merge(recherchesListOldRecherches);
                }
            }
            for (Recherches recherchesListNewRecherches : recherchesListNew) {
                if (!recherchesListOld.contains(recherchesListNewRecherches)) {
                    recherchesListNewRecherches.getTagsList().add(tags);
                    recherchesListNewRecherches = em.merge(recherchesListNewRecherches);
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
                Integer id = tags.getId();
                if (findTags(id) == null) {
                    throw new NonexistentEntityException("The tags with id " + id + " no longer exists.");
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
            Tags tags;
            try {
                tags = em.getReference(Tags.class, id);
                tags.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tags with id " + id + " no longer exists.", enfe);
            }
            List<Evenement> evenementList = tags.getEvenementList();
            for (Evenement evenementListEvenement : evenementList) {
                evenementListEvenement.getTagsList().remove(tags);
                evenementListEvenement = em.merge(evenementListEvenement);
            }
            List<Recherches> recherchesList = tags.getRecherchesList();
            for (Recherches recherchesListRecherches : recherchesList) {
                recherchesListRecherches.getTagsList().remove(tags);
                recherchesListRecherches = em.merge(recherchesListRecherches);
            }
            em.remove(tags);
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

    public List<Tags> findTagsEntities() {
        return findTagsEntities(true, -1, -1);
    }

    public List<Tags> findTagsEntities(int maxResults, int firstResult) {
        return findTagsEntities(false, maxResults, firstResult);
    }

    private List<Tags> findTagsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tags.class));
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

    public Tags findTags(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tags.class, id);
        } finally {
            em.close();
        }
    }

    public int getTagsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tags> rt = cq.from(Tags.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
