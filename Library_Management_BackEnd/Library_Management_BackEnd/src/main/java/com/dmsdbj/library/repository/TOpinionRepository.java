package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TOpinion;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TOpinionRepository extends AbstractRepository<TOpinion, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TOpinionRepository() {
        super(TOpinion.class);
    }

    public List<TOpinion> findOpinionByUserId(String userId) {
        TypedQuery<TOpinion> query = em.createNamedQuery("TOpinion.findOpinionByUserId", TOpinion.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<TOpinion> findOpinionByStatus(String state) {
        TypedQuery<TOpinion> query = em.createNamedQuery("TOpinion.findOpinionByStatus", TOpinion.class);
        query.setParameter("status", state);
        return query.getResultList();
    }

}
