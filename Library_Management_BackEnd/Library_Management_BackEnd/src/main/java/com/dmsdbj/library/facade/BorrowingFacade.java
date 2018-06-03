package com.dmsdbj.library.facade;


import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.viewmodel.Borrowing;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public interface BorrowingFacade {
     ItooResult addBorrowing(Borrowing form);
     //ItooResult borrowingDownExcelGet();
     ItooResult borrowingExportBorrowingGet();
     ItooResult borrowingGetBorrowingByConditonConditonsPageSizePageNumGet(String conditons,Integer pageSize,Integer pageNum);
     ItooResult deleteBorrowing(Long borrowingId);
     ItooResult deleteBorrowings(Long borrowingIds);
     ItooResult getAllBorrowing(Integer pageSize,Integer pageNum);
     ItooResult getBorrowing(String userId);
     ItooResult getBorrowingByStatus(String status);
     ItooResult importBorrowing(Borrowing form);
     ItooResult updateBorrowing(Borrowing body);
     ItooResult DownExcelGet();
     ItooResult ExportBorrowing();
}
