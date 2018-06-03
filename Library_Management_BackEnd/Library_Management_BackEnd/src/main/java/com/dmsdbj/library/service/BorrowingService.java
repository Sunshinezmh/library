
package com.dmsdbj.library.service;


import com.dmsdbj.library.app.util.FastDfsUtils;
import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.entity.*;
import com.dmsdbj.library.repository.*;
import com.dmsdbj.library.viewmodel.BookViewModel;
import com.dmsdbj.library.viewmodel.Borrowing;
import com.dmsdbj.library.viewmodel.Student;
import org.apache.commons.collections.CollectionUtils;
//import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.persistence.Temporal;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author 10283_000
 */
public class BorrowingService {

    @Inject
    private TBorrowingRepository tBorrowingRepository;
    @Inject
    private StudentService studentService;
    @Inject
    private TBookRepository tBookRepository;
    @Inject
    private TBookBasicRepository tBookBasicRepository;

    @Inject
    private TStudentRepository tStudentRepository;
    @Inject
    private TWarehuse tWarehuse;
    @Inject
    private TWarehuseRepository tWarehuseRepository;

    @Inject
    private TSettingRepository tSettingRepository;

    /**
     * 王华伟--2017-10-14--根据用户ID查找借阅记录
     *
     * @param userId
     * @return
     */
    public List<TBorrowingModel1> getBorrowingByUserId(String userId) {

        return tBorrowingRepository.getBorrowingByUserId(userId);
    }

    /**
     * 编辑借阅记录--郭晶晶-2017年11月10日10:41:52
     *
     * @param Borrowing
     */
    public boolean editorBorrowing(TBorrowing Borrowing) {
        try {
            String  id = Borrowing.getId();
            TBorrowing tBorrowing = tBorrowingRepository.find(id);
            if(tBorrowing == null){
                return false;
            }
            tBorrowing.setUserId(Borrowing.getUserId());
            tBorrowing.setBookId(Borrowing.getBookId());
            tBorrowing.setIsOverdue(Borrowing.getIsOverdue());
			//如果实际还书日期有值，则借阅状态改为已还
            if(Borrowing.getRealDate()==null ){
                tBorrowing.setBorrowStatus(1);
            }else {
                tBorrowing.setBorrowStatus(2);
            }

            tBorrowing.setRenew(Borrowing.getRenew());
            tBorrowing.setBorrowTime(Borrowing.getBorrowTime());
            tBorrowing.setRealDate(Borrowing.getRealDate());
            tBorrowing.setUpdateTime(new Date());
            tBorrowing.setIsOverdue(Borrowing.getIsOverdue());
            tBorrowing.setLocation(Borrowing.getLocation());
            tBorrowingRepository.edit(tBorrowing);

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 添加借阅记录（电脑端）-郭晶晶-2017年11月6日12:14:52
     *
     * @param tBorrowingModel1
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean addBorrowing(TBorrowingModel1 tBorrowingModel1) {

        String searchNum = tBorrowingModel1.getSearchNum();
                //查询该书是不是在借状态
                // 1.根据索书号查询对应bookId
                List<BookViewModel> bookList = tBookRepository.getBookBySearchNum(searchNum);
                String bookId = bookList.get(0).getId();
                // 2. 在t_borrowing表中查找是否该书是已借阅状态
                List<TBorrowing> borrwingBookId =  tBorrowingRepository.findBorrowingByBookId(bookId);
                if ( borrwingBookId.size()>0){
                    return false;
                }
                // 3.判断该用户是否正在借这本书
                String code = tBorrowingModel1.getCode();
                List<TBorrowing> borr  = tBorrowingRepository.getBorrowing(bookId,code);
                if( borr.size()>0){
                    return false;
            }
        //查询是否可借（书的可用数量是否为0）
        List<wareHuseNew> warehuses  = tWarehuseRepository.getCountAndId(searchNum);
        if (warehuses.get(0).getuseNum() ==0){
            return false;
        }
        //查询系统中可借阅次数
        List<TSetting> settings = tSettingRepository.getAlltSettings();
        int borrowingNum = settings.get(0).getBorrowingNumber();
        //查询该用户已经借阅的书本数量
        String Stcode = tBorrowingModel1.getCode();
        List<TBorrowingModel1> borrowing = tBorrowingRepository.getBorrowingByStatus(1,Stcode);
        if (borrowing.size() >= borrowingNum) {
            return false;
        }
        try {
            //可用量-1
            int tSearch = warehuses.get(0).getuseNum();
            tSearch=tSearch-1;
            String wId = warehuses.get(0).getId();
            TWarehuse form = tWarehuseRepository.find(wId);
            form.setuseNum(tSearch);
            tWarehuseRepository.edit(form);

            //借阅时间为服务器当前时间
            Date date = new Date();
            //预计归还时间根据setting表中查找的借阅时长增加
            int borrowingDay = settings.get(0).getBorrowingTime();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate returnDate = LocalDate.now().plusDays(borrowingDay);
            ZonedDateTime zdt = returnDate.atStartOfDay(zoneId);
            Date end = Date.from(zdt.toInstant());

            //借阅记录+1
            TBorrowing tBorrowing = new TBorrowing();
            tBorrowing.setUserId(tBorrowingModel1.getCode());
            tBorrowing.setBookId(tBorrowingModel1.getBookId());
            tBorrowing.setBorrowTime(tBorrowingModel1.getBorrowTime());
            if(tBorrowingModel1.getRealDate() == null){
                tBorrowingModel1.setRealDate(null);
            }
            tBorrowing.setRealDate(tBorrowingModel1.getRealDate());
            tBorrowing.setEndDate(end);
            tBorrowing.setRenew(tBorrowingModel1.getRenew());
            tBorrowing.setIsOverdue(tBorrowingModel1.getIsOverdue());
            tBorrowing.setIsbn(searchNum);
			//如果实际还书日期有值，则借阅状态改为已还
            if(tBorrowingModel1.getRealDate()==null ){
                tBorrowing.setBorrowStatus(1);
            }else {
                tBorrowing.setBorrowStatus(2);
            }

            tBorrowing.setCompensation(0.0);

            tBorrowing.setId(UuidUtils.base58Uuid());
            tBorrowingRepository.create(tBorrowing);
            return true;
        }catch (Exception e){
            return false;
        }



    }

    /**
     * 王华伟--2017--10--23--查询全部借阅记录
     *
     * @return
     */
    public List<BookViewModel> getAllBorrowing() {
        //（1）根据userId和status查询在TBorrowing表中查询出所有记录
        List<BookViewModel> listborrowing = tBorrowingRepository.getAllBorrowing();
        return listborrowing;
    }

    /**
     * 王雅静--2018年4月24日10:29:53--查询全部已借借阅记录
     *
     * @return
     */
    public List<BookViewModel> getAllBorrowedRecord() {
        //（1）根据userId和status查询在TBorrowing表中查询出所有记录
        List<BookViewModel> listborrowing = tBorrowingRepository.getAllBorrowedRecord();
        return listborrowing;
    }

    /**
     * 王华伟--2017--10--27--loopStatement()
     * 将查询出来的数据进行整合
     *
     * @return
     */
    public List<Borrowing> loopStatement() {
        Borrowing borrowingByStatus = new Borrowing();
        List<Borrowing> borrowingByStatusList = new ArrayList<>();
        return borrowingByStatusList;
    }


    /**
     * 王华伟--2017--10-15--根据状态查询借阅记录
     *
     * @param status and userId
     * @return
     */
    public List<Object> getBorrowingByStatusAndCode(Integer status, String userId) {


        List<TBorrowing> listborrowing = tBorrowingRepository.getISBNByStatusAndCode(status, userId);

        List<Object> finalbook = new ArrayList<>();

        for(int i = 0;i < listborrowing.size();i++) {
            String isbn = listborrowing.get(i).getIsbn();
            List<BookViewModel> bookViewModels = tBorrowingRepository.getAllBorrowingByStatusAndCode( status,userId,isbn);
            finalbook.add(bookViewModels);
        }
         return finalbook;

    }

    //============================================多表连查=================================

    /**
     * 王华伟--2017--10--26--根据userId查询借阅记录
     *
     * @param userId
     * @return
     */
    public List<TBorrowingModel1> findAllBorrowingByUserId(String userId) {

        List<TBorrowingModel1> list = tBorrowingRepository.getBorrowingByUserId(userId);
       int count= list.size();

        return list;
    }

    /**
     * 王华伟--2017--10--29--根据条件查询（模糊查询）
     *
     * @param condition
     * @return
     */
    public List<BookViewModel> getBorrowingByCondition(String condition) {

        //（1）根据userId和status查询在TBorrowing表中查询出所有记录
        List<BookViewModel> listborrowing = tBorrowingRepository.findBorrowingByConditon(condition);

        return listborrowing;
    }

    public void insertBorrowingList(List<TBorrowingModel1> tBorrowingList) {
        tBorrowingRepository.insertBorrowingList(tBorrowingList);
    }

    /**
     * 批量删除-王华伟-2017年11月6日14:32:43
     *
     * @param borrowingId
     * @return
     */
    public boolean deleteBorrowingIds(String borrowingId) {
        TBorrowing tBorrowing = tBorrowingRepository.find(borrowingId);
        if (tBorrowing != null) {
            tBorrowing.setIsDelete(1);
            tBorrowingRepository.edit(tBorrowing);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 手机端续借--郭晶晶--2017年11月8日10:02:46
     *
     * @param borrowingId
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public TBorrowing updateBorrowing(String borrowingId) {
        //基本设置表中的续借次数
        List<TSetting> settings = tSettingRepository.getAlltSettings();
        int renweNumber = settings.get(0).getRenewNumber();
        //是否超过续借次数
        TBorrowing tBorrowing = tBorrowingRepository.find(borrowingId);
        if (tBorrowing == null) {
            return null;
        }
        int renewNum = tBorrowing.getRenew();
        if (renewNum >= renweNumber){
            return  null;
        }

        // 是否超期
        Date  endDate = tBorrowing.getEndDate();
        Date date = new Date();
        if (date.compareTo(endDate)>1 ){
            return null;
        }
        try {
            //续借次数+1
            renewNum=renewNum+1;
            //基本表中的借阅时长
            int borrowingDay = settings.get(0).getBorrowingTime();
            //更新借阅表中的借阅时间和续借次数
            boolean flag = false;

            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate returnDate = LocalDate.now().plusDays(borrowingDay);
            ZonedDateTime zdt = returnDate.atStartOfDay(zoneId);
            Date end = Date.from(zdt.toInstant());

            tBorrowing.setBorrowTime(date);
            tBorrowing.setEndDate(end);
            tBorrowing.setRenew(renewNum);
            tBorrowingRepository.edit(tBorrowing);

            TBorrowing  borrowingentity= tBorrowingRepository.find(borrowingId);
            return borrowingentity;

        }catch (Exception e){
            return null;
        }


    }


    /**
     * 还书--郭晶晶---2017年11月11日10:14:13
     * @param code
     * @param searchNum
     * @return
     */
    public boolean returnBorrwing(String code, String searchNum) {
        //查找是否有改记录
        //根据索书号查找对应的ID  t_book
        List<BookViewModel> bookList = tBookRepository.getBookBySearchNum(searchNum);
        String bookId = bookList.get(0).getId();
        //查找是否有改记录
        List<TBorrowing>  borrowingList = tBorrowingRepository.getBorrowing(bookId,code);
        if (CollectionUtils.isNotEmpty(borrowingList)){
            String  id = borrowingList.get(0).getId();
            TBorrowing tBorrowing = tBorrowingRepository.find(id);
            Date date = new Date();
            tBorrowing.setRealDate(date);
            tBorrowing.setUpdateTime(date);
            tBorrowing.setBorrowStatus(2);
            tBorrowing.setIsDelete(0);
            tBorrowingRepository.edit(tBorrowing);

            List<wareHuseNew> warehuses  = tWarehuseRepository.getCountAndId(searchNum);
            //可用量+1
            int tSearch = warehuses.get(0).getuseNum();
            tSearch=tSearch+1;
            String wId = warehuses.get(0).getId();
            TWarehuse form = tWarehuseRepository.find(wId);
            form.setuseNum(tSearch);
            tWarehuseRepository.edit(form);

            return true;
        }
            return false;
    }


    /**
     * 王华伟---还书手机端--2018年3月22日14:26:44
     * @param
     * @param
     * @return
     */
//    String userID, String isbn,String location,String picture
    public boolean returnBorrowingRecordAPP(BookViewModel form) {
        //首先去借阅表中查询该学号是否有借阅记录（根据学号和isbn）
        //然后修改T_borrowing
        //最后修改T_warehouse

        //查找是否有该记录
        String userID=form.getUserId();
        String isbn=form.getIsbn();
        String location=form.getLocation();
        String picture=form.getremark();
        List<TBorrowing>  borrowingList = tBorrowingRepository.getBorrowingByISBN(userID,isbn,location);
        if(CollectionUtils.isNotEmpty(borrowingList)){
            String  id = borrowingList.get(0).getId();
            TBorrowing tBorrowing = tBorrowingRepository.find(id);
            Date date = new Date();
            tBorrowing.setRealDate(date);
            tBorrowing.setUpdateTime(date);
            tBorrowing.setBorrowStatus(2);
            tBorrowing.setIsDelete(0);
            tBorrowing.setRemark(picture);
            tBorrowingRepository.edit(tBorrowing);

            //再次查询useCode
            List<TWarehuse> warehusesapp  = tWarehuseRepository.getUseCountBylocation(isbn,location);
            int useCount = warehusesapp.get(0).getuseNum();
            //可用量+1
            int useCountNew = useCount +1;
            String wId = warehusesapp.get(0).getId();
            TWarehuse result = tWarehuseRepository.find(wId);
            result.setuseNum(useCountNew);
            tWarehuseRepository.edit(result);
            return true;

        }
            return false;

    }


    /**
     * 借书---郭晶晶---2017年11月11日19:45:40
     * @param searchNum
     * @param code
     * @return
     */
    public boolean addBorrowingRecord(String searchNum, String code) {
        //查询是否可借（书的可用数量是否为0）
        List<wareHuseNew> warehuses  = tWarehuseRepository.getCountAndId(searchNum);
        if (warehuses.get(0).getuseNum() ==0){
            return false;
        }
        //根据索书号查找对应的ID  t_book
        List<BookViewModel> bookList = tBookRepository.getBookBySearchNum(searchNum);
        String bookId = bookList.get(0).getId();
        //判断借阅次数
        List<TSetting> settings = tSettingRepository.getAlltSettings();
        int borrowingNum = settings.get(0).getBorrowingNumber();
        //查询该用户已经借阅的书本数量
        List<TBorrowingModel1> borrowing = tBorrowingRepository.getBorrowingByStatus(1,code);
        if (borrowing.size() >= borrowingNum) {
            return false;
        }
        //判断是否再借
        List<TBorrowing>  borrowingList = tBorrowingRepository.getBorrowing(bookId,code);
        if (CollectionUtils.isEmpty(borrowingList)){
            int borrowingDay = settings.get(0).getBorrowingTime();
            TBorrowing tBorrowing = new TBorrowing();
            tBorrowing.setId(UuidUtils.base58Uuid());
            tBorrowing.setUserId(code);
            tBorrowing.setBookId(bookId);
            tBorrowing.setIsDelete(0);
            tBorrowing.setIsOverdue(0);
            Date date = new Date();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate returnDate = LocalDate.now().plusDays(borrowingDay);
            ZonedDateTime zdt = returnDate.atStartOfDay(zoneId);
            Date end = Date.from(zdt.toInstant());
            tBorrowing.setEndDate(end);
            tBorrowing.setCreateTime(date);
            tBorrowing.setBorrowStatus(1);
            tBorrowing.setBorrowTime(date);
            tBorrowing.setRenew(0);
            tBorrowingRepository.edit(tBorrowing);

            //可用量-1
            int tSearch = warehuses.get(0).getuseNum();
            tSearch=tSearch-1;
            String wId = warehuses.get(0).getId();
            TWarehuse form = tWarehuseRepository.find(wId);
            form.setuseNum(tSearch);
            tWarehuseRepository.edit(form);

            return true;
        }

        return false;
    }
    /**
     * 根据条件进行模糊查询解决方法+王雅静+2018年4月25日17:14:47
     *
     * @param MoHu_str
     * @return
     */
    public PaginationEntity findBorrowedRecordByConditions(String MoHu_str, int pageNum, int pageSize) {
        List<BookViewModel> list = tBorrowingRepository.findBorrowedByConditions(MoHu_str);
        //王雪芬-排除重复项-2018年4月16日17:17:18
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getBookName().equals(list.get(i).getBookName())) {
                    list.remove(j);
                }
            }
        }
        PaginationEntity paginationEntity = new PaginationEntity(pageNum, pageSize, list);
        return paginationEntity;
    }

    /**
     * 根据学生学号查询学生信息及已借阅数量 郑晓东 2017年11月12日21点55分
     * @param code 学号
     * @return 学生信息及借阅数量
     */
    public Student findStudentAndBorrowCount(String code) throws IOException {
        //单独查询学生
        List<TStudent> list = studentService.getStudentBystudentCode(code);
        if (list == null || list.size()==0) {
            return null;
        }
        TStudent tStudent = list.get(0);
        //查询用户借阅数量
        Long count = tBorrowingRepository.findStudentAndBorrowCount(code);
        if (count == null) {
            count=Long.parseLong("0");
        }
        Student student= new Student();
        student.setId(tStudent.getId());
        student.setStudentName(tStudent.getName());
        student.setStudentCode(code);
        student.setSex(tStudent.getSex());
        student.setClassName(tStudent.getClassesName());

        if (student.getPicture() != null) {
            if (tStudent.getPictrue().contains("http://")) {
                student.setPicture(tStudent.getPictrue());
            }else{
                student.setPicture(FastDfsUtils.getFastDfsIP()+tStudent.getPictrue());
            }
        }
        student.setBorrowCount(count.intValue());
        return student;
    }

    /**
     * 手机端添加借阅记录--2018年4月28日-- 杜雨--王华伟修改--加location字段--2018年5月1日18:18:16
     * @param isbn
     * @param code
     * @return
     */
    public boolean addAppBorrowingRecord(String isbn, String code,String location,String ID) {


        //判断库存数量是否充足

        //首先根据isbn去twarehuse表中查询是否还有书可借，也就是usecount大于0
        //调用张婷接口---public List<TWarehuse> getUseCount(String isbn)
        List<TWarehuse> warehusesapp  = tWarehuseRepository.getUseCountBylocation(isbn,location);
        //判断warehusesapp是否为空。
        if (warehusesapp.size()==0){
            return false;
        }
        int useCount = warehusesapp.get(0).getuseNum();

        //库存充足
        if(useCount >0){


            //查询该用户已经借阅的书本数量
            List<TBorrowing> borrowingUserNumber = tBorrowingRepository.getBorrowingUserNumber(2,ID);

            //查询系统设定的借阅次数
            List<TSetting> settings = tSettingRepository.getAlltSettings();
            int borrowingNum = settings.get(0).getBorrowingNumber();

            //如果借阅次数未达到上限，说明有可借阅量
            if( borrowingUserNumber.size() <  borrowingNum){

                //查询这本书是否已被该用户借阅，一本书只允许一次借阅一本，不可重复借阅
                //查询状态不等于2的借阅记录（为了防止重复借阅）
                List<BookViewModel> borrowing = tBorrowingRepository.getBorrowingByStatusAndCode(2,ID,isbn,location);

                //没有重复借阅，可以借阅此书
                if(borrowing.size()==0){

                    int borrowingDay = settings.get(0).getBorrowingTime();
                    TBorrowing tBorrowing = new TBorrowing();
                    tBorrowing.setId(UuidUtils.base58Uuid());
//                    tBorrowing.setUserId(code);
                    //把TBorrowing表的userID的添加成为ID（这里的ID不是手机号）
                    tBorrowing.setUserId(ID);
                    //因为手机端借还书无法获取bookID所以手动添加为null
                    tBorrowing.setBookId("null");
                    tBorrowing.setIsDelete(0);
                    tBorrowing.setIsOverdue(0);
                    Date date = new Date();
                    ZoneId zoneId = ZoneId.systemDefault();
                    LocalDate returnDate = LocalDate.now().plusDays(borrowingDay);
                    ZonedDateTime zdt = returnDate.atStartOfDay(zoneId);
                    Date end = Date.from(zdt.toInstant());
                    tBorrowing.setEndDate(end);
                    tBorrowing.setCreateTime(date);
                    tBorrowing.setBorrowStatus(1);
                    tBorrowing.setBorrowTime(date);
                    tBorrowing.setRenew(0);
                    tBorrowing.setIsbn(isbn);
                    tBorrowing.setLocation(location);
                    tBorrowingRepository.edit(tBorrowing);

                    String wId = warehusesapp.get(0).getId();
                    TWarehuse form = tWarehuseRepository.find(wId);
                    form.setuseNum(useCount - 1);
                    tWarehuseRepository.edit(form);
                    return true;
                }
            }
        }

        return false;

    }


    /**
     * 还书提醒（万达使用）--王华伟--2018年4月4日15:01:21
     * @param userId
     * @return
     */
    public List<BookViewModel> ReturnBookRemind(String userId) {
        //查询出所有该用户状态不等于2的借阅记录
        List<BookViewModel> listborrowing = tBorrowingRepository.ReturnBookRemind(userId);

        if(listborrowing.size() <0){
            return null;
        }
        return listborrowing;

    }
	
	 /**
     * 查看没有还书的人-王雪芬-2018年5月8日16:47:48
     * @return
     */
    public List<TBorrowing> doTime() {
        List<TBorrowing> list=tBorrowingRepository.doTime();
        return  list;

    }
	    /**
     * 查看该用户的isbn是否为超期状态-王雪芬-2018年4月29日17:55:41
     * @param isbn
     * @param code
     * @return
     */
    public boolean  BorrowingTime(String isbn, String code) {
        boolean flag = tBorrowingRepository.BorrowingTime(isbn,code);
        //为true已经超期，则发送短信
        return flag;
    }
	 /**
     * 查看是否超出预期，如果超出预期则把数据库中超期字段更改为1,查看是否离预期加盟还书还差1天。如果差1天则发送邮件-王雪芬-2018年4月29日17:54:51
     * @param isbn
     * @param code
     * @return
     */

    public boolean addAppBorrowingTime(String isbn, String code) {
        boolean flag = tBorrowingRepository.addAppBorrowingTime(isbn,code);
        //为true，则发送短信
        return flag;
    }
	
	
	
	
	
	


}






