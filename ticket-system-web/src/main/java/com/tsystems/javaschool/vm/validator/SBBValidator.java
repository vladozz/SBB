package com.tsystems.javaschool.vm.validator;

import com.tsystems.javaschool.vm.domain.SBBEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class SBBValidator<E extends SBBEntity> {

    @Autowired
    protected JavaXValidator<E> validator;

    public abstract List<String> validate(E entity);
}
