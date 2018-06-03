package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TSetting;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


/**
 * @author lyn
 */
public class TSettingRepository extends AbstractRepository<TSetting, Integer> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TSettingRepository() {
        super(TSetting.class);
    }


    public List<TSetting> getSetting() {
        return null;
    }

    public List<TSetting> getAlltSettings() {
        TypedQuery<TSetting> query = em.createNamedQuery("getAlltSettings",TSetting.class);
        List<TSetting> list = query.getResultList();
        return list;
    }
}
