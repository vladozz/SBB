package com.tsystems.javaschool.vm.dao;

import com.tsystems.javaschool.vm.domain.Board;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class BoardDAO extends CommonDAO<Board> {

    public BoardDAO() {
        super(Board.class);
    }
}
