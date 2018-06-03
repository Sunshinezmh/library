package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TNotes;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TNotesRepository extends AbstractRepository<TNotes, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TNotesRepository() {
        super(TNotes.class);
    }

}
