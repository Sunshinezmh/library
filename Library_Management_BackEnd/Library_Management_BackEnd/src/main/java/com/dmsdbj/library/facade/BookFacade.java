package com.dmsdbj.library.facade;


import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.viewmodel.Book;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public interface BookFacade {
     ItooResult addBook(Book form);
     ItooResult bookDownExcelGet();
     ItooResult bookExportBookGet();
     ItooResult bookGetFavoriteBookTypeIdGet(String bookTypeId);
     ItooResult deleteBook(Long bookId);
     ItooResult deleteBooks(Long bookIds);
     ItooResult findBookDetails(String isbn);
     ItooResult findByConditions(String conditions);
     ItooResult getBook(Integer pageSize,Integer pageNum);
     ItooResult getBookByBookType(String bookType);
     ItooResult importBook(Book form);
     ItooResult findByConditions(String conditions,Integer pageSize,Integer pageNum);
     ItooResult upLoadPicture();
     ItooResult updateBook(Book body);
     ItooResult DownExcelGet();
     ItooResult ExportBook();
}
