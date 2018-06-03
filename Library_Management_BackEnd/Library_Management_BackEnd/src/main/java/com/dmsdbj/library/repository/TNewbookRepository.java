package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TNewbook;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TNewbookRepository extends AbstractRepository<TNewbook, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TNewbookRepository() {
        super(TNewbook.class);
    }
    





}
