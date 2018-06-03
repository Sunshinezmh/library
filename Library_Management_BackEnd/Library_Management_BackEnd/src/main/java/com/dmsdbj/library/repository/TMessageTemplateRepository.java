package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TMessageTemplate;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TMessageTemplateRepository extends AbstractRepository<TMessageTemplate, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TMessageTemplateRepository() {
        super(TMessageTemplate.class);
    }

}
