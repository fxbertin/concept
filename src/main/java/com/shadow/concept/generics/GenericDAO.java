package com.shadow.concept.generics;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.primefaces.model.SortOrder;

@SuppressWarnings("unchecked")
public abstract class GenericDAO<T extends GenericEntity> implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	protected EntityManager em;

	public T getById(Long id) {
		return (T) em.find(getTypeClass(), id);
	}

	public void save(T entity) {
		em.persist(entity);
	}

	public void update(T entity) {
		em.merge(entity);
	}

	public void delete(T entity) {
		em.remove(entity);
	}

	public List<T> findAll() {
		return em.createQuery(("FROM " + getTypeClass().getName())).getResultList();
	}

	public List<T> paginate(int first, int pageSize, SortOrder multiSortMeta, Map<String, Object> filters) {
		Criteria criteria = em.unwrap(Session.class).createCriteria(getTypeClass());
		List<T> list = criteria.setMaxResults(pageSize).setFirstResult(first).addOrder(Order.asc("id")).list();

		return list;
	}

	public List<T> paginate(int first, int pageSize, SortOrder multiSortMeta, Map<String, Object> filters,
			String fields) {
		Criteria criteria = em.unwrap(Session.class).createCriteria(getTypeClass());

		String[] split = fields.split(",");

		ProjectionList pl = Projections.projectionList();
		for (String c : split) {
			pl.add(Projections.property(c), c);
		}

		List<T> list = criteria.setProjection(pl).setMaxResults(pageSize).setFirstResult(first)
				.addOrder(Order.asc("id")).setResultTransformer(Transformers.aliasToBean(getTypeClass())).list();

		return list;
	}

	public int count() {
		Criteria criteria = em.unwrap(Session.class).createCriteria(getTypeClass());
		Number uniqueResult = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return uniqueResult.intValue();
	}

	private Class<?> getTypeClass() {
		Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return clazz;
	}
}
