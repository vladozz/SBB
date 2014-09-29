package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.SBBEntity;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public abstract class CommonDAO<E extends SBBEntity>{

    protected Class<E> entityClass;

    @PersistenceContext(unitName = "SBBPU")
    protected EntityManager entityManager;

    protected CommonDAO(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(E entity) {
        entityManager.persist(entity);
    }

    public E findById(Long id) throws EntityNotFoundException {
        E entity = entityManager.find(entityClass, id);
        if (entity == null) {
            throw new EntityNotFoundException(entityClass.getCanonicalName() + " not found; id: " + id);
        }
        detach(entity);
        return entity;
    }

    public E getProxy(Long id) throws EntityNotFoundException {
        E entity = entityManager.getReference(entityClass, id);
        if (entity == null) {
            throw new EntityNotFoundException(entityClass.getCanonicalName() + " not found; id: " + id);
        }
        detach(entity);
        return entity;
    }


    public E update(E entity)  {
        if (entity.getId() > 0) {
            entityManager.merge(entity);
        }
        return entity;
    }

    public void detach(E entity) {
        entityManager.detach(entity);
    }

    public void refresh(E entity) {
        entityManager.refresh(entity);
    }

    public void delete(Long id, Integer version) {
        if (id > 0) {
            E entity = entityManager.find(entityClass, id);
            if (!entity.getVersion().equals(version)) {
                throw new OptimisticLockException("This object was changed by another transaction");
            }
            entity.setDeleted(true);
        }
    }

    public List<E> findAll() {
        String queryString = "SELECT o FROM " + entityClass.getCanonicalName() + " o WHERE o.deleted = false";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }

    public List<E> findAllDeleted() {
        String queryString = "SELECT o FROM " + entityClass.getCanonicalName() + " o WHERE o.deleted = true";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }
}
