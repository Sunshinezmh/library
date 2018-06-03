package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TReadingroom;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TReadingroomRepository extends AbstractRepository<TReadingroom, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TReadingroomRepository() {
        super(TReadingroom.class);
    }

}
