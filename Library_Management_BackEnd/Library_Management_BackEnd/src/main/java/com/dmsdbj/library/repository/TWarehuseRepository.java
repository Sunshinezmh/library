package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TBook;
import com.dmsdbj.library.entity.TWarehuse;
import com.dmsdbj.library.entity.wareHuseNew;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class TWarehuseRepository extends AbstractRepository<TWarehuse, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TWarehuseRepository() {
        super(TWarehuse.class);
    }

    //根据isbn查询入库信息（可借用量）-张婷-2017-11-4 10:40:40
    public List<TWarehuse> getUseCount(String isbn) {

        TypedQuery<TWarehuse> query = em.createNamedQuery("TWarehuse.getUseCount", TWarehuse.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }


    //根据isbn和location查询入库信息（可借用量）-张明慧-2018年4月28日21:40:34
    public List<TWarehuse> getUseCountBylocation(String isbn, String location) {
        TypedQuery<TWarehuse> query = em.createNamedQuery("TWarehuse.getUseCountBylocation", TWarehuse.class);
        query.setParameter("isbn", isbn);
        query.setParameter("location", location);
        return query.getResultList();
    }

    public int finduseNumByIsbn(String searchNum) {
        StringBuilder jpqlBuffer = new StringBuilder();
        jpqlBuffer.append("select new com.dmsdbj.library.entity.TWarehuse( ");
        jpqlBuffer.append("useCount)");
        jpqlBuffer.append("from TWarehuse w, TBook b,TBookBasic tbb ");
        jpqlBuffer.append("where tb.searchNum = :searchNum ");
        jpqlBuffer.append("and w.isbn = tbb.isbn ");
        jpqlBuffer.append("and tbb.id = tb.basicId ");
        Query query = em.createNativeQuery(jpqlBuffer.toString());
        query.setParameter("searchNum", searchNum);
        List list = query.getResultList();

        int result = Integer.parseInt(list.get(0).toString());

        return result;
    }

    //根据索书号获得入库表id和可借用量-张婷-2017-11-6 22:22:14
    public List<wareHuseNew> getCountAndId(String isbn) {

        TypedQuery<wareHuseNew> query = em.createQuery("SELECT new com.dmsdbj.library.entity.wareHuseNew(w.id,w.useNum) FROM TBookBasic b,TBook c, TWarehuse w ,TBookAndType t WHERE b.id=c.basicId and w.isbn=b.isbn and c.typeId=t.id and b.isbn =:isbn and w.isDelete=0 ", wareHuseNew.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }

    //删除warehuse表
    public Boolean deleteWarehuse(String isbn, String location) {
        Boolean flag = false;
        Query query = em.createNativeQuery("UPDATE t_warehuse  set is_delete=1 where isbn=:isbn and location=:location");
        query.setParameter("isbn", isbn);
        query.setParameter("location", location);
        int result = query.executeUpdate();
        if (result > 0) {
            flag = true;
        }
        return flag;
    }
}
