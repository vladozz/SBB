package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.SBBEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

    public EntityTransaction getTransaction() {
        return entityManager.getTransaction();
    }

    public Query createQuery(String queryString) {
        return entityManager.createQuery(queryString);
    }

    public void create(E entity) {
        entityManager.persist(entity);
    }

    public E findById(Long id) {
        return entityManager.find(entityClass, id);
    }

    public void update(E entity) {
        entityManager.merge(entity);

    }

    public void detach(E entity) {
        entityManager.detach(entity);
    }

    public void refresh(E entity) {
        entityManager.refresh(entity);
    }

    public void delete(Long id) {
        E entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    public List<E> findAll() {
        String queryString = "SELECT o FROM " + entityClass.getCanonicalName() + " o";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }


}
