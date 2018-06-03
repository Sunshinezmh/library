package com.dmsdbj.library.repository;

import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.entity.TBook;
import com.dmsdbj.library.entity.TBookBasic;
import com.dmsdbj.library.entity.TCollection;
import com.dmsdbj.library.viewmodel.BookViewModel;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TBookBasicRepository extends AbstractRepository<TBookBasic, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TBookBasicRepository() {
        super(TBookBasic.class);
    }

    /**
     * 根据图书id获得图书isbn-张婷-2017年9月19日16:29:39
     **/
    public TBook findIsbnByBookID(String bookId) {
        TypedQuery<TBook> query = em.createNamedQuery("TBook.findIsbnByBookID", TBook.class);
        query.setParameter("bookId", bookId);
        return (TBook) query.getResultList();
    }


    /**
     * 根据作者/摘要/作品名称/所有者模糊查询-王雪芬-2018年5月13日21:48:23-可借阅-手机端
     *
     * @param MoHu_str
     * @return
     */
    public List<BookViewModel> findcanborrow(String MoHu_str) {
        TypedQuery<BookViewModel> query = null;
        StringBuilder jpqlBuilder = new StringBuilder();
        jpqlBuilder.append("SELECT new com.dmsdbj.library.viewmodel.BookViewModel( ");
        jpqlBuilder.append("b.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator,b.location)");
        jpqlBuilder.append("from TBookBasic b,TBook c, TWarehuse w ,TBookAndType t ");
        jpqlBuilder.append("WHERE b.id = c.basicId ");
        jpqlBuilder.append("and w.isbn = b.isbn ");
        jpqlBuilder.append("and w.location = b.location ");
        jpqlBuilder.append("and w.wareCount > 0 ");
        jpqlBuilder.append("and c.typeId = t.id ");
        jpqlBuilder.append("and b.isDelete = 0 and c.isDelete = 0 and w.isDelete = 0 and t.isDelete = 0 ");

        if (MoHu_str != null && !MoHu_str.trim().isEmpty()) {
            jpqlBuilder.append(" and ");
            jpqlBuilder.append("(b.author like:MoHu_str ");
            jpqlBuilder.append("or b.name like:MoHu_str ");
            jpqlBuilder.append("or b.location like:MoHu_str ");
            jpqlBuilder.append(" or c.owner like:MoHu_str )");
            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
            query.setParameter("MoHu_str", "%" + MoHu_str + "%");
        } else {
            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
        }
        return query.getResultList();
    }

    /**
     * 根据作者/摘要/作品名称/所有者模糊查询-王雪芬-2018年5月13日21:48:23-不可借阅-手机端
     *
     * @param MoHu_str
     * @return
     */
    public List<BookViewModel> findnotborrow(String MoHu_str) {
        TypedQuery<BookViewModel> query = null;
        StringBuilder jpqlBuilder = new StringBuilder();
        jpqlBuilder.append("SELECT new com.dmsdbj.library.viewmodel.BookViewModel( ");
        jpqlBuilder.append("b.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator,b.location)");
        jpqlBuilder.append("from TBookBasic b,TBook c, TWarehuse w ,TBookAndType t ");
        jpqlBuilder.append("WHERE b.id = c.basicId ");
        jpqlBuilder.append("and w.isbn = b.isbn ");
        jpqlBuilder.append("and w.location = b.location ");
        jpqlBuilder.append("and w.wareCount = 0 ");
        jpqlBuilder.append("and c.typeId = t.id ");
        jpqlBuilder.append("and b.isDelete = 0 and c.isDelete = 0 and w.isDelete = 0 and t.isDelete = 0 ");

        if (MoHu_str != null && !MoHu_str.trim().isEmpty()) {
            jpqlBuilder.append(" and ");
            jpqlBuilder.append("(b.author like:MoHu_str ");
            jpqlBuilder.append("or b.name like:MoHu_str ");
            jpqlBuilder.append("or b.location like:MoHu_str ");
            jpqlBuilder.append(" or c.owner like:MoHu_str )");
            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
            query.setParameter("MoHu_str", "%" + MoHu_str + "%");
        } else {
            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
        }
        return query.getResultList();
    }


    /**
     * 根据作者/摘要/作品名称/所有者模糊查询-张婷-2017-11-11 09:46:21
     *
     * @param MoHu_str
     * @return
     */
    public List<BookViewModel> findByConditions(String MoHu_str) {
        TypedQuery<BookViewModel> query = null;
        StringBuilder jpqlBuilder = new StringBuilder();
        jpqlBuilder.append("SELECT new com.dmsdbj.library.viewmodel.BookViewModel( ");
        jpqlBuilder.append("b.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator,b.location)");
        jpqlBuilder.append("from TBookBasic b,TBook c, TWarehuse w ,TBookAndType t ");
        jpqlBuilder.append("WHERE b.id = c.basicId ");
        jpqlBuilder.append("and w.isbn = b.isbn ");
        jpqlBuilder.append("and w.location = b.location ");
        jpqlBuilder.append("and c.typeId = t.id ");
        jpqlBuilder.append("and b.isDelete = 0 and c.isDelete = 0 and w.isDelete = 0 and t.isDelete = 0 ");

        if (MoHu_str != null && !MoHu_str.trim().isEmpty()) {
            jpqlBuilder.append(" and ");
            jpqlBuilder.append("(b.author like:MoHu_str ");
            jpqlBuilder.append("or b.name like:MoHu_str ");
            jpqlBuilder.append("or b.location like:MoHu_str ");
            jpqlBuilder.append(" or c.owner like:MoHu_str )");
            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
            query.setParameter("MoHu_str", "%" + MoHu_str + "%");
        } else {
            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
        }
        return query.getResultList();
    }

    //根据isbn导出
    public List<BookViewModel> findByIsbns(List<String> isbnList) {
        TypedQuery<BookViewModel> query = null;
        StringBuilder jpqlBuilder = new StringBuilder();
        jpqlBuilder.append("SELECT new com.dmsdbj.library.viewmodel.BookViewModel( ");
        jpqlBuilder.append("b.id,c.searchNum,b.isbn,b.name,b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime,t.name,c.owner,b.remark,w.useNum,w.wareCount,b.publishPlace,b.picture,c.origin,b.bid,b.primCost,b.rank,b.sellPrice,c.operator,b.location)");
        jpqlBuilder.append("from TBookBasic b,TBook c, TWarehuse w ,TBookAndType t ");
        jpqlBuilder.append("WHERE b.id = c.basicId ");
        jpqlBuilder.append("and w.isbn = b.isbn ");
        jpqlBuilder.append("and c.typeId = t.id ");
        jpqlBuilder.append("and b.isDelete = 0 and c.isDelete = 0 and w.isDelete = 0 and t.isDelete = 0 ");

        if (isbnList != null && !Objects.equals(isbnList.get(0), "")) {
            jpqlBuilder.append(" and ");
            jpqlBuilder.append("(b.isbn in :isbns)");

            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
            query.setParameter("isbns", isbnList);
        } else query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
        return query.getResultList();
    }

    /**
     * 根据作者名/书名/摘要 模糊查询BookBasic表的信息
     *
     * @param conditions
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<BookViewModel> findByConditionsSum(String conditions, int pageNum, int pageSize) {
        TypedQuery<BookViewModel> query = null;
        StringBuilder jpqlBuilder = new StringBuilder();
        jpqlBuilder.append("SELECT new com.dmsdbj.library.viewmodel.BookViewModel( ");
        jpqlBuilder.append("b.id,b.isbn,b.name,b.author,b.content,b.summary,b.languagea,b.updateTime,b.remark,w.useNum,w.wareCount,b.publishPlace,b.bid,b.primCost,b.rank,b.sellPrice)");
        jpqlBuilder.append("from TBookBasic b, TWarehuse w  ");
        jpqlBuilder.append("where w.isbn = b.isbn ");
        jpqlBuilder.append("and b.isDelete = 0  and w.isDelete = 0  ");

        if (conditions != null && !conditions.trim().isEmpty()) {
            jpqlBuilder.append(" and ");
            jpqlBuilder.append("(b.author like:conditions ");
            jpqlBuilder.append("or b.name like:conditions ");
            jpqlBuilder.append("or b.summary like:conditions) ");
            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
            query.setParameter("conditions", "%" + conditions + "%");
        } else {
            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
        }
        query.setFirstResult((pageNum - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }


    /**
     * 根据作者名/书名/摘要 模糊查询BookBasic表的信息
     *
     * @param conditions
     * @return
     */
    public int findByConditionsSumCount(String conditions) {
        Query query = null;
        StringBuilder jpqlBuilder = new StringBuilder();
        jpqlBuilder.append("SELECT count(b.ID) ");
        jpqlBuilder.append("from t_book_basic b, t_warehuse w  ");
        jpqlBuilder.append("where w.isbn = b.isbn ");
        jpqlBuilder.append("and b.is_delete = 0  and w.is_delete = 0  ");

        if (conditions != null && !conditions.trim().isEmpty()) {
            jpqlBuilder.append(" and ");
            jpqlBuilder.append("(b.author like:conditions ");
            jpqlBuilder.append("or b.name like:conditions ");
            jpqlBuilder.append("or b.summary like:conditions) ");
            query = em.createNativeQuery(jpqlBuilder.toString());
            query.setParameter("conditions", "%" + conditions + "%");
        } else {
            query = em.createNativeQuery(jpqlBuilder.toString());
        }
        Object obj = query.getSingleResult();
        int result = Integer.parseInt(obj.toString());
        return result;
    }


    //图书详情页-田成荣-2017-11-4 10:37:23

    public List<BookViewModel> findBookDetailsByIsbn(String isbn) {
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBookBasic.findBookDetailsByIsbn", BookViewModel.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();

    }

    //图书详情信息传booklist-张婷-2017年11月9日10:29:07
    public List<BookViewModel> findBookDetailsByIsbnList(String collection) {
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.TCollectionBookByIsbnList", BookViewModel.class);
        query.setParameter("collection", collection);
        return query.getResultList();
    }

    public List<TBookBasic> findBookByTypeIdPagination(int pageNum, int pageSize) {
        String sql = "select bb from TBookBasic bb where bb.isDelete=0";
        TypedQuery<TBookBasic> query = em.createQuery(sql, TBookBasic.class);
        query.setMaxResults(pageSize);
        int index = (pageNum - 1) * pageSize;
        query.setFirstResult(index);
        return query.getResultList();

    }


    public List<TBookBasic> getBookName(List<String> bookBasicId) {

        TypedQuery<TBookBasic> query = em.createNamedQuery("TBookBasic.getBook", TBookBasic.class);

        query.setParameter("bookBasicId", bookBasicId);

        return query.getResultList();
    }

    public List<TBookBasic> fandISBN(String bookName) {
        TypedQuery<TBookBasic> query = em.createNamedQuery("TBookBasic.findISBN", TBookBasic.class);
        query.setParameter("bookName", bookName);
        return query.getResultList();
    }

    /**
     * 分页查询图书记录
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<BookViewModel> findBookListByPagination(int pageNum, int pageSize) {
        StringBuilder jpql = new StringBuilder("select ");
        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel(");
        jpql.append("bc.id,bc.name,bc.isbn,bc.picture,bc.location)");
        jpql.append("from TBookBasic bc ");
        jpql.append("where bc.isDelete=0 ");
        TypedQuery<BookViewModel> query = em.createQuery(jpql.toString(), BookViewModel.class);
        query.setMaxResults(pageSize);
        int index = (pageNum - 1) * pageSize;
        query.setFirstResult(index);
        return query.getResultList();
    }

    /**
     * 无条件查询图书记录
     *
     * @return
     */
    public int findBookCount(String condition) {
        String sql = "";
        if (condition == null) {
            sql = "select count(b) from TBookBasic b where b.isDelete=0 ";
        } else {
            sql = "select count(b) from TBookBasic b where b.isDelete=0 and condition ";
        }
        Query query = em.createQuery(sql);
        Object obj = query.getSingleResult();
        int result = Integer.parseInt(obj.toString());
        return result;
    }

    /**
     * 根据类别idlist查询书籍信息-分页查询-武刚鹏-2017年11月23日13:27:42
     *
     * @param typeIds
     * @return
     */
    public List<BookViewModel> findBooksByTypeIds(List<String> typeIds, int pageNum, int pageSize) {

        StringBuilder jpql = new StringBuilder("select ");
        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel(");
        jpql.append("bb.id,bb.name,bb.isbn,bb.picture)");
        jpql.append("from TBook b inner join TBookBasic bb ");
        jpql.append("on b.basicId =bb.id ");
        jpql.append("where b.typeId in :typeIds ");
        jpql.append("group by bb.id ");
        TypedQuery<BookViewModel> query = em.createQuery(jpql.toString(), BookViewModel.class);
        query.setParameter("typeIds", typeIds);
        int index = (pageNum - 1) * pageSize;
        query.setFirstResult(index);
        query.setMaxResults(pageSize);
        return query.getResultList();

    }

    public int findBooksCountByTypeIds(List<String> typeIds) {
        StringBuilder jpql = new StringBuilder("select ");
        jpql.append("bb.id,bb.name,bb.isbn,bb.picture ");
        jpql.append("from TBook b inner join TBookBasic bb ");
        jpql.append("on b.basicId =bb.id ");
        jpql.append("where b.typeId in :typeIds ");
        jpql.append("group by bb.id ");
        Query query = em.createQuery(jpql.toString());
        query.setParameter("typeIds", typeIds);
        int result = query.getResultList().size();
        return result;
    }

    /**
     * 根据ISBN查询一类图书的书名---万达使用---王华伟---2018年3月22日19:15:57
     *
     * @param isbn
     * @return
     */
    public List<TBookBasic> getBookNameByISBN(String isbn) {

        TypedQuery<TBookBasic> query = em.createNamedQuery("getBookNameByISBN", TBookBasic.class);

        query.setParameter("isbn", isbn);

        return query.getResultList();
    }

    /**
     * 查询所有的图书位置分类-张明慧--2018年4月28日14:51:53
     *
     * @param pageNum
     * @param pageSize
     * @return
     */

    public List<BookViewModel> findAllbookLocation(int pageNum, int pageSize) {
        String sql = "select distinct  c.location from TBookBasic c WHERE c.isDelete=0";
        Query query1 = em.createQuery(sql.toString());
        List<BookViewModel> query2 = query1.getResultList();
        return query2;

    }
    public List<BookViewModel> getCheckInfo(String location) {
        String sql = "select new com.dmsdbj.library.viewmodel.BookViewModel(c.id, c.isbn , c.name , c.location ,w.useNum,c.updateTime) from TBookBasic c,TWarehuse w WHERE c.isbn=w.isbn and c.location =:location and w.isDelete=0 and c.isDelete=0";
        TypedQuery<BookViewModel> query1 = em.createQuery(sql.toString(), BookViewModel.class);
      //  Query query1 = em.createQuery(sql.toString(),BookViewModel.class);
        query1.setParameter("location", location);
        List<BookViewModel> query2 = query1.getResultList();
        return query2;

    }
    @Transactional
    public boolean insertChrckList(List<BookViewModel> CheckList ,Date createTime) {

/* 查userID。。。 */

        String tableName = "t_check";
        int isDelete = 0;
        int ischeck = 0;
        int addcount = 0;
        int checkResult = 0;
//        int checkResult = 0;
//        int borrow_status = 1;
//        String remark = "批量插入";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        int is_overdue = 0;
//        DateTime startTime = DateTime.now();
        DateTime startTime = DateTime.now();

        StringBuilder jpql = new StringBuilder("insert into ");
        jpql.append(tableName);
        jpql.append("(");
        jpql.append("ID,isbn,name,location,use_count,add_count,is_check,checkResult,create_time,update_time,is_delete");
        jpql.append(")");

        jpql.append("values");
        for (int i = 0; i < CheckList.size(); i++) {
//            TypedQuery<TUser> queryQ = em.createNamedQuery("TUser.findUserByname", TUser.class);
//            queryQ.setParameter("name", CheckList.get(i).getStudentName());
//            List<TUser> lista = queryQ.getResultList();
//            String userId = lista.get(0).getId();
//            if(lista.get(0).getId()!=""&& lista.get(0).getId()!=null)
//            {
//                tBorrowingList.get(i).setId(lista.get(0).getId());
//            }
//            if(tBorrowingList.get(i).getRealDate()==null){
//                tBorrowingList.get(i).setRealDate(null);
//            }
            jpql.append("(");
            jpql.append("\"" + UuidUtils.base58Uuid() + "\",");
            jpql.append("\"" + CheckList.get(i).getIsbn() + "\",");
            jpql.append("\"" + CheckList.get(i).getBookName() + "\",");
            jpql.append("\"" + CheckList.get(i).getLocation() + "\",");
            jpql.append("\"" + CheckList.get(i).getuseNum() + "\",");
            jpql.append("\"" + addcount + "\",");
            jpql.append("\"" + ischeck + "\",");
            jpql.append("\"" + CheckList.get(i).getuseNum() + "\",");
            jpql.append("\"" + format.format(createTime)+ "\",");
            jpql.append("\"" + format.format(createTime)+ "\",");
            jpql.append("\"" + isDelete + "\"");
//            jpql.append("\"" + is_overdue + "\",");
//            jpql.append("\"" + remark + "\",");
//            jpql.append("\"" + tBorrowingList.get(i).getRenew() + "\",");
//            jpql.append("\"" + "操作人" + "\",");
//            jpql.append("\"" + format.format(tBorrowingList.get(i).getBorrowTime())  + "\",");
//            jpql.append("\"" + borrow_status + "\",");
//            jpql.append("\"" + format.format(tBorrowingList.get(i).getEndDate()) + "\",");
//            jpql.append("\"" + format.format(tBorrowingList.get(i).getRealDate() ) + "\",");
//            jpql.append("\"" + tBorrowingList.get(i).getLocation() + "\"");
            jpql.append(")");
            jpql.append(",");
        }


        String sql = jpql.substring(0, jpql.length() - 1);
        Query query = em.createNativeQuery(sql);
        int result = query.executeUpdate();

        if (result > 0) {
            return true;
        } else {
            return false;
        }

    }




    /**
     * 书架得到所有的图书详情--手机端-张明慧-2018年4月28日14:24:00
     *
     * @return
     */

    public List<BookViewModel> GetbookDetatil(String isbn, String location) {
        String sql = "select c from TBookBasic c WHERE c.isDelete=0 and c.isbn=:isbn and c.location =:location";
        Query query= em.createQuery(sql.toString());
        query.setParameter("isbn", isbn);
        query.setParameter("location", location);
        List<BookViewModel> query2 = query.getResultList();
        return query2;
    }

    /**
     * 查询所有书籍没有从网上抓取信息-王雪芬-2018年5月10日11:39:48
     * @return
     */
    public List<TBookBasic> Getbookisbn() {
        TypedQuery<TBookBasic> query=em.createNamedQuery("Getbookisbn",TBookBasic.class);
        return query.setMaxResults(30).getResultList();

    }
    /**
     * 图书管理页面显示图书名字-王雪芬-电脑端-2018年5月15日10:09:09
     * @return
     */
    public List<TBookBasic> getBookinfoNameRepository() {
        TypedQuery<TBookBasic> query = em.createNamedQuery("getBookinfoName", TBookBasic.class);
        return query.setMaxResults(30).getResultList();
    }
    /**
     * 图书管理页面上传图片-王雪芬-电脑端-2018年5月15日10:09:09
     * @return
     */
    public boolean updateBookpic(BookViewModel bookViewModel) {
        Boolean flag = false;
            Query query=em.createNativeQuery("UPDATE t_book_basic set picture=:picture where isbn =:isbn");
            query.setParameter("picture", bookViewModel.getpicture());
            query.setParameter("isbn", bookViewModel.getIsbn());
            int result=query.executeUpdate();
            if (result>0) {
                flag=true;
            }
            return flag;
        }
    /**
     * 查看该isbn是否有图片-王雪芬-电脑端-2018年5月15日10:09:09
     * @return
     */
    public List<TBookBasic> getBookinfopicture(String isbn) {
        TypedQuery<TBookBasic> query = em.createNamedQuery("getBookinfopicture", TBookBasic.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }
}


