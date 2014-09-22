package com.tsystems.javaschool.vm.validator;

import com.tsystems.javaschool.vm.domain.SBBEntity;
import com.tsystems.javaschool.vm.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Component
public class JavaXValidator<E extends SBBEntity> {

    @Autowired
    private Validator validator;

    public void validate(E entity, List<String> errorList) {
        Set<ConstraintViolation<E>> constraintViolations = validator.validate(entity);
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<E> cv : constraintViolations) {
                errorList.add(cv.getMessage());
            }
        }
    }
}
