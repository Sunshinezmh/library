package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TBookAndType;
import com.dmsdbj.library.viewmodel.BookViewModel;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TBookAndTypeRepository extends AbstractRepository<TBookAndType, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TBookAndTypeRepository() {
        super(TBookAndType.class);
    }

    /**
     * 查询所有的图书分类-申明霜-2017-9-28
     * @return
     */
    public List<TBookAndType> findAllbookType(){
        TypedQuery<TBookAndType> query = em.createNamedQuery("TBookAndType.findAllbookType",TBookAndType.class);
        return query.getResultList();

    }

    /**
     * 模糊查询根据图书类别名称查询图书--刘雅雯--2017年10月7日21:27:24
     * @return
     */
    public List<TBookAndType> findBookTypeByName(String bookName){
        TypedQuery<TBookAndType> query =em.createNamedQuery("TBookAndType.findBookTypeByName",TBookAndType.class);
        query.setParameter("BookName","%"+ bookName +"%");
        return  query.getResultList();
    }

    /**
     * 查询一级图书类别--刘雅雯--2017年10月10日19:19:47
     * @return
     */
    public List<TBookAndType> findParentBookType(){
        TypedQuery<TBookAndType> query = em.createNamedQuery("TBookAndType.findParentBookType",TBookAndType.class);
        query.setParameter("pid","0");
        return query.getResultList();
    }

    /**
     * 查询所有二级图书类别--田成荣--2018年5月2日10:39:09
     * @return
     */
    public List<TBookAndType> findShelfBookType(){
        TypedQuery<TBookAndType> query = em.createNamedQuery("TBookAndType.findShelfBookType",TBookAndType.class);
        query.setParameter("pid","0");
        return query.getResultList();
    }

        /**
         * 二级分类和location查询书--田成荣--2018年5月2日15:20:01
         * @param Location
         * @param typeID
         * @param pageNum
         * @param pageSize
         * @return
         */
        public List<BookViewModel> findBookbyShelfandLocation(String Location,String typeID,Integer pageNum,Integer pageSize) {
             StringBuilder jpql = new StringBuilder();
            jpql.append("select ");
            jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel( ");
            jpql.append("c.id,b.isbn,b.name,b.author,b.content,b.summary, ");
            jpql.append("b.picture,b.location,t.remark)");
            jpql.append("FROM TBookBasic b inner join TBook c on b.id=c.basicId  inner join TBookAndType t on c.typeId = t.id where t.id =:typeID  ");
            jpql.append(" and b.location =:location and b.isDelete=0 group by b.isbn ");
            TypedQuery<BookViewModel> query = em.createQuery(jpql.toString(), BookViewModel.class);
            int index = (pageNum -1)* pageSize;
            query.setFirstResult(index);
            query.setMaxResults(pageSize);
            query.setParameter("typeID", typeID);
            query.setParameter("location", Location);
            return query.getResultList();
        }

    /**
     * 根据一级类别id查询图书类别--刘雅雯--2017年10月10日19:47:56
     * @return
     */
    public List<TBookAndType> findBookTypeBypId(String pId){
        TypedQuery<TBookAndType> query = em.createNamedQuery("TBookAndType.findBookTypeBypId",TBookAndType.class);
        query.setParameter("pid",pId);
        return query.getResultList();
    }

    /**
     * 根据id查询pId的图书类别（返回实体）--刘雅雯--2017年10月31日14:11:09
     * @param typeId
     * @return
     */
    public TBookAndType findBookTypepIdById(String typeId){
        TypedQuery<TBookAndType> query = em.createNamedQuery("TBookAndType.findBookTypeBypId",TBookAndType.class);
        query.setParameter("pid",typeId);
        return query.getSingleResult();
    }

//    /**
//     * 根据图书类别名称获取ID--刘雅雯--2017年10月31日11:16:21
//     * @param typeName
//     * @return /
//     *
//    public List<TBookAndType> findBookTypeIdByName(String typeName){
//        TypedQuery<TBookAndType> query =em.createNamedQuery("TBookAndType.findBookTypeIdByName",TBookAndType.class);
//        query.setParameter("typeName",typeName);
//        return query.getResultList();
//    }
    /**
     * 根据图书类别名称获取ID--刘雅雯--2017年10月31日11:16:21
     * @param typeName
     * @return
     */
    public List<TBookAndType> findBookTypeIdByName(String typeName){
        TypedQuery<TBookAndType> query =em.createNamedQuery("TBookAndType.findBookTypeIdByName",TBookAndType.class);
        query.setParameter("typeName",typeName);
        return query.getResultList();
    }



    /**
     * 查找出所有的子类别，包括二级分类以及二级分类以下 -武刚鹏-2017年12月2日11:31:06
     * @return
     */
    public List<TBookAndType> findAllSonType() {
        String jpql = "select t from TBookAndType t where t.pId <> :pId and t.isDelete =0";

        TypedQuery<TBookAndType> query = em.createQuery(jpql,TBookAndType.class);
        query.setParameter("pId","0");
        return query.getResultList();
    }
}
