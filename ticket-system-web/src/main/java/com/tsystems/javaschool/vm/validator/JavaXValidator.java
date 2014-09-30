package com.tsystems.javaschool.vm.validator;

import com.tsystems.javaschool.vm.domain.SBBEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
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
