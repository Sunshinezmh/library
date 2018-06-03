package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TFollow;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TFollowRepository extends AbstractRepository<TFollow, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TFollowRepository() {
        super(TFollow.class);
    }

}
