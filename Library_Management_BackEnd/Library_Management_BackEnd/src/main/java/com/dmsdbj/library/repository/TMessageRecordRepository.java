package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TMessageRecord;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TMessageRecordRepository extends AbstractRepository<TMessageRecord, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TMessageRecordRepository() {
        super(TMessageRecord.class);
    }

    /**
     * 1,查看消息:  getMessage-何新生-2017-9-2
     * @param userId,typeId
     * @return
     * @throws Exception
     */
    public List<TMessageRecord> getMessage(String userId){

        TypedQuery<TMessageRecord> query = em.createNamedQuery("TMessageRecord.getMessage", TMessageRecord.class);
        query.setParameter("userId", userId);

        return query.getResultList();
    }
}
