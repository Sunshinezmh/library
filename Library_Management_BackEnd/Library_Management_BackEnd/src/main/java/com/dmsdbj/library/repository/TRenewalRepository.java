package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TRenewal;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TRenewalRepository extends AbstractRepository<TRenewal, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TRenewalRepository() {
        super(TRenewal.class);
    }

}
