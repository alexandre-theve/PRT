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

import andrevent.server.model.Tags;

/**
 * 
 * @author Alex
 */
public class TagsJpaController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6103486347972948555L;

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void create(Tags tags) {
		em.persist(tags);
	}

	@Transactional
	public void edit(Tags tags) {
		tags = em.merge(tags);
	}

	@Transactional
	public void destroy(Integer tags) {
		em.remove(tags);
	}

	public List<Tags> findTagsEntities() {
		return findTagsEntities(true, -1, -1);
	}

	public List<Tags> findTagsEntities(int maxResults, int firstResult) {
		return findTagsEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Tags> findTagsEntities(boolean all, int maxResults,
			int firstResult) {
		CriteriaQuery<Tags> cq = em.getCriteriaBuilder().createQuery(Tags.class);
		cq.select(cq.from(Tags.class));
		Query q = em.createQuery(cq);
		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	public Tags findTags(Integer id) {
		return em.find(Tags.class, id);
	}

	public int getTagsCount() {
		CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
		Root<Tags> rt = cq.from(Tags.class);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
