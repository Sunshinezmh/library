
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmsdbj.library.service;

import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.entity.TBookBasic;
import com.dmsdbj.library.entity.TReservation;
import com.dmsdbj.library.entity.TStudent;
import com.dmsdbj.library.repository.TReservationRepository;
import com.dmsdbj.library.viewmodel.Reservation;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 10283_000
 */
public class ReserveService {
    @Inject
    private StudentService studentService;
    @Inject
    private TReservationRepository tReservationRepository;

    private final String NO_BOOK="ISBN图书不存在";
    private final String NO_STUDENT="学号不存在";
    private final String UNALLOW="预约次数超出";
    private final String INSERFAIL="数据导入失败";


    //region PC端添加预约信息-武刚鹏-2017年10月24日16:38:19

    /**
     * PC端添加预约信息-武刚鹏-2017年10月24日16:38:19
     *修改：添加检查条件 郑晓东 2017年11月9日17点40分
     * @param reservation 预约实体viewmodel
     */
    public boolean addReserveByPC(Reservation reservation) {
        //检查学生是否存在，不存在则不允许预约
        List<TStudent> studentList = studentService.getStudentBystudentCode(reservation.getStudentNo());//TODO 此方法可以优化
        if (studentList == null || studentList.get(0) == null) {
            return false;
        }
        String userId = studentList.get(0).getId();

        //检查用户预约次数是否超出规定(超出则不允许预约)
        if (tReservationRepository.getReservedCount(userId)>=tReservationRepository.getReserveCount()) {
            return false;
        }

        //检查图书是否存在（不存在则不允许预约）
        TBookBasic tBookBasic = this.getIsbn(reservation.getIsbn());
        if (tBookBasic == null) {
            return false;
        }
        String isbn=tBookBasic.getIsbn();
        String location=tBookBasic.getLocation();

        //检查该用户是否已经预约该图书，预约不再重复添加预约记录(true为已存在预约，false为不存在预约)
        if (!tReservationRepository.isReservedBook(userId,isbn,location)) {
            //以上检查成功，允许预约，保存预约记录
            TReservation tReservation = new TReservation();
            tReservation.setId(UuidUtils.base58Uuid());
            tReservation.setIsbn(isbn);
            tReservation.setUserId(userId);
            short announce = 1;
            tReservation.setIsDelete(0);
            tReservation.setIsannounce(announce);
            tReservation.setStatus(0);
            tReservation.setLocation(location);
            tReservationRepository.create(tReservation);
        }

        return true;
    }
    //endregion

    //region --手机端预约查询是否重复预约同一本书-杜雨-2018年5月8日12:08:00
    public boolean isReservedBookResult(String userID,String isbn, String location){
        return tReservationRepository.isReservedBook(userID,isbn,location);
    }
    //endregion



    //region  手机端添加预约记录 -武刚鹏-2017年10月24日18:32:15

    /**
     * 手机端添加预约记录 -杜雨-2018年5月1日16:50:39
     * @param tReserve
     * @return
     */
    public boolean addReserveByApp(TReservation tReserve){
        String userID=tReserve.getUserId();
        String isbn=tReserve.getIsbn();
        String location=tReserve.getLocation();
        TReservation tReservation=new TReservation();
        //查询系统可预约次数
        int reserveCount=tReservationRepository.getReserveCount();
        //查询用户已经预约的次数
        int reservedCount=tReservationRepository.getReservedCount(userID);
        //查询该用户是否已经预约该图书
        boolean isReservedBookResult=tReservationRepository.isReservedBook(userID,isbn,location);
           if(reserveCount>reservedCount && isReservedBookResult==false){
                //isbn和用户id从界面获取 其他为默认值
                tReservation.setId(UuidUtils.base58Uuid());
                tReservation.setStatus(0);
                //1表示提醒
                short announce = 1;
                tReservation.setIsannounce(announce);
                tReservation.setIsDelete(0);
                tReservation.setUserId(userID);
                tReservation.setIsbn(isbn);
                tReservation.setLocation(location);
                tReservationRepository.create(tReservation);
                return true;
           }
           return false;
    }
    //endregion


   //region  手机端添加预约记录 -武刚鹏-2017年10月24日18:32:15
//
//    /**
//     * 手机端添加预约记录 -武刚鹏-2017年10月24日18:32:15
//     * 修改：添加判断预约次数和不可重复预约-王雅静-2017年11月23日16:13:58
//     * @param tReservation
//     */
//    public boolean addReserveByMobile(TReservation tReservation) {
//        //查询可预约次数
//        int reserveCount=tReservationRepository.getReserveCount();
//        //查询用户已经预约的次数
//        int reservedCount=tReservationRepository.getReservedCount(tReservation.getUserId());
//        //查询该用户是否已经预约该图书
//        boolean isReservedBookResult=tReservationRepository.isReservedBook(tReservation.getUserId(),tReservation.getIsbn());
//        if(tReservation.getUserId() ==null || tReservation.getUserId().isEmpty()||tReservation.getIsbn()==null || tReservation.getIsbn().isEmpty() || reservedCount >= reserveCount || isReservedBookResult==true){
//            return false;
//        }
//            //isbn和用户id从界面获取 其他为默认值
//            tReservation.setId(UuidUtils.base58Uuid());
//            tReservation.setStatus(0);
//            //1表示提醒
//            short announce = 1;
//            tReservation.setIsannounce(announce);
//            tReservation.setIsDelete(0);
//            tReservationRepository.create(tReservation);
//            return true;
//        }
  // endregion


    //region 根据isbn和location查询该用户借阅的书籍被谁预约（手机端用户信息界面）-杜雨-2018年5月9日15:20:34

    /**
     *  根据isbn和location查询该用户借阅的书籍被谁预约
     * @param isbn
     * @param location
     * @return reserveList
     */
    public List<Reservation> bookReservationInfo(String isbn,String location) {
        List<Reservation> reserveList = tReservationRepository.getBookReservationInfobyisbnAndlocation(isbn,location);

        return reserveList;
    }
    //endregion


    //region 根据用户id查询用户预约记录（手机端我的预约）-武刚鹏-2017年10月27日16:24:41

    /**
     * 根据用户id查询用户预约记录（手机端我的预约）-武刚鹏-2017年10月27日16:24:41
     *
     * @param userId
     * @return
     */
    public List<Reservation> findReserveByUserId(String userId) {
        List<Reservation> reserveList = tReservationRepository.findReserveByUserId(userId);

        return reserveList;
    }
    //endregion

    //region 编辑用户的预约记录（PC端编辑预约记录） -武刚鹏-2017年10月27日16:27:30

    /**
     * 编辑用户的预约记录（PC端编辑预约记录） -武刚鹏-2017年10月27日16:27:30
     *
     * @param reservation
     */
    public boolean updateReservation(Reservation reservation) {
        //默认只能修改预约的书
        return tReservationRepository.updateReservationIsbn(reservation);
    }
    //endregion

    //region 批量根据预约iD删除预约记录-武刚鹏-2017年11月1日15:47:36
    /**
     * 删除预约记录
     * @param reservationIds
     * @return
     */
    public Boolean deleteReservationes(String reservationIds) {
        return tReservationRepository.deleteReservations(reservationIds);
    }
    //endregion

    //region 根据用户ID和预约ID取消预约（手机端预约管理）-王雅静-2017年11月23日10:15:01
    /**
     * 取消预约记录
     * @param reservationId
     * @param userId
     * @return
     */
    public Boolean cancelReservationes(String reservationId,String userId) {
        return tReservationRepository.cancelReservations(reservationId,userId);
    }
    //endregion

    //region 模糊条件分页查询预约记录（PC端预约管理）-武刚鹏-2017年10月27日16:26:13

    /**
     * 模糊条件分页查询预约记录（PC端预约管理）-武刚鹏-2017年10月27日16:26:13
     *
     * @param condition
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PaginationEntity findReservationByPagenationCondition(String condition, int pageSize, int pageNum) {

        List<Reservation> list = tReservationRepository.findReservationByPagenationCondition(condition);
        PaginationEntity paginationEntity = new PaginationEntity(pageNum, pageSize, list);
        return paginationEntity;

    }
    //endregion

    //region 查询所有的预约记录-武刚鹏-2017年11月1日15:53:50
    /**
     * 查询所有的预约记录
     * @return
     */
    public List<Reservation> getAllReservation(){
        List<Reservation> list = tReservationRepository.findReservationByPagenationCondition(null);
        return list;

    }
    //endregion

    //region 根据用户id查询该用户查询预约记录的个数-武刚鹏-2017年11月1日15:48:17
    /**
     * 根据用户id查询该用户查询预约记录的个数-武刚鹏-2017年11月1日15:48:17
     * @param userId
     * @return
     */
    public Integer getBorrowReservationCount(String userId) {
        return tReservationRepository.getBorrowReservationCount(userId);
    }
    //endregion

    //region 根据isbn查询TBookBasic

    /**
     *
     * @param isbn
     * @return
     */
    private TBookBasic getIsbn(String isbn) {
        TBookBasic tBookBasic = tReservationRepository.getTBookBasicByisbn(isbn);
        return tBookBasic;
    }
//endregion

    //region 批量插入预约记录
    /**
     * 批量插入预约记录 -武刚鹏-2017年11月8日10:14:30
     * @param reservations
     */
    public boolean insertReservationList(List<Reservation> reservations){

        return tReservationRepository.insertReservationList(reservations);
    }

    //region 检查批量导入数据（未执行） 郑晓东 2017年11月12日
//    public List<Reservation> insertReservationList(List<Reservation> reservations) {
//        List<Reservation> reservationList =new ArrayList<>();
//        List<Reservation> errorReservations = new ArrayList<>();
//
//        //插入信息去重
//        reservationList = removeMore(reservations);
//
//        //利用SET特性，用于检验是否存在
//        Set<Object> set=new HashSet<>();
//
//        //图书信息验证
//        List<TBookBasic> tBookBasicList = removeMoreBook(reservationList);
//        if (tBookBasicList.size()==0) {
//            //所有图书信息都不存在
//            for (int i = 0; i < reservationList.size(); i++) {
//                //记录错误：不存在图书
//                errorReservations.add(returnErrorReservation(reservationList.get(i),NO_BOOK));
//                reservationList.remove(i);
//                i--;
//            }
//        }else{
//            for (int i = 0; i < tBookBasicList.size(); i++) {
//                set.add(tBookBasicList.get(i).getIsbn());
//            }
//            for (int i = 0; i < reservationList.size(); i++) {
//                if (set.contains(reservationList.get(i).getViewIsbn())) {
//                    //记录错误：不存在图书
//                    errorReservations.add(returnErrorReservation(reservationList.get(i),NO_BOOK));
//                    //移除不存在的图书
//                    reservationList.remove(i);
//                    i--;//必须这一步，否则所以还会增加，下一条记录不会在处理之中
//                }
//            }
//        }
//
//        //学生信息验证
//        set = new HashSet<>();
//        List<TStudent> tStudentList = removeNOStudent(reservationList);//学生验证结果
//        if (tStudentList.size()==0) {
//            //所有学生都不存在
//            for (int i = 0; i < reservationList.size(); i++) {
//                //记录错误：学生不存在
//                errorReservations.add(returnErrorReservation(reservationList.get(i),NO_STUDENT));
//                //移除学生不存在的记录
//                reservationList.remove(i);
//                i--;
//            }
//        }else{
//            //部分学生存在
//            for (int i = 0; i < tStudentList.size(); i++) {
//                set.add(tStudentList.get(i).getCode());
//            }
//            for (int i = 0; i < reservationList.size(); i++) {
//                if (set.contains(reservationList.get(i).getStudentNo())) {
//                    //记录错误：学生不存在
//                    errorReservations.add(returnErrorReservation(reservationList.get(i),NO_STUDENT));
//                    //移除学生不存在的记录
//                    reservationList.remove(i);
//                    i--;//必须这一步，否则所以还会增加，下一条记录不会在处理之中
//                }else{
//                    for (TStudent tStudent: tStudentList ) {
//                        if (tStudent.getCode()==reservationList.get(i).getStudentNo()) {
//                            //设置userId
//                            reservationList.get(i).setUserId(tStudentList.get(i).getId());
//                            break;//跳出本层循环
//                        }
//                    }
//                }
//            }
//        }
//
//        //查询可预约最大次数
//        int maxCount = tReservationRepository.getReserveCount();
//
//        //检查用户预约次数
//        set=new HashSet<>();
//        //验证学生
//        List<Object[]> objectList = getReservedCount(tStudentList);
//        for (int i = 0; i < objectList.size(); i++) {
//            set.add(objectList.get(i)[0]);
//        }
//        for (int i = 0; i < reservationList.size(); i++) {
//            //如果存在userID，则计算对应UserID的预约次数
//            if (set.contains(reservationList.get(i).getUserId().toString())) {
//                for (int j = 0; j <objectList.size() ; j++) {
//                    if (objectList.get(j)[0].toString() == reservationList.get(i).getUserId().toString()) {
//                        int count = Integer.parseInt(objectList.get(j).toString());//获取用户预约次数
//                        //如果小于最大预约次数 ，则允许插入
//                        if (count < maxCount) {
//                            objectList .get(i)[1]=count+1;
//                        }else{
//                            //记录错误：超出预约次数
//                            errorReservations.add(returnErrorReservation(reservationList.get(i),UNALLOW));
//                            //移除超出预约次数的记录
//                            reservationList.remove(i);
//                            i--;
//                            break;//跳出本层循环
//                        }
//                    }
//                }
//            }else{
//                //不存在，则初始化该用户预约次数
//                set.add(reservationList.get(i).getUserId());
//                Object[] objects=new Object[]{reservationList.get(i).getUserId(),1};
//                //将该用户预约集合存入用户预约记录集中
//                objectList.add(objects);
//            }
//        }
//        //以上验证通过允许批量插入
//        boolean flag=tReservationRepository.insertReservationList(reservationList);
//
//        //检查是否插入成功
//        if (flag) {
//            for (int i = 0; i < reservationList.size(); i++) {
//                //记录错误：插入记录失败
//                errorReservations.add(returnErrorReservation(reservationList.get(i),INSERFAIL));
//            }
//        }
//
//        //返回错误集
//        return errorReservations;
//    }
    //endregion

    /**
     * 记录错误集：批量插入预约专用 郑晓东 2017年11月12日13点11分
     * @param reservation 预约记录
     * @param reason 错误原因
     * @return 含错误原因的预约记录
     */
    private Reservation returnErrorReservation(Reservation reservation,String reason){
        reservation.setReason(reason);
        return reservation;
    }

    /**
     * 去除重复插入数据（去重条件：学号+isbn） 郑晓东 2017年11月10日20点36分
     * @param reservationList 验证数据
     * @return 去重结果
     */
    private List<Reservation> removeMore(List<Reservation> reservationList){
        List<Reservation> list=new ArrayList<>();
        Set<Reservation> set = new HashSet<>();//利用set去重
        for (int i = 0; i < reservationList.size(); i++) {
            Reservation reservation=reservationList.get(i);
            if (!set.contains(reservation)) {
                set.add(reservation);
            }
        }
        list.addAll(set);
        return list;
    }

    /**
     * 图书信息去重查询 郑晓东 2017年11月10日 20点59分
     * @param reservationList 预约集合
     * @return 图书信息集合
     */
    private List<TBookBasic> removeMoreBook(List<Reservation> reservationList){
        List<String> list=new ArrayList<>();
        List<TBookBasic> tBookBasics= new ArrayList<>();
        for (int i = 0; i < reservationList.size(); i++) {
            list.add(reservationList.get(i).getViewIsbn());
        }
        if (list.size()>0) {
            tBookBasics=tReservationRepository.getTBookBasicByisbn(list);
        }
        return tBookBasics;
    }

    /**
     * 根据预约集合查询学生信息 郑晓东 2017年11月10日21点17分
     * @param reservationList 预约集合
     * @return 学生信息集合
     */
    private List<TStudent>  removeNOStudent(List<Reservation> reservationList){
        List<String> list=new ArrayList<>();
        List<TStudent> tStudents= new ArrayList<>();
        for (int i = 0; i < reservationList.size(); i++) {
            list.add(reservationList.get(i).getStudentNo());
        }
        if (list.size()>0) {
            tStudents=studentService.getStudentByCodeList(list);
        }
        return tStudents;
    }

    /**
     * 根据学生查询学生预约次数  郑晓东 2017年11月11日17点30分
     * @param studentList 学生信息集合
     * @return 学生预约次数集合
     */
    private List getReservedCount(List<TStudent> studentList){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < studentList.size(); i++) {
            list.add(studentList.get(i).getId());
        }
        return tReservationRepository.getReservedListCount(list);
    }

    /**
     * 通过上传的预约集合获取具体预约数据 郑晓东 2017年11月11日20点24分
     * @param reservationList 上传的预约集合
     * @return 具体预约集合
     */
    private List<TReservation> setReserveList(List<Reservation> reservationList){
        List<TReservation>  list = new ArrayList<>();
        for (int i = 0; i < reservationList.size(); i++) {
            TReservation tReservation=setReservation(reservationList.get(i));
            list.add(tReservation);
        }
        return list;
    }


    //endregion

    /**
     * 设置预约实体 郑晓东  2017年11月11日20点18分
     * @param reservation  预约扩展信息（上传的预约）
     */
    private TReservation setReservation(Reservation reservation){
        TReservation tReservation = new TReservation();
        tReservation.setId(UuidUtils.base58Uuid());
        tReservation.setIsbn(reservation.getViewIsbn());
        tReservation.setUserId(reservation.getUserId());
        short announce = 1;
        tReservation.setIsDelete(0);
        tReservation.setIsannounce(announce);
        tReservation.setStatus(0);
        return tReservation;
    }

    public void testRepositoryFunction(){
        List<String> list= new ArrayList<>();
        list.add("aaaaaa");
        list.add("bbbb");
        list.add("cccc");
//        for (int i = 0; i < list.size(); i++) {
//            list.remove(i);
//        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j)=="aaaaaa") {
                    break;
                }
            }
            System.out.println(list.get(i));
        }
    }

    /**
     * 王华伟---2017年12月1日--根据查询条件进行导出
     * @param condition
     * @return
     */
    public List<Reservation> findReservationByCondition(String condition) {
        List<Reservation> list = tReservationRepository.findReservationByPagenationCondition(condition);
        return list;
    }
}