package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Role;
import org.springframework.stereotype.Component;


@Component
public class RoleDAO extends CommonDAO<Role> {

    public RoleDAO() {
        super(Role.class);
    }
}
