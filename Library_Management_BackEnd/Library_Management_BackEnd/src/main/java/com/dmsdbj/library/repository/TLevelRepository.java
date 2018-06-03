package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TLevel;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TLevelRepository extends AbstractRepository<TLevel, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TLevelRepository() {
        super(TLevel.class);
    }

}
