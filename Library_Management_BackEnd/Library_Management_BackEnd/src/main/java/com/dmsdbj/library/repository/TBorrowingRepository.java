
package com.dmsdbj.library.repository;
import com.dmsdbj.library.app.util.SendMail;
import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.entity.*;
import com.dmsdbj.library.service.StudentService;
import com.dmsdbj.library.viewmodel.Book;
import com.dmsdbj.library.viewmodel.BookViewModel;
import com.dmsdbj.library.viewmodel.hotBookViewModel;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import com.dmsdbj.library.viewmodel.Borrowing;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TBorrowingRepository extends AbstractRepository<TBorrowing, String> {
    @Inject
    private SendMail sendMaill;
    @Inject
    private EntityManager em;
    @Inject
    private StudentService studentService;
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TBorrowingRepository() {
        super(TBorrowing.class);
    }

    /**
     * 王华伟--2017-10-14--根据用户ID查找借阅记录
     * @param userId
     * @return
     */
    public List<TBorrowingModel1> getBorrowingByUserId(String userId) {

        TypedQuery<TBorrowingModel1> query = em.createNamedQuery("TBorrowing.getBorrowingByUserId",TBorrowingModel1.class);
        query.setParameter("userId",userId);
        return query.getResultList();
    }

    /**
     * 建工学院查询借阅记录的方法
     * @param status
     * @param userId
     * @return
     */
    public List<TBorrowingModel1> getBorrowingByStatus(Integer status, String userId) {
        TypedQuery<TBorrowingModel1> query = em.createNamedQuery("TBorrowing.getBorrowingByStatus",TBorrowingModel1.class);
        query.setParameter("status",status);
        query.setParameter("userId",userId);
        return query.getResultList();
    }

    /**
     * 查询所有记录记录--万达使用--王华伟--2018年3月31日14:45:39
     * @param status
     * @param userId
     * @param isbn
     * @return
     */
    public List<BookViewModel> getAllBorrowingByStatusAndCode(Integer status, String userId,String isbn) {
        TypedQuery<BookViewModel> query = em.createNamedQuery("findAllBorrowingStatusByCodeAndStatus",BookViewModel.class);
        query.setParameter("status",status);
        query.setParameter("userId",userId);
        query.setParameter("isbn",isbn);
        return query.getResultList();
    }


    /**
     * 查询所有借阅记录（万达使用）--2018年3月28日15:25:32--修改的是原来的方法
     * @return
     */
    public List<BookViewModel> getAllBorrowing() {
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBorrowing.getAllBorrowing",BookViewModel.class);
        return query.getResultList();
    }
    /**
     * 查询所有未还借阅记录（万达使用）--2018年4月24日10:28:14-王雅静
     * @return
     */
    public List<BookViewModel> getAllBorrowedRecord() {
        TypedQuery<BookViewModel> query = em.createNamedQuery("TBorrowing.getAllBorrowedRecord",BookViewModel.class);
        return query.getResultList();
    }
    /**
     * 根据作品名称模糊查询-王雅静-2018年4月25日15:51:38
     *
     * @param MoHu_str
     * @return
     */
    public List<BookViewModel> findBorrowedByConditions(String MoHu_str) {
        TypedQuery<BookViewModel> query = null;
        StringBuilder jpqlBuilder = new StringBuilder();
        jpqlBuilder.append("SELECT new com.dmsdbj.library.viewmodel.BookViewModel( ");
        jpqlBuilder.append("ts.name,tbb.author,tbb.isbn,tbb.location,tbb.name,tbb" +
                ".content,br.borrowTime,br.endDate,br.realDate,br.isOverdue,br.renew,br.borrowStatus," +
                "tbb.picture) ");
        jpqlBuilder.append("from TUser ts,TBookBasic tbb,TBorrowing br");
        jpqlBuilder.append(" where ts.id = br.userId ");
        jpqlBuilder.append("and br.isbn = tbb.isbn ");
        jpqlBuilder.append("and br.isDelete = 0 ");
        jpqlBuilder.append("and br.borrowStatus <> 2 ");

        if (MoHu_str != null && !MoHu_str.trim().isEmpty()) {
            jpqlBuilder.append(" and ");
            jpqlBuilder.append("(tbb.name like:MoHu_str) ");
            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
            query.setParameter("MoHu_str", "%" + MoHu_str + "%");
        } else {
            query = em.createQuery(jpqlBuilder.toString(), BookViewModel.class);
        }
        return query.getResultList();
    }

    public List<TBorrowing> getAllBorrowingByUserId(String userId) {
        TypedQuery<TBorrowing> query = em.createNamedQuery("TBorrowing.getBorrowingByUserId",TBorrowing.class);
        query.setParameter("userId",userId);
        return query.getResultList();
    }
    public List getBookId (String userId){

        TypedQuery<TBorrowing> queryQ = em.createNamedQuery("TBorrowing.getBookQ", TBorrowing.class);
        queryQ.setParameter("userId", userId);
        List<TBorrowing> lista = queryQ.getResultList();
        return lista;

    }

    public List<BookViewModel> findBorrowingByConditon(String condition ) {

        StringBuilder jpqlBuffer = new StringBuilder();
        jpqlBuffer.append("SELECT new com.dmsdbj.library.viewmodel.BookViewModel( ");
        jpqlBuffer.append("ts.id,br.id,ts.name,tbb.author,tbb.isbn,tbb.location,tbb.name,tbb" +
                ".content,br.borrowTime,br.endDate,br.realDate,br.isOverdue,br.renew,br.borrowStatus," +
                "tbb.picture) ");
        jpqlBuffer.append("from TUser ts,TBookBasic tbb,TBorrowing br");
        jpqlBuffer.append(" where ts.id = br.userId ");
        jpqlBuffer.append("and br.isbn = tbb.isbn ");
        jpqlBuffer.append("and br.isDelete = 0 ");

        TypedQuery<BookViewModel> query = null;
        if(condition != null && !condition.trim().isEmpty() && !"null".equals(condition)){
            jpqlBuffer.append("and ");
            jpqlBuffer.append("(ts.name like:condition ");
            jpqlBuffer.append("or tbb.name like:condition ");
            jpqlBuffer.append("or br.renew like:condition ");
            jpqlBuffer.append("or br.isOverdue like:condition )");
            query = em.createQuery(jpqlBuffer.toString(), BookViewModel.class);
            query.setParameter("condition", "%" + condition + "%");
        }else{
            query = em.createQuery(jpqlBuffer.toString(), BookViewModel.class);
        }
        return query.getResultList();
    }

    @Transactional(rollbackOn = Exception.class)

    public boolean insertBorrowingList(List<TBorrowingModel1> tBorrowingList) {

//查userID。。。

        String tableName = "t_borrowing";
        int isDelete = 0;
        int borrow_status = 1;
        String remark = "批量插入";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int is_overdue = 0;


        StringBuilder jpql = new StringBuilder("insert into ");
        jpql.append(tableName);
        jpql.append("(");
        jpql.append("id,user_id,isbn,book_id,is_delete,is_overdue,remark,renew,operator,borrow_time,borrow_status,end_date,real_date,location");
        jpql.append(")");
        jpql.append("values");

        for (int i = 0; i < tBorrowingList.size(); i++) {
            TypedQuery<TUser> queryQ = em.createNamedQuery("TUser.findUserByname", TUser.class);
            queryQ.setParameter("name", tBorrowingList.get(i).getStudentName());
            List<TUser> lista = queryQ.getResultList();
            String userId = lista.get(0).getId();
            if(lista.get(0).getId()!=""&& lista.get(0).getId()!=null)
            {
                tBorrowingList.get(i).setId(lista.get(0).getId());
            }
            if(tBorrowingList.get(i).getRealDate()==null){
                tBorrowingList.get(i).setRealDate(null);
            }
            jpql.append("(");
            jpql.append("\"" + UuidUtils.base58Uuid() + "\",");
            jpql.append("\"" + tBorrowingList.get(i).getId() + "\",");
            jpql.append("\"" + tBorrowingList.get(i).getIsbn() + "\",");
            jpql.append("\"" + tBorrowingList.get(i).getBookName() + "\",");
            jpql.append("\"" + isDelete + "\",");
            jpql.append("\"" + is_overdue + "\",");
            jpql.append("\"" + remark + "\",");
            jpql.append("\"" + tBorrowingList.get(i).getRenew() + "\",");
            jpql.append("\"" + "操作人" + "\",");
            jpql.append("\"" + format.format(tBorrowingList.get(i).getBorrowTime())  + "\",");
            jpql.append("\"" + borrow_status + "\",");
            jpql.append("\"" + format.format(tBorrowingList.get(i).getEndDate()) + "\",");
            jpql.append("\"" + format.format(tBorrowingList.get(i).getRealDate() ) + "\",");
            jpql.append("\"" + tBorrowingList.get(i).getLocation() + "\"");
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
     * 根据bookID和学号查找借阅记录
     * @param bookId
     * @param code
     * @return
     */
    public List<TBorrowing> getBorrowing(String bookId, String code) {
        TypedQuery<TBorrowing> query = em.createNamedQuery("getBorrowing",TBorrowing.class);
        query.setParameter("code",code);
        query.setParameter("bookId",bookId);
        return query.getResultList();
    }

    //查询5条最热图书 -张婷-2017-11-13 11:43:28
    public List<hotBookViewModel> getHotBook() {
        // TypedQuery<hotBookViewModel> query = em.createNamedQuery("getHotBook", hotBookViewModel.class);
//       Query query = em.createNativeQuery("SELECT b.name,b.isbn as isbn,count(c.ID) as count FROM t_book c LEFT JOIN  t_book_basic b ON B.id = c.basic_Id LEFT  JOIN  t_borrowing o  ON o.book_id = c.ID  and c.is_delete=0 group by isbn order by count desc limit 5");
        Query query = em.createNativeQuery("select b.name,b.isbn as isbn ,b.location from t_book_basic b LEFT JOIN t_borrowing o ON o.isbn=b.isbn and o.location=b.location and b.is_delete=0 GROUP BY o.isbn ORDER BY COUNT(o.isbn) desc LIMIT 5");
        List <hotBookViewModel> list =query.getResultList();
        return  list;
    }
    public List<TBorrowing>GotBook(){
//        Query query = em.createNativeQuery("SELECT b.* FROM t_borrowing b WHERE b.is_delete=0");
        TypedQuery<TBorrowing> query = em.createNamedQuery("GotHotBook",TBorrowing.class);
        System.out.println("judy_____________________________"+query);
        List<TBorrowing> list = query.getResultList();
        return list;
    }

    public List<TBookBasic>GotBookName( List<hotBookViewModel> listNoRe){
        List<TBookBasic> list = new ArrayList<>();
        List<TBookBasic> listNew = new ArrayList<>();
        //根据isbn查找这类书的所有信息
        hotBookViewModel hotBookViewModel = new hotBookViewModel();
        for (int i = 0; i < listNoRe.size(); i++) {
            TypedQuery<TBookBasic> query = em.createNamedQuery("getBookNameByISBN",TBookBasic.class);
            query.setParameter("isbn",listNoRe.get(i).getIsbn());
            list.addAll(query.getResultList()) ;
        }
        if(list.size()>5){
            listNew = list.subList(0,4);
            return listNew;
          }
        else {
            return list;
        }

    }

    /**
     * 根据学生学号查询已借阅数量 郑晓东 2017年11月12日21点55分
     * @param code 学号
     * @return 学生借阅数量
     */
    public Long findStudentAndBorrowCount(String code){
        TypedQuery<Long> query=em.createNamedQuery("getBorrowingCount",Long.class);
        query.setParameter("code",code);
        return query.getSingleResult();
    }


    /**
     * 根据用户id查询借阅表的的书的所属类别
     * @param userId
     * @return
     */
    public List<String> findTypeIdListByUserId(String userId) {

       String sql = "select b.typeId from TBorrowing br INNER JOIN TBook b on b.id = br.bookId where br.userId = :userId group by b.typeId";
       TypedQuery<String> query = em.createQuery(sql,String.class);
       query.setParameter("userId",userId);
       List<String> list = query.getResultList();
    return list;
    }


    /**
     * 根据bookID查找该书是否为再借状态----郭晶晶----2017年11月23日14:09:31
     * @param bookId
     * @return
     */
    public List<TBorrowing> findBorrowingByBookId(String bookId) {
        TypedQuery<TBorrowing> query = em.createNamedQuery("fingBorrwingByBookId",TBorrowing.class);
        query.setParameter("bookId",bookId);
        return query.getResultList();
    }

    /**
     * 王华伟---根据ISBN去t_borrowing表中查询借阅记录（万达这边使用）--2018年3月22日14:42:55
     * @param userID
     * @param isbn
     * @return
     */
    public List<TBorrowing> getBorrowingByISBN(String userID, String isbn,String location) {

        TypedQuery<TBorrowing> query = em.createNamedQuery("findBorrowingByISBNAndCode",TBorrowing.class);
        query.setParameter("isbn",isbn);
        query.setParameter("userID",userID);
        query.setParameter("location",location);
        return query.getResultList();
    }

    //根据status和userID查询ISBN
    public List<TBorrowing> getISBNByStatusAndCode(Integer status, String userId) {

        TypedQuery<TBorrowing> query = em.createNamedQuery("getISBNByStatusAndCode",TBorrowing.class);
        query.setParameter("status",status);
        query.setParameter("userId",userId);
        return query.getResultList();
    }

    //查询记录--万达这边使用--王华伟 --2018年3月31日10:14:14
    public List<TBorrowing> getBorrowingUserNumber(Integer status, String id) {
        TypedQuery<TBorrowing> query = em.createNamedQuery("getUserBorrowingNumber",TBorrowing.class);
        query.setParameter("status",status);
        query.setParameter("code",id);
        return query.getResultList();

    }

    //查询记录  根据isbn和userid查询borrowing是否有这条数据--王华伟--2018年3月31日19:05:08
    public List<TBorrowing> getBorrowingByISBNAndCode(String isbn, String code) {
        TypedQuery<TBorrowing> query = em.createNamedQuery("getBorrowingByISBNAndCode",TBorrowing.class);
        query.setParameter("isbn",isbn);
        query.setParameter("code",code);
        return query.getResultList();
    }

    /**
     * 万达查询借阅记录（状态不等于2）的方法--王华伟--2018年3月22日17:30:48
     * @param status
     * @param userId
     * @return
     */
    public List<BookViewModel> getBorrowingByStatusAndCode(Integer status, String userId,String isbn,String location) {
        TypedQuery<BookViewModel> query = em.createNamedQuery("findBorrowingStatusByCodeAndStatus",BookViewModel.class);
        query.setParameter("status",status);
        query.setParameter("userId",userId);
        query.setParameter("isbn",isbn);
        query.setParameter("location",location);
        return query.getResultList();
    }

    /**
     * 根据用户ID查询出所有状态不等于2的借阅记录--王华伟--2018年4月4日15:16:17
     * @param userId
     * @return
     */
    public List<BookViewModel> ReturnBookRemind(String userId) {
        TypedQuery<BookViewModel> query = em.createNamedQuery("ReturnBookRemind",BookViewModel.class);
        query.setParameter("userId",userId);
        return query.getResultList();
    }
	  /**
     * 查看没有还书的人和书-王雪芬-2018年5月8日16:48:17
     * @return
     */
    public List<TBorrowing> doTime() {
        TypedQuery<TBorrowing> query = em.createNamedQuery("doTime",TBorrowing.class);
        List<TBorrowing> tBorrowings= query.getResultList();
        return tBorrowings;
    }
	/**
     * 查看该用户的isbn是否超期,如何超期则发送邮件-王雪芬-2018年4月29日17:59:23
     * @param isbn
     * @param code
     * @return
     */
    public boolean BorrowingTime(String isbn, String code) {
        //查询所有借阅情况
        TypedQuery<TBorrowing> query = em.createNamedQuery("BorrowingTime",TBorrowing.class);
        query.setParameter("isbn",isbn);
        query.setParameter("code",code);

        //查询是否超过日期
        List<TBorrowing> BorrowingTime= query.getResultList();
        if (BorrowingTime.size()>0){
           int getIsOverdue=  BorrowingTime.get(0).getIsOverdue();
           //3表示超期
           if(getIsOverdue==3) {
               //返回true则表示该书超期，需要发送邮件
               List<TStudent> tStudents = studentService.getStudentBystudentCode(code);
               sendMaill.sendMail(tStudents.get(0).getEmail(),"18333640045@163.com");
           }
           return  true;
        }
        return false;
    }
	 /*-
     *王雪芬-2018年4月29日17:59:23
     * 1查询所有的借书情况
     * 2判断当前借书是否超过预期还书情况
     * 3如果超期则标记已经超期
     * @param isbn
     * @param code
     * @return
     */

    public boolean addAppBorrowingTime(String isbn, String code) {
        //表示查看借书但是没有超期的人员。
        TypedQuery<TBorrowing> query = em.createNamedQuery("addAppBorrowingTime",TBorrowing.class);
        query.setParameter("isbn",isbn);
        query.setParameter("code",code);
        //查询所有借阅情况
        List<TBorrowing> BorrowingTime= query.getResultList();
        //预期还书时间,long类型，用于与当前时间long类型进行比较
        long Enddate=BorrowingTime.get(0).getEndDate().getTime();
        //借书时间
        long borrowtime=BorrowingTime.get(0).getBorrowTime().getTime();
        //当前时间
        Date BorrowNow= new Date();
        //如果预约还书时间超过当前的时间。
        long newlong = BorrowNow.getTime();
        //如果预期时间小于当前时间则说明超出时间
        if (Enddate<=newlong) {
            if(BorrowingTime.get(0).getBorrowStatus()==1){
               //更新数据
                 String sql = "UPDATE TBorrowing set borrow_status=3 where is_overdue=0 and isbn=:isbn and user_id=:codes and borrow_status=1  ";
                 Query query2 = em.createQuery(sql);
                 query2.setParameter("isbn", isbn)
                        .setParameter("codes", code);
                 query2.executeUpdate();
          }
   }
        //查看是否预期时间比结束时间相差1天
         long  between = Enddate-borrowtime;
            //相差1天则返回true，需要发送邮件
            if(between > (24* 3600000)){
                return true;
            }
            else {
                return  false;
            }
  }
	
	
}
