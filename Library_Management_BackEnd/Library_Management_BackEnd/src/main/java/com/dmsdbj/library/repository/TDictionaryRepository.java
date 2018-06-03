package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TDictionary;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TDictionaryRepository extends AbstractRepository<TDictionary, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TDictionaryRepository() {
        super(TDictionary.class);
    }

}
