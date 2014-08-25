package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Role;

import javax.persistence.EntityManager;

public class RoleDAO extends CommonDAO<Role> {

    public RoleDAO(EntityManager entityManager) {
        super(entityManager);
    }
}
