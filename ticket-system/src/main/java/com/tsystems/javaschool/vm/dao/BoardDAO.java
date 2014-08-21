package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Board;

import javax.persistence.EntityManager;

public class BoardDAO extends CommonDAO<Board> {


    public BoardDAO(EntityManager entityManager) {
        super(entityManager);
    }
}
