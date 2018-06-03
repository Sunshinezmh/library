package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TAnnouncement;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TAnnouncementRepository extends AbstractRepository<TAnnouncement, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TAnnouncementRepository() {
        super(TAnnouncement.class);
    }

    public List<TAnnouncement> searchingOverview(String type) {
        TypedQuery<TAnnouncement> query=em.createNamedQuery("TAnnouncement.findByType", TAnnouncement.class);
        query.setParameter("type", type);
        return query.getResultList();
    }
//根据当前时间查询所有公告-张婷-2017年9月20日17:18:14
     public List<TAnnouncement> findAnnounceByTime(DateTime date) {
        TypedQuery<TAnnouncement> query=em.createNamedQuery("TAnnouncement.findAnnounceByTime", TAnnouncement.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
}
