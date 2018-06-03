//package com.dmsdbj.library.app.util;
//
///**
// * @param
// * @Author: judy
// * @Description:
// * @Date: Created in 11:10 2018/5/10
// */
//
//import com.dmsdbj.library.entity.TBookBasic;
//import com.dmsdbj.library.service.BookService;
//import com.dmsdbj.library.viewmodel.BookViewModel;
//
//import javax.ejb.Schedule;
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import java.util.List;
//
//
//@Stateless
//public class SchedulePicture {
//    @Inject
//    private BookService bookservice;
//    @Schedule(second = "*/120", minute = "*", hour = "*", persistent = false)
//    public void doWork() {
//      List<TBookBasic> list= bookservice.Getbookisbn();
//        for (int i = 0; i <list.size() ; i++) {
//            String A = list.get(i).getIsbn();
//            try {
//                bookservice.getSearchBookByISBN(A);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//      System.out.println("judy");
//
//    }
//
//}
