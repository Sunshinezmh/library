package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TMessageType;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TMessageTypeRepository extends AbstractRepository<TMessageType, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TMessageTypeRepository() {
        super(TMessageType.class);
    }

}
