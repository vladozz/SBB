package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role extends SBBEntity {
    @Column(name = "title")
    private String title;

    public Role() {
    }

}

