package com.dmsdbj.library.app.util;

/**
 *
 * @Author: 王雪芬
 * @Description:固定時執行
 * @Date: Created in 9:40 2018/4/22
 */
import com.dmsdbj.library.controller.BorrowingController;
import com.dmsdbj.library.entity.TBorrowing;
import com.dmsdbj.library.entity.TStudent;
import com.dmsdbj.library.service.BorrowingService;
import com.dmsdbj.library.service.StudentService;
import com.dmsdbj.library.service.TeacherService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.*;
import javax.ejb.Schedule;
import javax.inject.Inject;

//使用singleon会按照执行时间执行。如果使用@Stateless或者是Stateful那么他会每分钟调用一次
@Singleton
public class ScheduleExample {

    @Inject
    private BorrowingController borrowingController;
    @Inject
    private BorrowingService borrowingService;
    @Inject
    private StudentService studentService;
    @Inject
    private SendMail sendMaill;
    @Inject
    private TeacherService teacherService;


//一年中每天的上午1点，2点，下午8点要被调用一次。年和月没有就是默认值，表示每年和每月都回去执行。
//    @Schedule(second = "*",minute = "*",hour = "")

      @TransactionAttribute(TransactionAttributeType.NEVER)
//    @Schedule(second = "*/200", minute = "*", hour = "*", persistent = false)
      //8点执行一次
   @Schedule(hour = "8", persistent = false)
      public  void  printTime(){
       //查看没有还书的人
       List<TBorrowing> list= borrowingService.doTime();
       //如果有人没有换则发送邮件。
       if(list.size()>0){
           for (int i = 0; i <list.size() ; i++) {
               doWork( list.get(i).getIsbn(),list.get(i).getUserId());
           }

       }
    }

     public void doWork(String isbn,String code ){
        //查询是否有超出时间
         boolean flag = borrowingService.BorrowingTime(isbn,code);
         //如果查询借阅记录。
          if (flag =true){
           boolean flag1= borrowingService.addAppBorrowingTime(isbn,code);
              if(flag1=true){
                  //查询该学生好几号
                  List<TStudent> tStudents = studentService.getStudentBystudentCode(code);
                  //发送邮件，发送管理员和该学生
                  sendMaill.sendMail(tStudents.get(0).getEmail(),"18333640045@163.com");
              }
         }
  }
}
