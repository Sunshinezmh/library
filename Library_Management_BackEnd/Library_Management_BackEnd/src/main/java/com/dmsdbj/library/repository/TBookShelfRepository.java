package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TBookShelf;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TBookShelfRepository extends AbstractRepository<TBookShelf, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TBookShelfRepository() {
        super(TBookShelf.class);
    }

}
