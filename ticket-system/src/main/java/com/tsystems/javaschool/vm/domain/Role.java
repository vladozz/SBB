package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role extends SBBEntity {
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "roles")
    private List<Role> users;

    public Role() {
    }

    @ManyToOne(optional = false)
    private Role roles;

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }
}

