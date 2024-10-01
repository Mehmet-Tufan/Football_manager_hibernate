package com.mehmett.service;

import com.mehmett.entity.BaseEntity;
import com.mehmett.repository.ICRUD;

import java.util.List;
import java.util.Optional;

public abstract class ServiceManager<T extends BaseEntity, ID> implements ICRUDService<T, ID> {
	private final ICRUD<T, ID> repository;
	
	public ServiceManager(ICRUD<T, ID> repository) {
		this.repository = repository;
	}
	
	@Override
	public T save(T entity) {
		return repository.save(entity);
	}
	
	@Override
	public Iterable<T> saveAll(Iterable<T> entities) {
		return repository.saveAll(entities);
	}

	@Override
	public Boolean update(T entity) {
		return repository.update(entity);
	}

	@Override
	public Boolean updateAll(Iterable<T> entities) {
		return repository.updateAll(entities);
	}

	@Override
	public Boolean deleteById(ID id) {
		return repository.deleteById(id);
	}
	
	@Override
	public Boolean softDeleteByID(ID id) {
		return repository.softDeleteByID(id);
	}
	
	
	@Override
	public Optional<T> findById(ID id) {
		return repository.findById(id);
	}
	
	@Override
	public Boolean existById(ID id) {
		return repository.existById(id);
	}
	
	@Override
	public List<T> findAll() {
		return repository.findAll();
	}
	
	@Override
	public List<T> findByFieldNameAndValue(String fieldName, Object value) {
		return repository.findByFieldNameAndValue(fieldName, value);
	}
	
	@Override
	public List<T> findByFilledFields(T entity) {
		return repository.findByFilledFields(entity);
	}

}