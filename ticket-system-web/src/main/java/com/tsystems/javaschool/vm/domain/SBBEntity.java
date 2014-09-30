package com.tsystems.javaschool.vm.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class SBBEntity implements Comparable {
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

    @Version
    @Column(name = "version")
    protected Integer version = 1;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    @Column(name = "deleted")
    protected Boolean deleted = false;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
    public int compareTo(Object o) {
        SBBEntity that = ((SBBEntity) o);
        return this.id.compareTo(that.id);
    }
}
