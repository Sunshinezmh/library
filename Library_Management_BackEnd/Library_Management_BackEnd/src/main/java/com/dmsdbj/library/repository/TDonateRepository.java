
package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TDonate;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TDonateRepository extends AbstractRepository<TDonate, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TDonateRepository() {
        super(TDonate.class);
    }


    /**
     * 1,模糊查询所有捐赠书目(分页):  getDonate-何新生-2017-9-2
     *
     * @param userId
     * @return
     */
    public List<TDonate> getDonate(String userId) {
        TypedQuery<TDonate> query = em.createNamedQuery("TDonate.getDonate", TDonate.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    /**
     * 3,根据状态查询捐赠的书:  findByStatus-何新生-2017-9-2
     *
     * @param status
     * @return
     */
    public List<TDonate> findByStatus(String status) {
        TypedQuery<TDonate> query = em.createNamedQuery("TDonate.findByStatus", TDonate.class);
        query.setParameter("status",status);
        return query.getResultList();
    }
}
