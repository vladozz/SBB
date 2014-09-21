package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Role;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;


@Component
public class RoleDAO extends CommonDAO<Role> {

    public RoleDAO() {
        super(Role.class);
    }

    public List<Role> findByTitle(String title) {
        String queryString = "SELECT r FROM Role r WHERE LOWER(r.title) = :title";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("title", title.toLowerCase());
        return query.getResultList();
    }

    public List<Role> findAllServiceRoles() {
        String queryString = "SELECT r FROM Role r WHERE LOWER(r.title) <> 'role_user'";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }
}
