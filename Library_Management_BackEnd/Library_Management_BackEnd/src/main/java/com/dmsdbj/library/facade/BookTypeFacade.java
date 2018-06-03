package com.dmsdbj.library.facade;


import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.viewmodel.BookType;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public interface BookTypeFacade {
     ItooResult addBookType(BookType form);
     ItooResult bookTypeDownExcelGet();
     ItooResult bookTypeExportBookTypeGet();
     ItooResult bookTypeGetAllTypePageSizePageNumGet(String pageNum,String pageSize);
     ItooResult bookTypeGetBookTypeByConditonConditonsPageSizePageNumGet(String conditons,Integer pageSize,Integer pageNum);
     ItooResult bookTypeGetSubByIDBookTypeIdGet(String bookTypeId);
     ItooResult bookTypeInitTreeGet();
     ItooResult deleteBookType(Long bookTypeId);
     ItooResult deleteBookTypes(Long bookTypeIds);
     ItooResult importBookType(BookType form);
     ItooResult updateBookType(BookType body);
     ItooResult DownExcelGet();
     ItooResult ExportBookType();
}
