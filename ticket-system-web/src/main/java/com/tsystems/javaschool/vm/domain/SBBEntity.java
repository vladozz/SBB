package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class SBBEntity {
    @Id
    @GeneratedValue(generator = "table", strategy=GenerationType.TABLE)
    @TableGenerator(name = "table", allocationSize = 10)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SBBEntity)) {
            return false;
        }

        SBBEntity sbbEntity = (SBBEntity) o;

        if (!id.equals(sbbEntity.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
