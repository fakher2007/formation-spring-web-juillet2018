package com.acme.common.persistence.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.Metamodel;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acme.common.model.AbstractPersistentEntity;
import com.acme.common.persistence.ObjectStore;
import com.acme.common.persistence.Results;

@Transactional(propagation=Propagation.MANDATORY)
public class JpaObjectStoreImpl implements ObjectStore {
		
	@PersistenceContext(type=PersistenceContextType.TRANSACTION)
	private EntityManager em;

	@Override
	public <T extends AbstractPersistentEntity<U>, U> T saveOrUpdate(T entity) {
		// TODO Auto-generated method stub

		// si entity n'a pas d'id : persist
		if (entity.getId() == null) {
			this.em.persist(entity);
		} else /* l'entity a un id (et donc a son équivalent en base) */ {
			// si entity est détaché de l'entityManager : appeler merge
			if(!this.em.contains(entity)){
				entity = this.em.merge(entity);
			}
			// si entity est attaché à l'entityManager : ne rien faire car les changements d'état seront propagés vers la base à la fin de la transaction (juste avant le commit)
			else{
				
			}
		}

		return entity;
	}

	// Person p = new Person(1);
	// em.remove(p);
	@Override
	public <T extends AbstractPersistentEntity<U>, U> void delete(T entity) {
		if(!em.contains(entity)) {
			delete(entity.getClass(), entity.getId());
		}
		else {
			this.em.remove(entity);
		}
		// si entity est attaché : suppression.
		// sinon : appel de la méthode delete ci dessous avec passage en paramètre du type de l'entité et de son id
	}

	@Override
	public <T extends AbstractPersistentEntity<U>, U> void delete(Class<T> clazz, U id) {
		T entity = this.em.find(clazz, id);
		if(entity!=null) {
			delete(entity);
		}
		// récupération de l'entité dont le type est clazz et l'id est id;
		// si le résultat est non nul : suppression
		// sinon : logger.warn ou levée d'une IllegalArgumentException
	}

	@Override
	public <T extends AbstractPersistentEntity<U>, U> Optional<T> getById(Class<T> clazz, U id, Function<T, ?>... fetchs) {
		// récupération de l'entité dont le type est clazz et l'id est id;
		T entity = this.em.find(clazz, id);
		
		
		// fabrication et renvoie d'un Optional d'après l'entité récupérée (peut être nulle).

		return entity == null ? Optional.empty() : Optional.of(entity);
		// ou bien return Optional.ofNullable(entity);
	}

	@Override
	public <T extends AbstractPersistentEntity<U>, U> Optional<T> findOne(Class<T> clazz, String query, Map<String, Object> args) {
		TypedQuery<T> jpaQuery = this.em.createQuery(query, clazz);

		// 2. bind des paramètres nommés s'il y en a
		if (args != null) {
			args.forEach((x, y) -> jpaQuery.setParameter(x, y));
		}
		List<T> results = jpaQuery.setFirstResult(0).setMaxResults(2).getResultList();
		return results.size() == 1 ? Optional.of(results.get(0)) : Optional.empty();
	}

	// findMany(Employee.class, "select e from Employee e where e.login like :x or e.login like :y", new QueryParams().add("x", "a%").add("y", "z%"), 0, 50);
	@Override
	public <T> Results<T> findMany(Class<T> clazz, String query, Map<String, Object> args, int start, int max) {

		boolean doPagination = (start >= 0 && max >= 1);
		// 1. création d'une TypedQuery
		TypedQuery<T> jpaQuery = this.em.createQuery(query, clazz);

		// 2. bind des paramètres nommés s'il y en a
		if (args != null) {
			args.forEach((x, y) -> jpaQuery.setParameter(x, y));
		}
		// List<T> items = doPagination ?
		// jpaQuery.setFirstResult(start).setMaxResults(max).getResultList() :
		// jpaQuery.getResultList();

		if (!doPagination) {
			// 3. récupération des résultats
			List<T> items = jpaQuery.getResultList();

			// 4. renvoie d'un Results
			return new Results<>(items, items.size());
		} else {
			// 3. pagination si doPagination==true
			jpaQuery.setFirstResult(start).setMaxResults(max);
			// 4. récupération des résultats
			List<T> items = jpaQuery.getResultList();

			// 5. requête de comptage, bind des paramètres s'il y en a,
			// récupération du comptage (getSingleResult sur la requête de
			// comptage).
			String countQuery = "select count(*) from " + query.split(" from ")[1].replace(" join fetch ", " join ");
			if (countQuery.contains("order by ")) {
				countQuery = countQuery.substring(0, countQuery.indexOf(" order by "));
			}
			TypedQuery<Long> jpaCountQuery = this.em.createQuery(countQuery, Long.class);

			if (args != null) {
				args.entrySet().forEach(x -> jpaCountQuery.setParameter(x.getKey(), x.getValue()));
			}
			long n = jpaCountQuery.getSingleResult();

			// 6. renvoie d'un Results
			return new Results<>(items, n);
		}

	}

	@Override
	public Long count(String query, Map<String, Object> args) {
		TypedQuery<Long> jpaQuery = this.em.createQuery(query, Long.class);
		if (args != null) {
			args.forEach((x, y) -> jpaQuery.setParameter(x, y));
			//ou bien : args.forEach(jpaQuery::setParameter);
		}
		return jpaQuery.getSingleResult();
	}

	
	// shortcuts methods
	@Override
	public <T extends AbstractPersistentEntity<U>, U> void saveOrUpdateAll(Iterable<T> entities) {
		entities.forEach(this::saveOrUpdate);
		
	}

	@Override
	public <T extends AbstractPersistentEntity<U>, U> void deleteAll(Iterable<T> entities) {
		entities.forEach(this::delete);
		
	}

	@Override
	public <T> Results<T> findMany(Class<T> clazz, String query, Map<String, Object> args) {
		return findMany(clazz, query, args, -1,-1);
	}

	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		return findMany(clazz, "select e from " + clazz.getName() + " e", null).getItems();	
	}


	private EntityManagerFactory emf;

	public void loadCache(){
		EntityManager specialEm = emf.createEntityManager();
		Metamodel metamodel = emf.getMetamodel();
		metamodel.getEntities().stream()
				.map(e -> e.getJavaType())
				.filter(entityClass -> entityClass.isAnnotationPresent(Cacheable.class))
				.forEach(entityClass -> {
					String q = "select e from "+entityClass.getName()+" e";
					em.createQuery(q).getResultList();
				});
		specialEm.close();
	}
}
