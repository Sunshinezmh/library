package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TCollection;
import com.dmsdbj.library.viewmodel.BookViewModel;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

public class TCollectionRepository extends AbstractRepository<TCollection, String> {
 
    @Inject
    private EntityManager em;

     @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TCollectionRepository() {
        super(TCollection.class);
    }

    //TODO 未知功能
    public List<TCollection> getCollectionbookid(String bookid , String userId)
    {
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.TBookid", BookViewModel.class);
        query.setParameter("bookid", bookid);
        List<BookViewModel> list=  query.getResultList();
        if(list!=null && list.size()>0)
        {
            TypedQuery<TCollection> query1 = em.createNamedQuery("TCollection.TCollectionIsbnUserId", TCollection.class);
            query1.setParameter("isbn", list.get(0).getIsbn());
            query1.setParameter("userId", userId);
            return  query1.getResultList();
        }
        else
        {	     return Collections.emptyList();
 }

    }

    /**
     * 通过用户ID和isbn 查询图书是否已经被收藏,收藏返回true,未收藏返回false-王雅静-2017年11月23日17:20:10--添加location条件判断--张明慧--2018年5月1日18:22:37
     *
     * @param isbn 索书号
     * @param userId 用户ID
     * @return 是否已经收藏过此图书的结果收藏返回true,未收藏返回false
     */
    public boolean isCollectioned(String isbn , String userId,String location){
        TypedQuery<TCollection> query1 = em.createNamedQuery("TCollection.TCollectionIsbnUserId", TCollection.class);
        query1.setParameter("isbn",isbn);
        query1.setParameter("userId",userId);
        query1.setParameter("location",location);
        List<TCollection> list =query1.getResultList();
        if (list==null || list.size()==0){
            return false;
        }
        return true;
    }


    /**
     * 通过userId查询用户收藏的图书ISBN 郑晓东 2017年11月9日11点32分
     * @param userId 用户ID
     * @return isbn集合
     */
    public List<String> getIsbnByUserId(String userId){
        TypedQuery<String> query = em.createNamedQuery("TCollection.getCollectionsisbn", String.class);
        query.setParameter("UserId", userId);
        return query.getResultList();
    }

    /**
     * 通过图书收藏ID 查询图书收藏信息（集合） 田成荣
     * 优化：通过图书收藏ID 查询图书收藏信息（单一） 郑晓东
     * @param id 图书收藏ID
     * @return 图书收藏实体
     */
    public TCollection getCollectionInfo (String id)
    {
        TypedQuery<TCollection> query = em.createNamedQuery("TCollection.getCollectionInfo", TCollection.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    /**
     *  批量删除收藏（假删除） 郑晓东 2017年11月9日10点01分
     * @param list 收藏ID字符串集合
     * @return 成功或失败
     */
    @Transactional(rollbackOn = Exception.class)//执行update或delete时需要添加此注解
    public boolean deleteCollections(List<String> list){
        boolean flag =false;
        String sql = "update TCollection c set c.isDelete=1 where c.id in:list";
        Query query = em.createQuery(sql);
        query.setParameter("list",list);
        int result =  query.executeUpdate();
        if(result>0){
            flag = true;
        }
        return flag;
    }

    /**
     * 根据userid查询用户收藏信息 郑晓东 2017年11月12日10点27分
     * @param userId
     * @return
     */
    public List<TCollection> selectCollectionByUserId(String userId){
        TypedQuery<TCollection> query = em.createNamedQuery("TCollection.getCollections", TCollection.class);
        query.setParameter("UserId", userId);
        return query.getResultList();
    }

    /**
     * 根据userid查询用户收藏信息 郑晓东 2017年11月12日10点27分
     * @param userId
     * @return
     */
    public List<BookViewModel> findBookDetailsByIsbnList(String userId){

        TypedQuery<BookViewModel> query=em.createNamedQuery("TCollection.getCollectionBookInfo",BookViewModel.class);
        query.setParameter("userId", userId);
        return query.getResultList();

    }

    /*
       输入书名在书架中搜索图书--张明慧--2018年5月21日21:03:32
       @param MoHu_str 书名
       @param userid   用户名
       @return 成功true，失败false
    */
    public List<BookViewModel>findColByCondition(String MoHu_str,String userId){
         TypedQuery<BookViewModel> query = null;
         StringBuilder jpqlBuilder=new StringBuilder();
         jpqlBuilder.append("select new com.dmsdbj.library.viewmodel.BookViewModel( ");
         jpqlBuilder.append("tb.id,tb.name,tb.isbn,tb.picture,tb.location,tc.id) ");
         jpqlBuilder.append("from TBookBasic tb,TCollection tc ");
         jpqlBuilder.append("where tc.userId=:userId ");
         jpqlBuilder.append("and tc.isbn=tb.isbn ");
         jpqlBuilder.append("and tc.location=tb.location ");
         jpqlBuilder.append("and tc.isDelete = 0 ");
         jpqlBuilder.append("and tb.isDelete = 0 ");
         if (MoHu_str != null && !MoHu_str.trim().isEmpty()){
             jpqlBuilder.append("and ");
             jpqlBuilder.append("(tb.name like:MoHu_str) ");
             query=em.createQuery(jpqlBuilder.toString(),BookViewModel.class);
             query.setParameter("MoHu_str", "%" + MoHu_str + "%");
             query.setParameter("userId", userId);
         }else
         {
             query=em.createQuery(jpqlBuilder.toString(),BookViewModel.class);
             query.setParameter("userId", userId);
         }

        return query.getResultList();
   }

}
