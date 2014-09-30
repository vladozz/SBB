package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class PathDAO extends CommonDAO<Path> {

    public PathDAO() {
        super(Path.class);
    }

}


