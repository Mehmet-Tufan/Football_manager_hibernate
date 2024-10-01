package com.mehmett.repository;

import com.mehmett.entity.BaseEntity;
import com.mehmett.utility.enums.EState;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mehmett.utility.HibernateConnection.*;

public abstract class RepositoryManager<T extends BaseEntity, ID> implements ICRUD<T, ID> {
	
	protected   Class<T> entityClass;
	
	public RepositoryManager(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	public T save(T entity) {
		try {
			setDefaultValues(entity);
			em.persist(entity);

		}
		catch (RuntimeException e) {
			System.err.println("Save metodunda hata..." + e.getMessage());
		}
		return entity;
	}
	
	
	@Override
	public Iterable<T> saveAll(Iterable<T> entities) {
		try {
			for (T entity : entities) {
				setDefaultValues(entity);
				em.persist(entity);
			}

		}
		catch (RuntimeException e) {
			System.err.println("SaveAll metodunda hata..." + e.getMessage());
		}
		return entities;
	}

	@Override
	public Boolean update(T entity) {
		try {
			entity.setUpdateAt(LocalDate.now());
			em.merge(entity);
			return true;

		}
		catch (RuntimeException e) {
			System.err.println("Save metodunda hata..." + e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean updateAll(Iterable<T> entities) {
		try {
			for (T entity : entities) {
				entity.setUpdateAt(LocalDate.now());
				em.merge(entity);
			}
			return true;
		}
		catch (RuntimeException e) {
			System.err.println("SaveAll metodunda hata..." + e.getMessage());
			return false;
		}
	}


	@Override
	public Boolean deleteById(ID id) {
		try {
			T entityToRemove = em.find(entityClass, id);
			if (entityToRemove != null) {
				em.remove(entityToRemove);
				return true;
			}
		}
		catch (RuntimeException e) {
			
			System.err.println("DeleteById metodunda hata..." + e.getMessage());
			return false;
		}
		return false;
	}
	
	@Override
	public Boolean softDeleteByID(ID id) {
		
		try {
			T entity = em.find(entityClass, id);
			if (entity != null) {
				entity.setState(EState.PASSIVE);
				return true;
			}
		}
		catch (RuntimeException e) {
			
			System.err.println("SoftDeleteByID metodunda hata..." + e.getMessage());
			return false;
		}
		return false;
	}
	
	@Override
	public Optional<T> findById(ID id) {
		
		try {
			T entity = em.find(entityClass, id);
			if (entity.getState().equals(EState.PASSIVE)) {
				return Optional.empty();
			}
			return Optional.ofNullable(entity);
		}
		catch (RuntimeException e) {
			System.err.println("FindById metodunda hata..." + e.getMessage());
		}
		return Optional.empty();
	}
	
	@Override
	public Boolean existById(ID id) {
		return findById(id).isPresent();
	}
	
	@Override
	public List<T> findAll() {
		
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(entityClass);
			Root<T> root = cq.from(entityClass);
			cq.select(root).where(cb.equal(root.get("state"), EState.ACTIVE));
			return em.createQuery(cq).getResultList();
		}
		catch (RuntimeException e) {
			System.err.println("FindAll metodunda hata..." + e.getMessage());
		}
		return new ArrayList<>();
		
	}
	
	@Override
	public List<T> findByFieldNameAndValue(String fieldName, Object value) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(entityClass);
			Root<T> root = cq.from(entityClass);
			cq.select(root)
			  .where(cb.and(cb.equal(root.get(fieldName), value), cb.equal(root.get("state"), EState.ACTIVE)));
			return em.createQuery(cq).getResultList();
		}
		catch (RuntimeException e) {
			System.err.println("FindByFieldNameAndValue metodunda hata..." + e.getMessage());
		}
		return new ArrayList<>();
	}
	
	@Override
	public List<T> findByFilledFields(T entity) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(entityClass);
			Root<T> root = cq.from(entityClass);
			List<Predicate> predicates = new ArrayList<>();
			for (Field field : entity.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				try {
					Object value = field.get(entity);
					if (value != null) {
						predicates.add(cb.and(cb.equal(root.get(field.getName()), value), cb.equal(root.get("state"),
						                                                                           EState.ACTIVE)));
					}
				}
				catch (Exception e) {
					System.err.println("findByFilledFields metodunda hata meydana geldi..." + e.getMessage());
				}
			}
			cq.where(predicates.toArray(new Predicate[0]));
			return em.createQuery(cq).getResultList();
		}
		catch (RuntimeException e) {
			System.err.println("findByFilledFields metodunda hata..." + e.getMessage());
		}
		return new ArrayList<>();
	}
	
	private static <T extends BaseEntity> void setDefaultValues(T entity) {
		entity.setCreateAt(LocalDate.now());
		entity.setUpdateAt(LocalDate.now());
	}
}