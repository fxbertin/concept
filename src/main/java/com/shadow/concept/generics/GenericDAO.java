package com.shadow.concept.generics;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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

	public int count() {
		Criteria criteria = em.unwrap(Session.class).createCriteria(getTypeClass());
		Number uniqueResult = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return uniqueResult.intValue();
	}

	public List<T> paginate(int first, int pageSize, SortOrder sort, Map<String, Object> filters,
			String fields) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
		Root<T> c = (Root<T>) q.from(getTypeClass());

		q.select(getCampos(cb, c, fields));

		List<Object[]> resultList = em.createQuery(q).setMaxResults(pageSize).setFirstResult(first).getResultList();

		return (List<T>) getNormalizedResultList(fields, resultList, getTypeClass());
	}

	private Selection getCampos(CriteriaBuilder cb, Root<T> r, String listaCampos) {
		List<Selection> cbc = new ArrayList<Selection>();

		String[] rootFields = listaCampos.split(",");

		for (String rf : rootFields) {
			if (rf.contains(".")) {
				cbc.add(montaJoin(rf, r));
			} else {
				cbc.add(r.get(rf));
			}
		}

		return cb.tuple(cbc.toArray(new Selection[cbc.size()]));
	}

	private Selection<?> montaJoin(String campoJoin, Root<T> r) {

		String[] split = campoJoin.split("\\.");
		Path<Object> p = r.get(split[0]);

		if (split.length > 1) {
			for (int i = 1; i < split.length - 1; i++) {
				p = p.get(split[i]);
			}
		}
		return p.get(split[split.length - 1]);

	}

	private Class<?> getTypeClass() {
		Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return clazz;
	}

	public static <T> List<T> getNormalizedResultList(String attributes, List resultList, Class<T> clazz) {
		if (attributes != null && attributes.split(",").length > 0) {
			List result = new ArrayList();
			String[] fields = attributes.split(",");
			for (Object object : resultList) {
				try {
					Object entity = clazz.newInstance();
					for (int i = 0; i < fields.length; i++) {
						String property = fields[i].trim().replaceAll("/s", "");
						initializeCascade(property, entity);
						if (object instanceof Object[]) {
							PropertyUtils.setProperty(entity, property, ((Object[]) object)[i]);
						} else {
							PropertyUtils.setProperty(entity, property, object);
						}
					}
					result.add(entity);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
			return result;
		}
		return resultList;
	}

	public static void initializeCascade(String property, Object bean) {
		int index = property.indexOf(".");
		if (index > -1) {
			try {
				String field = property.substring(0, property.indexOf("."));
				Object propertyToInitialize = PropertyUtils.getProperty(bean, field);
				if (propertyToInitialize == null) {
					propertyToInitialize = PropertyUtils.getPropertyDescriptor(bean, field).getPropertyType()
							.newInstance();
					PropertyUtils.setProperty(bean, field, propertyToInitialize);
				}
				String afterField = property.substring(index + 1, property.length());
				if (afterField != null && afterField.indexOf(".") > -1) {
					initializeCascade(afterField, propertyToInitialize);
				}
			} catch (Exception ex) {
				// logger.log(Level.SEVERE, null, ex);
				ex.printStackTrace();
			}
		}
	}
}
