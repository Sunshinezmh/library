package com.dmsdbj.library.repository.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Producer for injectable EntityManager
 *
 */
public class EntityManagerProducer {

    @Produces
    @PersistenceContext(unitName = "DEFAULT_PU")
    private EntityManager em;
}
