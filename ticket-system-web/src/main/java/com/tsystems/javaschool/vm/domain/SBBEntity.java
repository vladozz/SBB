package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class SBBEntity implements Comparable<SBBEntity> {
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

        return id != null && sbbEntity.id != null && id.equals(sbbEntity.id);

    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return id.hashCode();
    }

    @Override
    public int compareTo(SBBEntity that) {
        return this.id.compareTo(that.id);
    }
}
