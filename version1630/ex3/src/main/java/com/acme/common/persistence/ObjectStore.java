package com.acme.common.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.acme.common.model.AbstractPersistentEntity;

public interface ObjectStore {


	<T extends AbstractPersistentEntity<U>, U> T saveOrUpdate(T entity);
	
	<T extends AbstractPersistentEntity<U>, U> void delete(T entity);

	<T extends AbstractPersistentEntity<U>, U> void delete(Class<T> clazz, U id);

	<T extends AbstractPersistentEntity<U>, U> Optional<T> getById(Class<T> clazz, U id, Function<T, ?>... fetchs);

	<T extends AbstractPersistentEntity<U>, U> Optional<T> findOne(Class<T> clazz, String query, Map<String, Object> args);
	
	<T> Results<T> findMany(Class<T> clazz, String query, Map<String, Object> args, int start, int max);

	Long count(String query, Map<String, Object> args);

	
	
	// shortcut methods
	
	<T extends AbstractPersistentEntity<U>, U> void saveOrUpdateAll(Iterable<T> entities);

	<T extends AbstractPersistentEntity<U>, U> void deleteAll(Iterable<T> entities);
	
	<T> Results<T> findMany(Class<T> clazz, String query, Map<String, Object> args);
	
	<T> List<T> findAll(Class<T> clazz);
}
