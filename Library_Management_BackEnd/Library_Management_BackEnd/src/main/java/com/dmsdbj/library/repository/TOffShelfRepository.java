package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TOffShelf;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TOffShelfRepository extends AbstractRepository<TOffShelf, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TOffShelfRepository() {
        super(TOffShelf.class);
    }

}
