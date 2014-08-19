package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.SBBEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class CommonDAO<E extends SBBEntity>{
    EntityManagerFactory emf;
    EntityManager em;

    public CommonDAO() {
        emf = Persistence.createEntityManagerFactory("SBBPU");
        em = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void create(E entity) {
        em.persist(entity);
    }

    public E findById(Class<E> entityClass, Integer id) {
        return em.find(entityClass, id);
    }

    public void update(E entity) {
        em.merge(entity);
    }

    public void delete(E entity) {
        em.remove(entity);
    }

    public List<E> findAll(Class<E> entityClass)
    {
        String queryString = "SELECT o FROM " + entityClass.getCanonicalName() + " o";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }
}
