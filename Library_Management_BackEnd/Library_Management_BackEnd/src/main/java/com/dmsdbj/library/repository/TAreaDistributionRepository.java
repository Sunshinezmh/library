package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TAreaDistribution;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TAreaDistributionRepository extends AbstractRepository<TAreaDistribution, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TAreaDistributionRepository() {
        super(TAreaDistribution.class);
    }

}
