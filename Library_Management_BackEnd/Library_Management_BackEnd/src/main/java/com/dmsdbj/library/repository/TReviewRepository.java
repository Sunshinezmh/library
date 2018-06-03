package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TReview;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TReviewRepository extends AbstractRepository<TReview, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TReviewRepository() {
        super(TReview.class);
    }

}
