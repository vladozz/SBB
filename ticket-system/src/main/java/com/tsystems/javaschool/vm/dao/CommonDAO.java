package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.SBBEntity;

import javax.persistence.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class CommonDAO<E extends SBBEntity>{
    protected Class<E> entityClass;
    protected EntityManager em;

    public CommonDAO(EntityManager entityManager) {
        em = entityManager;
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        Type type = (genericSuperclass.getActualTypeArguments()[0]);
        if (type instanceof Class) {
            System.out.println("(Class<E>) type = " + (Class<E>) type);;
        } else if (type instanceof ParameterizedType) {
            System.out.println("(Class<E>) ((ParameterizedType)type).getRawType() = " + (Class<E>) ((ParameterizedType) type).getRawType());;
        }

        //this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
        this.entityClass = (Class<E>) type;
    }

    public EntityTransaction getTransaction() {
        return em.getTransaction();
    }

    public Query createQuery(String queryString) {
        return em.createQuery(queryString);
    }

    public void create(E entity) {
        em.persist(entity);
    }

    public E findById(Long id) {
        return em.find(entityClass, id);
    }

    public void update(E entity) {
        em.merge(entity);
    }

    public void delete(E entity) {
        em.remove(entity);
    }

    public List<E> findAll()
    {
        String queryString = "SELECT o FROM " + entityClass.getCanonicalName() + " o";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }
}
