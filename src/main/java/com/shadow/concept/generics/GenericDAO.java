package com.shadow.concept.generics;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@SuppressWarnings("unchecked")
public abstract class GenericDAO<T extends GenericEntity> {
	@Inject
	private EntityManager entityManager;

	public T getById(Long id) {
		return (T) entityManager.find(getTypeClass(), id);
	}

	public void save(T entity) {
		entityManager.persist(entity);
	}

	public void update(T entity) {
		entityManager.merge(entity);
	}

	public void delete(T entity) {
		entityManager.remove(entity);
	}

	public List<T> findAll() {
		return entityManager.createQuery(("FROM " + getTypeClass().getName())).getResultList();
	}

	private Class<?> getTypeClass() {
		Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return clazz;
	}
}
