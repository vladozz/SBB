package com.tsystems.javaschool.vm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends SBBEntity {
    @Column(name = "title")
    private String title;

    public Role() {
    }

}

