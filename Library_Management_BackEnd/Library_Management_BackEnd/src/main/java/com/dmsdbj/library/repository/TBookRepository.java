package com.dmsdbj.library.repository;

import com.dmsdbj.library.entity.TBook;
import com.dmsdbj.library.entity.TBookAndType;
import com.dmsdbj.library.entity.TBookBasic;
import com.dmsdbj.library.entity.TCheck;
import com.dmsdbj.library.viewmodel.BookViewModel;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


public class TBookRepository extends AbstractRepository<TBook, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TBookRepository() {
        super(TBook.class);
    }
     /**扫描图书抓取图书基本信息-张婷-2017年11月4日10:34:18**/
    public List<TBookBasic> getBookBasicByISBN(String isbn){
	 TypedQuery<TBookBasic> query = em.createQuery("select o from TBookBasic o where o.isbn=:isbn and o.isDelete=0 ", TBookBasic.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }
public List<BookViewModel> getBookBySearchNum (String searchNum){

        TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.getBookBySearchId", BookViewModel.class);
        query.setParameter("searchNum", searchNum);
        return query.getResultList();
    }
public List<TBookBasic> getBookBasicById(String Id){
	 TypedQuery<TBookBasic> query = em.createQuery("select o from TBookBasic o where o.id=:Id and o.isDelete=0 ", TBookBasic.class);
        query.setParameter("Id", Id);
        return query.getResultList();
    }
     /**查book表所有信息**/
     public List<TBook> getAllBook ( ){
        TypedQuery<TBook> query = em.createNamedQuery("TBook.getAllBook", TBook.class);
        
        return query.getResultList();
    }
     
     public List<TBookAndType> getAllBooktype (String typeId ){
        TypedQuery<TBookAndType> query = em.createNamedQuery("TBook.getAllBooktype", TBookAndType.class);
        query.setParameter("typeId", typeId);
        return query.getResultList();
    }
     public List<TBookBasic> getBasicIdByisbn (String ISBN ){
        TypedQuery<TBookBasic> query = em.createNamedQuery("TBookBasic.getBookbasicId", TBookBasic.class);
        query.setParameter("ISBN", ISBN);
        return query.getResultList();
    }
    public List<TBookBasic> getBookbasicLocation (String ISBN,String location ){
        TypedQuery<TBookBasic> query = em.createNamedQuery("TBookBasic.getBookbasicLocation", TBookBasic.class);
        query.setParameter("ISBN", ISBN);
        query.setParameter("location", location);
        return query.getResultList();
    }

    /**删除book表bookbasic表和workhuse表中的书-王雅静-2017年12月3日18:36:32**/
    //删除book表
     public Boolean deleteBookByBasicId(String basicId){
         Boolean flag = false;
         Query query=em.createNativeQuery("UPDATE t_book set is_delete=1 where basic_id =:basicId");
         query.setParameter("basicId", basicId);
         int result=query.executeUpdate();
         if (result>0) {
            flag=true;
         }
         return flag;
     }

    /**根据bookid获得tbook单表信息-张婷-2017年11月4日10:33:47**/
    public List<TBook> getBook (String bookId){
        TypedQuery<TBook> query = em.createNamedQuery("TBook.getBook", TBook.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
    /**根据图书类型id查找图书-田成荣-2017-11-4 10:35:23**/
    public List<BookViewModel> TypeIdgetBook (String bookType){
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.getBookByTypeIdGroupByIsbn", BookViewModel.class);
        query.setParameter("bookType", bookType);
        return query.getResultList();
    }
    //查询盘点表信息。--田成荣--2018年5月15日11:34:11
    public List<BookViewModel>  getCheckInfo (String location){
        StringBuilder jpql = new StringBuilder("select ");
        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.isbn,c.name,c.location,c.usecount,c.addcount,c.checkResult,c.ischeck,c.createTime,c.updateTime,c.endTime)");
        jpql.append("from TCheck c ");
        jpql.append("where c.location =:location and c.isDelete=0 order by c.updateTime desc");
        TypedQuery<BookViewModel> query = em.createQuery(jpql.toString(),BookViewModel.class);
        query.setParameter("location", location);
        return query.getResultList();
    }

    public List<BookViewModel>  getCheckResult (String location){
        StringBuilder jpql = new StringBuilder("select ");
        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.isbn,c.name,c.location,c.usecount,c.addcount,c.checkResult,c.ischeck,c.createTime,c.updateTime,c.endTime)");
        jpql.append("from TCheck c ");
        jpql.append("where c.location =:location and c.checkResult<>0 and c.isDelete=0 order by c.updateTime desc");
        TypedQuery<BookViewModel> query = em.createQuery(jpql.toString(),BookViewModel.class);
        query.setParameter("location", location);
        return query.getResultList();
    }

    //根据isbn查询盘点表信息。--田成荣--2018年5月15日11:34:11
    public List<BookViewModel>  getCheckByisbn (String isbn ,String location){
        StringBuilder jpql = new StringBuilder("select ");
        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.isbn,c.name,c.location,c.usecount,c.addcount,c.checkResult,c.ischeck,c.createTime,c.updateTime,c.endTime)");
        jpql.append("from TCheck c ");
        jpql.append("where c.location =:location and c.isDelete=0 and c.isbn =:isbn and c.checkResult <>0");
        TypedQuery<BookViewModel> query = em.createQuery(jpql.toString(),BookViewModel.class);
        query.setParameter("isbn", isbn);
        query.setParameter("location", location);
        return query.getResultList();
    }

    public List<BookViewModel> getCheckBylocation (String location){
        StringBuilder jpql = new StringBuilder("select ");
        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.isbn,c.name,c.location,c.usecount,c.addcount,c.checkResult,c.ischeck,c.createTime,c.updateTime,c.endTime)");
        jpql.append("from TCheck c ");
        jpql.append("where c.isDelete=0 and c.location =:location");
        TypedQuery<BookViewModel> query = em.createQuery(jpql.toString(),BookViewModel.class);
        query.setParameter("location", location);
        return query.getResultList();
    }
    //批量删除盘点表的信息。--田成荣--2018年5月17日16:05:38
    @Transactional
    public Boolean deleteCheckById (List<String> Id){
//      StringBuilder jpql = new StringBuilder("delete ");
//////        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.isbn,c.name,c.location,c.usecount,c.addcount,c.checkResult,c.createTime,c.updateTime,c.endTime)");
//      jpql.append("from TCheck c ");
//       jpql.append("where c.isDelete=0 and c.id =:id");
        Boolean flag = false;
        for(int i=0;i<Id.size();i++)
        {
//            "DELETE FROM t_check c WHERE c.is_delete=0 and c.ID =:id"
            Query query = em.createNativeQuery("DELETE FROM t_check  WHERE is_delete=0 and ID IN :id");
            query.setParameter("id", Id);
            int result=query.executeUpdate();
            if(result>0)
            {
                flag=true;
            }
            else
            {
                return false;
            }

        }
        return flag;

    }
    //更新盘点记录。。--田成荣--2018年5月17日16:07:18
    @Transactional
    public Boolean updateCheckByisbn (String isbn, Date updateTime) {

        Query query = em.createNativeQuery("UPDATE t_check SET is_check =:heck, update_time =:updateTime, checkResult=checkResult-1 ,add_count = add_count+1 WHERE is_delete=0 and isbn=:isbn");
        query.setParameter("isbn", isbn);
        query.setParameter("updateTime", updateTime);
        query.setParameter("heck", "1");
        int result=query.executeUpdate();
        if(result>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    //更新结束时间。--田成荣--2018年5月17日19:55:23
    @Transactional
    public Boolean updateCheckAll (Date updateTime) {

        Query query = em.createNativeQuery("UPDATE t_check SET update_time =:updateTime WHERE is_delete=0");
        query.setParameter("updateTime", updateTime);
        int result=query.executeUpdate();
        if(result>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    //盘点结束时插入到历史表中。
    @Transactional
    public Boolean updateCheckHistoryAll (String location) {

        Query query = em.createNativeQuery("INSERT INTO t_check_history(id,isbn,name,location,use_count,add_count,is_check,checkResult,create_time,update_time,end_time,is_delete)SELECT id,isbn,name,location,use_count,add_count,is_check,checkResult,create_time,update_time,end_time,is_delete FROM t_check WHERE location =:location");
        query.setParameter("location",location);
        int result=query.executeUpdate();
        if(result>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public List<TBookAndType> getBooktype (){
        TypedQuery<TBookAndType> query = em.createNamedQuery("TBook.getBooktype", TBookAndType.class);
        return query.getResultList();
    }

 /**查询所有图书信息-田成荣-2017-11-4 10:34:35**/
    public List<BookViewModel> findAllBookByPagination(int pageNum,int pageSize){
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.findAllBookByPagination", BookViewModel.class);
        int index = (pageNum-1)*pageSize ;
        query.setFirstResult(index);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<BookViewModel> findAllBook(){
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.findAllBook", BookViewModel.class);
        return query.getResultList();
    }

    public List<TBook> findBook(List<String> bookIdList) {
        TypedQuery<TBook> query = em.createNamedQuery("TBook.findBook",TBook.class);
        query.setParameter("bookIdList",bookIdList);
        List<TBook> tbook = query.getResultList();
        return tbook;
    }
    /**查询所有图书信息-王雅静-2017年12月3日10:46:23**/
    public List<BookViewModel> findAllBookByPaginationSum(int pageNum,int pageSize){
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.findAllBookByPaginationSum", BookViewModel.class);
        int index = (pageNum-1)*pageSize ;
        query.setFirstResult(index);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }
    /**根据isbn获得索书号 getSearchSumByISBN-张婷-2017-11-4 10:37:51**/
    public List<BookViewModel>getSearchSumByISBN(String isbn){
	 TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.getSearchSumByISBN",BookViewModel.class);
        query.setParameter("isbn",isbn);
        return query.getResultList();
    }

/**最热搜索接口-张婷-2017-11-12 21:23:30****/
public List<BookViewModel> getHotBook(){
    TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.getHotBook",BookViewModel.class);
    return query.getResultList();
}

    public List<BookViewModel> getAllBookList() {
        StringBuilder jpql = new StringBuilder("select ");
        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel(tbb.id,tbb.name,tbb.isbn)");
//        jpql.append("tbb.id,tbb.name ");
        jpql.append("from TBookBasic tbb ");
        jpql.append("where tbb.isDelete=0");
        TypedQuery<BookViewModel> query = em.createQuery(jpql.toString(),BookViewModel.class);
        return query.getResultList();
    }
    /**根据isbn查询bookbasic表，添加图书时使用-张婷-2017年11月6日10:12:33**/
        public List<TBookBasic>getBasicInfoByISBN(String isbn){
	 TypedQuery<TBookBasic> query = em.createQuery("Select o from TBookBasic o where o.isbn=:isbn and o.isDelete=0",TBookBasic.class);
        query.setParameter("isbn",isbn);
        return query.getResultList();
    }

    /**根据bookid获得图书信息-张婷-2017-11-9 11:47:55**/
    public List<BookViewModel>getBookInfoBybookId(String bookId){
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.getBookInfoBybookId",BookViewModel.class);
        query.setParameter("bookId",bookId);
        return query.getResultList();
    }

    public List<BookViewModel> getAllBookByTypeId(String typeId ,String location ){
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBook.getAllBookByTypeId",BookViewModel.class);
        query.setParameter("typeId",typeId);
        query.setParameter("location",location);
        return query.getResultList();
    }

    /**
     *   根据子类别ID集合，查询这些子类的书籍
     * @param bookTypeList
     * @return
     */
    public List<BookViewModel> getBookListByParentTypeId(List<String> bookTypeList) {
        StringBuilder  jpql = new StringBuilder("select ");
        jpql.append(" new com.dmsdbj.library.viewmodel.BookViewModel(c.id,c.searchNum,b.isbn,b.name," +
                "b.author,b.content,b.summary,c.totalPage,b.languagea,c.publishTime,b.updateTime," +
                "t.name,c.owner,b.remark,w.useNum,b.publishPlace,b.picture,c.origin,b.bid," +
                "b.primCost,b.rank,b.sellPrice,c.operator) ");
        jpql.append(" from  TBookBasic b,TBook c,TWarehuse w,TBookAndType t ");
        jpql.append(" WHERE ");
        jpql.append("b.id=c.basicId ");
        jpql.append("and w.isbn=b.isbn ");
        jpql.append("and c.typeId=t.id ");
        jpql.append("and b.isDelete=0 ");
        jpql.append("and t.id in :bookTypeIdList ");

        TypedQuery<BookViewModel> query = em.createQuery(jpql.toString(),BookViewModel.class);
        query.setParameter("bookTypeIdList",bookTypeList);
        return query.getResultList();
    }

    /**
     * 手机端书城页面 分页查询书籍信息 -武刚鹏-2017年12月1日15:52:56
     * @param typeId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<BookViewModel> getBookByTypeIdGroupByIsbn(String typeId,Integer pageNum,Integer pageSize) {
StringBuilder jpql = new StringBuilder();
        jpql.append("select ");
        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel( ");
        jpql.append("c.id,b.isbn,b.name,b.author,b.content,b.summary, ");
        jpql.append("b.picture)");
        jpql.append("FROM TBookBasic b inner join TBook c on b.id=c.basicId    inner join TBookAndType t on c.typeId = t.id where t.id=:bookType  ");
        jpql.append(" and b.isDelete=0 group by b.isbn ");
        TypedQuery<BookViewModel> query = em.createQuery(jpql.toString(), BookViewModel.class);


        int index = (pageNum -1)* pageSize;
        query.setFirstResult(index);
        query.setMaxResults(pageSize);
        query.setParameter("bookType", typeId);
        return query.getResultList();
    }

    public int bookCountByTypeId(String typeId){
        int result =0;
        String sql =  "select bb.isbn from TBook b inner join TBookBasic bb on b.basicId = bb.id where b.typeId=:typeId group by bb.isbn";
        Query query = em.createQuery(sql);
        query.setParameter("typeId",typeId);
        result = query.getResultList().size();
        return result;
    }




    //查询BookBasic表的可用记录数
    public int findBookCount() {
            String sql = "select count(b) from TBookBasic b where b.isDelete=0 ";
            Query query = em.createQuery(sql);
            Object obj = query.getSingleResult();
            int result = Integer.parseInt(obj.toString());
            return result;

    }

    //查询Book表的可用记录数
    public int findBookInfoCount(String conditions){
        Query query =null;
        StringBuilder jpql  =  new StringBuilder();
        jpql.append( "select count(c.ID) from t_book c inner join t_book_basic b on b.ID = c.basic_id INNER JOIN t_book_and_type t ON t.ID = c.type_id   ");
        jpql.append("where c.is_delete=0 ");
        if(conditions != null && !conditions.trim().isEmpty()){
            jpql.append("and ");
            jpql.append("(c.search_num like:conditions ");
            jpql.append("or c.origin like:conditions ");
            jpql.append(" or b.name like:conditions ");
            jpql.append("or b.isbn like:conditions)");
             query = em.createNativeQuery(jpql.toString());
            query.setParameter("conditions","%" + conditions + "%");

        }else{
            query = em.createNativeQuery(jpql.toString());
        }
        Object obj = query.getSingleResult();
        int result = Integer.parseInt(obj.toString());
        return result;
    }

    /**
     * 根据索书号/isbn号/书名/所有者 分页查询所有数据
     * @param conditions
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<BookViewModel> findTBookByConditions(String conditions, int pageNum, int pageSize) {
        TypedQuery<BookViewModel> query =null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("select ");
        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel( ");
        jpql.append("c.id,c.searchNum,b.isbn,b.name,b.author,b.location,c.totalPage,c.publishTime,c.owner,c.remark,t.name) ");
        jpql.append("FROM TBookAndType t  inner join TBook c on c.typeId = t.id inner join TBookBasic b on b.id=c.basicId ");
        jpql.append(" where c.isDelete =0 ");

        if(conditions != null && !conditions.isEmpty()){
            jpql.append("and ");
            jpql.append("(c.searchNum like:conditions ");
            jpql.append("or c.origin like:conditions ");
            jpql.append(" or b.name like:conditions ");
            jpql.append("or b.isbn like:conditions)");
             query = em.createQuery(jpql.toString(),BookViewModel.class);
            query.setParameter("conditions","%" + conditions +"%");
        }else{
            query = em.createQuery(jpql.toString(),BookViewModel.class);
        }
        int index = (pageNum-1)*pageSize;
        query.setFirstResult(index);
        query.setMaxResults(pageSize);
        List<BookViewModel> list = query.getResultList();
        return list;

    }

    /**
     * 根据isbn号/书籍位置 分页查询所有数据
     * @param isbn
     * @param location
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<BookViewModel> findTBookByIsbnAndLocation(String isbn,String location, int pageNum, int pageSize) {
        TypedQuery<BookViewModel> query =null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("select ");
        jpql.append("new com.dmsdbj.library.viewmodel.BookViewModel( ");
        jpql.append("c.id,c.searchNum,b.isbn,b.name,b.author,b.location,b.totalPage,b.publishTime,c.owner,b.remark,t.name) ");
        jpql.append("FROM TBookAndType t  inner join TBook c on c.typeId = t.id inner join TBookBasic b on b.id=c.basicId ");
        jpql.append(" where c.isDelete =0 and b.isbn=:isbn and b.location=:location ");
        query = em.createQuery(jpql.toString(),BookViewModel.class);
        query.setParameter("isbn",isbn);
        query.setParameter("location",location);
        int index = (pageNum-1)*pageSize;
        query.setFirstResult(index);
        query.setMaxResults(pageSize);
        List<BookViewModel> list = query.getResultList();
        return list;

    }

}

