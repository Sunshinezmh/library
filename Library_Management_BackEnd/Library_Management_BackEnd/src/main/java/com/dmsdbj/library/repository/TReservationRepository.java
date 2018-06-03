
package com.dmsdbj.library.repository;

import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.entity.TBookBasic;
import com.dmsdbj.library.entity.TReservation;
import com.dmsdbj.library.viewmodel.Reservation;

import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TReservationRepository extends AbstractRepository<TReservation, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TReservationRepository() {
        super(TReservation.class);
    }


    //region 根据isbn和location查询该用户借阅的书籍被谁预约（手机端用户信息界面）-杜雨-2018年5月9日15:20:34

    /**
     * 根据isbn和location查询该用户借阅的书籍被谁预约
     * @param isbn
     * @param location
     * @return
     */
    public List<Reservation> getBookReservationInfobyisbnAndlocation(String isbn,String location) {
        StringBuilder sql = new StringBuilder();
        sql.append("select new com.dmsdbj.library.viewmodel.Reservation( ");
        sql.append("r.userId,r.isbn,s.name,s.classesName,s.professionName,tbc.name,r.createTime,tbc.location,tbc.picture)");
        sql.append("from TReservation r, TStudent s,TBookBasic tbc ");
        sql.append("where r.isDelete = 0 ");
        sql.append("and r.isbn =tbc.isbn ");
        sql.append("and r.location=tbc.location ");
        sql.append("and r.userId = s.id ");
        sql.append("and r.location= :location ");
        sql.append("and r.isbn = :isbn ");
        TypedQuery<Reservation> query = em.createQuery(sql.toString(), Reservation.class);

        query.setParameter("location", location);
        query.setParameter("isbn",isbn);
        return query.getResultList();
    }
    //endregion


    //region 根据用户id查询用户预约的书籍（手机端用户信息界面）-武刚鹏-2017年10月27日15:38:07

    /**
     * 根据用户id查询用户预约的书籍（手机端用户信息界面）-武刚鹏-2017年10月27日15:38:07
     *
     * @param userId
     * @return
     */
    public List<Reservation> findReserveByUserId(String userId) {
        StringBuilder sql = new StringBuilder("select ");
        //使用 Reservation实体的构造函数
        sql.append("new com.dmsdbj.library.viewmodel.Reservation (");
        sql.append(" r.id,r.userId,r.isbn,tb.name ,tb.author ,tw.useNum,tb.picture,r.location )");
        sql.append("from TReservation r left join TBookBasic tb ");
        sql.append("on tb.isbn = r.isbn ");
        sql.append("left join TWarehuse tw ");
        sql.append("on tw.isbn=r.isbn ");
        sql.append("where r.userId =:id and r.isDelete = 0 and r.status = 0 ");
        sql.append(" ) ");
        TypedQuery<Reservation> query = em.createQuery(sql.toString(), Reservation.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }
    //endregion

    //region 更新预约记录 （PC端预约管理） -武刚鹏-2017年10月28日15:25:31
    /**
     * 更新预约记录 （PC端预约管理） -武刚鹏-2017年10月28日15:25:31
     *
     * @param tReservation
     * @return
     */
    public boolean updateReservation(TReservation tReservation) {
        boolean flag = false;
        String sql = "update t_reservation set user_id =:userId,update_time=:updateTime,isbn =:isbn where ID=:id  ";
        Query query = em.createNativeQuery(sql);
        query.setParameter("userId", tReservation.getUserId())
                .setParameter("updateTime", tReservation.getUpdateTime())
                .setParameter("isbn", tReservation.getIsbn())
                .setParameter("id", tReservation.getId());
        int result = query.executeUpdate();
        if (result > 0) {
            flag = true;
        }
        return flag;

    }
    //endregion

    //region 根据模糊条件分页查询 预约记录（预约管理） -武刚鹏-2017年10月27日15:36:14

    /**
     * 根据模糊条件分页查询 预约记录（预约管理） -武刚鹏-2017年10月27日15:36:14
     *
     * @param condition
     * @return
     */
    public List<Reservation> findReservationByPagenationCondition(String condition) {

        //使用 
        StringBuilder jpqlBuffer = new StringBuilder();
        jpqlBuffer.append("select new com.dmsdbj.library.viewmodel.Reservation( ");
        jpqlBuffer.append("r.id,r.userId,r.isbn,s.code,s.name,s.classesName,s.professionName,tbc.name,r.createTime,tbc.location)");
        jpqlBuffer.append("from TReservation r, TStudent s,TBookBasic tbc ");
        jpqlBuffer.append("where r.isDelete = 0 ");
        jpqlBuffer.append("and r.isbn = tbc.isbn ");
        jpqlBuffer.append("and r.userId =s.alluserId ");
//        jpqlBuffer.append("and r.userId = s.code ");

        TypedQuery<Reservation> query = null;
        if(condition != null && !condition.trim().isEmpty() && !"null".equals(condition)){
            jpqlBuffer.append("and ");
            jpqlBuffer.append("(s.code like:condition ");
            jpqlBuffer.append("or s.name like:condition ");
            jpqlBuffer.append(" or s.professionName like:condition ");
            jpqlBuffer.append("or s.classesName like:condition ");
            jpqlBuffer.append(" or tbc.name like:condition ");
            jpqlBuffer.append(" or tbc.location like:condition ");
            jpqlBuffer.append("or tbc.isbn like:condition )");
            query = em.createQuery(jpqlBuffer.toString(), Reservation.class);
            query.setParameter("condition","%"+condition+"%");
        }else{
            query = em.createQuery(jpqlBuffer.toString(), Reservation.class);
        }
        return query.getResultList();
    }

    //endregion

    //region 批量删除预约记录 -武刚鹏-2017年10月27日17:45:22

    /**
     * 批量删除预约记录 -武刚鹏-2017年10月27日17:45:22
     *
     * @param reservationIds
     */
    @Transactional(rollbackOn = Exception.class)
    public Boolean deleteReservations(String reservationIds) {
        Boolean flag = false;
        String[] ids = reservationIds.split(",");
        StringBuilder str = new StringBuilder("\""+ids[0]+"\"");
        for(int i = 1;i<ids.length;i++){
            str.append(",\""+ids[i] +"\"");
        }
        Query query = em.createNativeQuery("UPDATE t_reservation r set is_delete = 1 where r.ID in ("+str.toString()+")");
     int result =  query.executeUpdate();
        if (result > 0) {
            flag = true;
        }
        return flag;
    }
    //endregion

    //region 取消预约(手机端) -王雅静-2017年11月23日09:34:20

    /**
     * 取消预约(手机端) -王雅静-2017年11月23日09:34:20
     *
     * @param reservationId
     * @param userId
     */
    @Transactional(rollbackOn = Exception.class)
    public Boolean cancelReservations(String reservationId,String userId) {
        Boolean flag = false;
        String sql = "update t_reservation r set status = 1 where r.ID =:reservationId and r.user_id=:userId  ";
        Query query = em.createNativeQuery(sql);
        query.setParameter("reservationId",reservationId);
        query.setParameter("userId",userId);
        int result = query.executeUpdate();
        if (result > 0) {
            flag = true;
        }else
        {
            flag = false;
        }
        return flag;
    }
    //endregion



    //region 根据userId查询可借书籍的数量 -武刚鹏-2017年10月30日11:51:18
    /**
     * 根据userId查询可借书籍的数量 -武刚鹏-2017年10月30日11:51:18
     * @param userId
     * @return
     */
    public Integer getBorrowReservationCount(String userId) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append("count(r.ID) count FROM t_reservation r INNER join t_warehuse tw ");
        sql.append("on tw.isbn = r.isbn ");
        sql.append("where tw.use_count >0 ");
        sql.append("and r.is_delete = 0 ");
        sql.append("and r.is_delete = 0 ");
        sql.append("and r.user_id=:userId ");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("userId", userId);
        List list = query.getResultList();

        Integer result =Integer.parseInt(list.get(0).toString());

        return result;


    }
    //endregion

    /**
     * 更新
     * @param reservation
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean updateReservationIsbn(Reservation reservation){
        boolean flage = false;
        String sql = "update TReservation  r set r.isbn =:isbn where r.id =:id";
        Query query = em.createQuery(sql);
        query.setParameter("isbn",reservation.getIsbn());
        query.setParameter("id",reservation.getId());
        int result =  query.executeUpdate();
        if(result>0){
            flage = true;
        }
        return flage;
    }

    /**
     * 根据isbn查询TBookBasic信息（）-武刚鹏-2017年11月1日12:06:35
     * @param isbn
     * @return
     */
    public TBookBasic getTBookBasicByisbn(String isbn){
        TypedQuery<TBookBasic> query = em.createQuery("select tb from TBookBasic tb where tb.isbn=:isbn ",TBookBasic.class);
        query.setParameter("isbn",isbn);
        TBookBasic tBookBasic = query.getResultList().get(0);
        return tBookBasic;
    }

    /**
     * 查询图书信息 郑晓东 2017年11月10日20点45分
     * @param isbnList 图书ISBN集合
     * @return 图书ISBN集合
     */
    public List<TBookBasic> getTBookBasicByisbn(List<String> isbnList){
        TypedQuery<TBookBasic> query = em.createQuery("select tb from TBookBasic tb where tb.isbn in:list",TBookBasic.class);
        query.setParameter("list",isbnList);
        List<TBookBasic> list = query.getResultList();
        return list;
    }

  @Transactional(rollbackOn = Exception.class)
    public boolean insertReservationList(List<Reservation> reservationList) {
        String tableName = "t_reservation";
        int isDelete = 0;
        int isAnnounce = 0;
        String remark  = "批量插入";
        int status = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());

        StringBuilder jpql = new StringBuilder("insert into ");
        jpql.append(tableName);
        jpql.append("(");
        jpql.append("ID,isbn,is_delete,Is_announce,remark,status,user_id,operator,create_time,location");
        jpql.append(")");
        jpql.append("values");

        for(int i=0;i<reservationList.size();i++){
            jpql.append("(");
            jpql.append("\"" + UuidUtils.base58Uuid() + "\",");
            jpql.append("\""+reservationList.get(i).getViewIsbn() + "\",");
            jpql.append("\"" + isDelete + "\",");
            jpql.append("\"" + isAnnounce + "\",");
            jpql.append("\""+ remark + "\",");
            jpql.append("\""+ status + "\",");
            jpql.append("\"" + reservationList.get(i).getStudentNo() + "\",");
            jpql.append("\"" +"操作人" + "\"," );
            jpql.append("\"" +date+"\",");
            jpql.append("\"" +reservationList.get(i).getLocation()+"\"");
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
     * 查询允许预约次数
     * @return 允许预约次数
     */
    public int getReserveCount(){
        //查询可预约次数findReservationCount
//        TypedQuery<Integer> query=em.createNamedQuery("TSetting.findReservationCount",Integer.class);
        TypedQuery<Integer> query=em.createNamedQuery("findReservationCount",Integer.class);

        try{
            Integer reserveCount = query.getSingleResult();
            return reserveCount;
        } catch (NoResultException nre) {
            return 0;
        } catch (NonUniqueResultException nure) {
            return 0;
        }
    }

    /**
     * 查询用户已预约次数
     * @param userId 用户ID
     * @return 用户已预约次数
     */
    public int getReservedCount(String userId){
        //查询useId已经预约次数(count数据必须以long类型接收)
        TypedQuery<Long> query1=em.createNamedQuery("TReservation.findReservationCountByUserId",Long.class);
        query1.setParameter("userId",userId);
        int reservedCount =query1.getSingleResult().intValue();
        return reservedCount;
    }

    /**
     * 批量查询用户的预约次数（TODO 使用待定） 郑晓东
     * @param list
     * @return
     */
    public List getReservedListCount(List<String> list){
        //TODO 未完待续
        Query query=em.createNativeQuery("SELECT tr.user_id,count(*) FROM t_reservation tr  WHERE tr.is_delete = 0  AND tr.user_id IN :list  GROUP BY tr.user_id");
        query.setParameter("list",list);
        List objectList = query.getResultList();
        return objectList;
    }

    /**
     * 查询用户是否已预约该图书，预约返回true，未预约返回false
     * @param userId 用户ID
     * @param isbn 图书ISBN
     * @return 预约返回true，未预约返回false
     */
    public boolean isReservedBook(String userId,String isbn,String location){
        TypedQuery<TReservation> query1=em.createNamedQuery("TReservation.findReservationByUserIdIsbn",TReservation.class);
        query1.setParameter("userId",userId);
        query1.setParameter("isbn",isbn);
        query1.setParameter("location",location);
        List<TReservation> list = query1.getResultList();
        if (list == null || list.size()==0) {
            return false;
        }
        return true;
    }

}

