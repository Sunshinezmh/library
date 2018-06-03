package com.dmsdbj.library.controller;

import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.app.util.BookNetInfoUtil;
import com.dmsdbj.library.app.util.FastDfsUtils;
import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.controller.util.UploadFileUtil;
import com.dmsdbj.library.entity.TBook;
import com.dmsdbj.library.entity.TBookAndType;
import com.dmsdbj.library.entity.TBookBasic;
import com.dmsdbj.library.entity.TBorrowing;
import com.dmsdbj.library.repository.TBookRepository;
import com.dmsdbj.library.service.BookService;
import com.dmsdbj.library.service.BookTypeService;
import com.dmsdbj.library.viewmodel.BookViewModel;
import com.dmsdbj.library.viewmodel.hotBookViewModel;
import com.wordnik.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.jplus.hyberbin.excel.utils.ExcelUtil;
import org.slf4j.Logger;
import com.dmsdbj.library.entity.TWarehuse;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/book")

//用在类上，说明该类的作用
@Api(value = "/book", description = "the book API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-09-24T17:35:42.344+08:00")
public class BookController {

    @Inject
    private TBookRepository TBookRepository;
    @Inject
    private BookService bookService;
    @Inject
    private Logger log;
    @Inject
    private TBorrowing TBorrowing;

    @Inject
    private BookTypeService bookTypeService;


    /**
     * 图书入库-张婷-2017-11-2 14:50:38
     *
     * @param form
     * @param securityContext
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @POST
    @Path("/addBookInfo")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //用在方法上，说明方法的作用
    @ApiOperation(value = "图书入库的保存", notes = "图书入库的保存", response = BookViewModel.class, tags = "book")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = BookViewModel.class)
            ,
            @ApiResponse(code = 409, message = "an existing item already exists", response = BookViewModel.class)})
    public ItooResult addBookInfo(@ApiParam(value = "note that needs to be added", required = true) BookViewModel form, @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException {
        try {
            if (form.getpicture() != null) {
                if (form.getpicture().contains("http")) {
                    form.setpicture(form.getpicture().substring(form.getpicture().indexOf(FastDfsUtils.getFastDfsIP())
                            + FastDfsUtils.getFastDfsIP().length()));
                }
            }

            bookService.addBookInfo(form);

            log.info("图书入库成功");
            return ItooResult.build("0000", "图书信息添加成功", form);

        } catch (Exception e) {
            log.error("添加失败", e);
            return ItooResult.build("1111", "图书添加失败");

        }

    }

    /**
     * @param isbn
     * @param securityContext
     * @return 扫描抓取图书基本信息(图书入库页面)-张婷-2017-11-2 14:52:22
     * @throws NotFoundException
     * @throws URISyntaxException
     * @throws Exception
     */
    @GET
    @Path("/getBookBasicByISBN/{isbn}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //用在方法上，说明方法的作用
    @ApiOperation(value = "通过isbn扫描抓取图书基本信息(图书入库接口)", notes = "通过isbn扫描抓取图书基本信息(图书入库接口)", response = BookViewModel.class, tags = "book")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = TBookBasic.class)
            ,
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = TBookBasic.class)
            ,
            @ApiResponse(code = 409, message = "an existing item already exists", response = TBookBasic.class)})
    public ItooResult getBookBasicByISBN(@ApiParam(value = "note that needs to be added", required = true) @PathParam("isbn") String isbn, @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException, Exception {
        TBookBasic tbookbasic = bookService.findBookByIsbn(isbn);
        if (tbookbasic != null) {
            log.info("根据isbn抓取图书信息成功");
            return ItooResult.build("0000", "根据isbn抓取图书信息成功", tbookbasic);
        } else {
            log.error("添加失败");
            return ItooResult.build("1111", "根据isbn抓取图书信息失败");
        }
    }

    /**
     * @param isbn
     * @param securityContext
     * @return 扫描抓取图书基本信息(图书入库页面)-张婷-2017-11-2 14:52:22
     * @throws NotFoundException
     * @throws URISyntaxException
     * @throws Exception
     */
    @GET
    @Path("/getBasicIdByisbn/{isbn}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //用在方法上，说明方法的作用
    @ApiOperation(value = "通过isbn查询书名", notes = "通过isbn查询书名", response = BookViewModel.class, tags = "book")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = TBookBasic.class)
            ,
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = TBookBasic.class)
            ,
            @ApiResponse(code = 409, message = "an existing item already exists", response = TBookBasic.class)})
    public ItooResult getBasicIdByisbn(@ApiParam(value = "note that needs to be added", required = true) @PathParam("isbn") String isbn, @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException, Exception {
        List<TBookBasic> tbookbasic = bookService.getBasicIdByisbn(isbn);
        if (tbookbasic != null) {
            log.info("通过isbn查询书名成功");
            return ItooResult.build("0000", "通过isbn查询书名成功", tbookbasic);
        } else {
            log.error("通过isbn查询书名失败");
            return ItooResult.build("1111", "通过isbn查询书名失败");
        }
    }

//    getBasicIdByisbn

    /**
     * @param isbn
     * @param securityContext
     * @return 扫描直接抓取图书基本信息(不通过数据库)-张明慧-2018年1月13日20:42:49
     * @throws NotFoundException
     * @throws URISyntaxException
     * @throws Exception
     */
    @GET
    @Path("/getBookByISBN/{isbn}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //用在方法上，说明方法的作用
    @ApiOperation(value = "扫描直接抓取图书基本信息(不通过数据库)", notes = "扫描直接抓取图书基本信息(不通过数据库)", response = BookViewModel.class, tags = "book")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = TBookBasic.class)
            ,
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = TBookBasic.class)
            ,
            @ApiResponse(code = 409, message = "an existing item already exists", response = TBookBasic.class)})
    public ItooResult getBookByISBN(@ApiParam(value = "note that needs to be added", required = true) @PathParam("isbn") String isbn, @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException, Exception {
        TBookBasic tbookbasic = bookService.findgetBookByIsbn(isbn);
        if (tbookbasic != null) {
            log.info("根据isbn抓取图书信息成功");
            return ItooResult.build("0000", "根据isbn抓取图书信息成功", tbookbasic);
        } else {
            log.error("根据isbn抓取图书信息失败");
            return ItooResult.build("1111", "根据isbn抓取图书信息失败");
        }
    }


    /**
     * @param isbn
     * @return 根据ISBN抓取图书简介和目录并保存到数据库-张明慧-2018年1月13日20:45:05
     * @throws NotFoundException
     * @throws URISyntaxException
     * @throws Exception
     */
    @GET
    @Path("/getSearchBookByISBN/{isbn}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "根据ISBN抓取图书简介和目录并保存到数据库", notes = "根据ISBN抓取图书简介和目录并保存到数据库", response = BookViewModel.class, tags = "book")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = TBookBasic.class)
            ,
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = TBookBasic.class)
            ,
            @ApiResponse(code = 409, message = "an existing item already exists", response = TBookBasic.class)})
    public ItooResult getSearchBookByISBN(@ApiParam(value = "note that needs to be added", required = true) @PathParam("isbn") String isbn, @Context SecurityContext securityContext) throws NotFoundException, URISyntaxException, Exception {

        BookViewModel model = bookService.getSearchBookByISBN(isbn);
        if (model != null) {
            log.info("根据isbn抓取图书简介和目录成功");
            return ItooResult.build("0000", "根据isbn抓取图书简介和目录成功", model);
        } else {
            log.error("根据isbn抓取图书简介和目录失败");
            return ItooResult.build("1111", "根据isbn抓取图书简介和目录失败");
        }
    }

    /**
     * 批量删除Tbook表的信息，同时更改入库表的信息
     * 批量删除图书信息-张婷-2017-11-2 14:58:17
     *
     * @param bookIds
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @POST
    @Path("/deleteBooks/{bookIds}")
    @Produces({"application/json"})
    @ApiOperation(value = "批量删除图书", notes = "批量删除图书", response = BookViewModel.class, tags = "PC")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "delete succeed", response = void.class)
            ,
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,

            @ApiResponse(code = 404, message = "not found", response = void.class)})
    public ItooResult deleteBooks(@ApiParam(value = "book id to delete", required = true) @PathParam("bookIds") String[] bookIds,
                                  @Context SecurityContext securityContext) throws NotFoundException {

        boolean flag = false;

        bookIds = bookIds[0].split(",");

        flag = bookService.deleteBook(bookIds);

        if (flag == true) {
            log.info("批量删除图书成功！");
            return ItooResult.build("0000", "删除成功", flag);

        } else {
            log.info("批量删除图书失败！");
            return ItooResult.build("1111", "删除失败");
        }

    }

    /**
     * 批量删除图书（book，bookbasic，warehuse三表都删除）-王雅静-2017年12月3日16:30:30
     * 1
     *
     * @param bookIds
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @POST
    @Path("/deleteBasicBooks/{basicIds}")
    @Produces({"application/json"})
    @ApiOperation(value = "批量删除图书", notes = "批量删除图书", response = BookViewModel.class, tags = "PC")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "delete succeed", response = void.class)
            ,
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,

            @ApiResponse(code = 404, message = "not found", response = void.class)})
    public ItooResult deleteBasicBooks(@ApiParam(value = "book id to delete", required = true) @PathParam("basicIds") String[] basicIds,
                                       @Context SecurityContext securityContext) throws NotFoundException {

        boolean flag = false;

        basicIds = basicIds[0].split(",");
        for (int i = 0; i < basicIds.length; i++) {
            flag = bookService.deleteAllBook(basicIds[i]);
        }
        if (flag == true) {
            log.info("批量删除图书成功！");
            return ItooResult.build("0000", "删除成功", flag);

        } else {
            log.info("批量删除图书失败！");
            return ItooResult.build("1111", "删除失败");
        }

    }

    /**
     * 查询图书信息（单表操作）-张婷-2017-11-2 14:59:03
     *
     * @param bookId
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBook/{bookId}")
    @Produces({"application/json"})
    @ApiOperation(value = "查询图书信息（单表操作）", notes = "查询图书信息（单表操作） ", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得查询结果", response = TBook.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = TBook.class)})
    public ItooResult getBook(@ApiParam(value = "查询条件可以为空", required = true) @PathParam("bookId") String bookId,
                              @Context SecurityContext securityContext)
            throws NotFoundException {
        List<TBook> list = bookService.getBook(bookId);
        if (CollectionUtils.isNotEmpty(list)) {

            return ItooResult.build("0000", "根据图书ID查找图书成功", list);

        } else {
            return ItooResult.build("1111", "根据图书ID查找图书失败");
        }
    }


    /**
     * 更新bookBasic的信息
     * 更新图书信息-张婷-2017-11-2 15:00:07-----不修改位置信息的修改---王华伟添加注释--2018年5月7日17:47:48
     *
     * @param form
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @POST
    @Path("/updateBook")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "编辑图书信息--没有修改位置信息", notes = "编辑同种ISBN的图书的信息", response = void.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,
            @ApiResponse(code = 404, message = "not found", response = void.class)
            ,
            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult updateBook(@ApiParam(value = "提交更改的信息", required = true) BookViewModel form)
            throws NotFoundException, URISyntaxException {
        boolean flag = false;
        flag = bookService.updateBookBasic(form);
        if (flag == false) {
            log.info("更新图书失败！");
            return ItooResult.build("1111", "更新图书失败");
        } else {
            log.info("更新图书成功！");
            return ItooResult.build("0000", "图书信息更新成功", form);
        }
    }

    /**
     * 更新图书的物理位置---王华伟--2018年5月7日17:51:16
     *
     * @param form
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @POST
    @Path("/updateBookBasicBylocation")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "编辑图书信息--更新图书的物理位置", notes = "编辑同种ISBN的图书的信息", response = void.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,
            @ApiResponse(code = 404, message = "not found", response = void.class)
            ,
            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult updateBookBasicBylocation(@ApiParam(value = "提交更改的信息", required = true) BookViewModel form)
            throws NotFoundException, URISyntaxException {
        boolean flag = false;
        flag = bookService.updateBookBasicBylocation(form);
        if (flag == false) {
            log.info("更新图书失败！");
            return ItooResult.build("1111", "更新图书失败");
        } else {
            log.info("更新图书成功！");
            return ItooResult.build("0000", "图书信息更新成功", form);
        }
    }


    /**
     * 更新bookBasic的信息
     * 更新图书信息-张婷-2017-11-2 15:00:07
     *
     * @param form
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @POST
    @Path("/updateTBookInfo")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "编辑图书详情信息", notes = "编辑一本图书信息", response = void.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,
            @ApiResponse(code = 404, message = "not found", response = void.class)
            ,
            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult updateTBookInfo(@ApiParam(value = "提交更改的信息", required = true) BookViewModel form)
            throws NotFoundException, URISyntaxException {
        boolean flag = false;
        flag = bookService.updateBook(form);
        if (flag == false) {
            log.info("更新图书失败！");
            return ItooResult.build("1111", "更新图书失败");
        } else {
            log.info("更新图书成功！");
            return ItooResult.build("0000", "图书信息更新成功", form);
        }
    }

    /**
     * 根据条件进行模糊查询+田成荣+2017年10月12日14:57:41
     *
     * @param MoHu_str
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/findByConditions/{MoHu_str}/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据输入的作者,摘要,作品名称进行模糊查询", notes = "根据输入的作者,摘要,作品名称进行模糊查询", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "get book Succeed", response = BookViewModel.class),


            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult findByConditions(@ApiParam(value = "输入的作者，摘要，作品名称", required = true) @PathParam("MoHu_str") String MoHu_str, @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize,
                                       @Context SecurityContext securityContext)
            throws NotFoundException {
        PaginationEntity paginationEntity = bookService.findByConditions(MoHu_str, pageNum, pageSize);
        if (paginationEntity != null) {
            log.info("根据查询条件查询成功！");
            return ItooResult.build("0000", "根据查询条件查询成功", paginationEntity);

        } else {
            log.info("根据查询条件查询失败！");
            return ItooResult.build("1111", "根据查询条件失败");
        }
    }


    /**
     * 根据条件进行模糊查询+王雪芬+2018年5月13日14:55:39-手机端使用
     *
     * @param MoHu_str
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/findByConditionselect/{MoHu_str}/{MoHu_s}/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据输入的作者,摘要,作品名称进行模糊查询", notes = "根据输入的作者,摘要,作品名称进行模糊查询", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "get book Succeed", response = BookViewModel.class),
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult findByConditionselect(@ApiParam(value = "输入的作者，摘要，作品名称", required = true) @PathParam("MoHu_str") String MoHu_str, @PathParam("MoHu_s") String MoHu_s, @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize,
                                            @Context SecurityContext securityContext)
            throws NotFoundException {
        PaginationEntity paginationEntity = bookService.findByConditionselect(MoHu_str, MoHu_s, pageNum, pageSize);
        if (paginationEntity != null) {
            log.info("根据查询条件查询成功！");
            return ItooResult.build("0000", "根据查询条件查询成功", paginationEntity);
        } else {
            log.info("根据查询条件查询失败！");
            return ItooResult.build("1111", "根据查询条件失败");
        }
    }


    /**
     * 根据模糊条件查询信息 如果条件为空的话，前端传递 %20 添加location-王雅静-2018年5月3日17:54:06
     *
     * @param conditions
     * @param conditions
     * @param pageNum
     * @param pageSize
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/findTBookByConditions/{conditions}/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据输入的来源，书名，iabn号，索书号进行模糊查询", notes = "根据输入的来源，书名，iabn号，索书号进行模糊查询", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "get book Succeed", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult findTBookByConditions(@ApiParam(value = "输入的来源，书名，iabn号，索书号", required = true) @PathParam("conditions") String conditions, @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize,
                                            @Context SecurityContext securityContext)
            throws NotFoundException {
        PaginationEntity paginationEntity = bookService.findTBookByConditions(conditions, pageNum, pageSize);
        if (paginationEntity != null) {
            log.info("根据查询条件查询成功！");
            return ItooResult.build("0000", "根据查询条件查询成功", paginationEntity);

        } else {
            log.info("根据查询条件查询失败！");
            return ItooResult.build("1111", "根据查询条件失败");
        }
    }

    /**
     * 根据模糊条件查询信息 如果条件为空的话，前端传递 %20 添加location-王雅静-2018年5月3日17:54:06
     *
     * @param conditions
     * @param conditions
     * @param pageNum
     * @param pageSize
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/findTBookByIsbnAndLocation/{isbn}/{location}/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据isbn和location查询书籍信息", notes = "根据isbn和location查询书籍信息", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "get book Succeed", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult findTBookByIsbnAndLocation(@ApiParam(value = "isbn,locatoin,pageNum,pageSize", required = true) @PathParam("isbn") String isbn, @PathParam("location") String location, @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize,
                                                 @Context SecurityContext securityContext)
            throws NotFoundException {
        PaginationEntity paginationEntity = bookService.findTBookByIsbnAndLocation(isbn, location, pageNum, pageSize);
        if (paginationEntity != null) {
            log.info("根据查询条件查询成功！");
            return ItooResult.build("0000", "根据查询条件查询成功", paginationEntity);

        } else {
            log.info("根据查询条件查询失败！");
            return ItooResult.build("1111", "根据查询条件失败");
        }
    }


    /**
     * 根据条件进行模糊查询返回basic表中的ID+王雅静+2017年12月3日11:01:50
     *
     * @param MoHu_str
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/findByConditionsSum/{MoHu_str}/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据输入的作者,摘要,作品名称进行模糊查询", notes = "根据输入的作者,摘要,作品名称进行模糊查询", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "get book Succeed", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult findByConditionsSum(@ApiParam(value = "输入的作者，摘要，作品名称", required = true) @PathParam("MoHu_str") String MoHu_str, @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize,
                                          @Context SecurityContext securityContext)
            throws NotFoundException {
        PaginationEntity paginationEntity = bookService.findByConditionsSum(MoHu_str, pageNum, pageSize);
        if (paginationEntity != null) {
            log.info("根据查询条件查询成功！");
            return ItooResult.build("0000", "根据查询条件查询成功", paginationEntity);

        } else {
            log.info("根据查询条件查询失败！");
            return ItooResult.build("1111", "根据查询条件失败");
        }
    }

    /**
     * 猜你喜欢+田成荣+2017年10月12日14:57:41 1、从借阅表中根据userid查询最新一条bookid； 2、根据bookid查询booktypeid 3、根据booktypeid查询图书 如果没有借阅记录则直接查询图书类别表中一条记录
     *
     * @param User_Id
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getFavorite/{userId}/{pageNum}/{pageSize}")

    @Produces({"application/json"})
    @ApiOperation(value = "猜你喜欢", notes = "根据借阅记录或者最近上新推荐图书 ", response = TBorrowing.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得查询结果", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getFavorite(@ApiParam(value = "借阅记录里的图书分类或者是上新推荐图书", required = true) @PathParam("userId") String userId,
                                  @PathParam("pageNum") int pageNum, @PathParam("pageSize") int pageSize,
                                  @Context SecurityContext securityContext)
            throws NotFoundException {

        PaginationEntity paginationEntity = bookService.getBookFavorite(userId, pageNum, pageSize);

        if (paginationEntity.getListRows() != null && paginationEntity.getListRows().size() > 0) {
            log.info("猜你喜欢查询成功！");
            return ItooResult.build("0000", "猜你喜欢查询成功", paginationEntity);

        } else {
            log.info("猜你喜欢查询失败，请先添加图书分类！");
            return ItooResult.build("1111", "猜你喜欢查询失败，请先添加图书分类！");
        }

    }

    /**
     * 根据isbn查书+田成荣+2017年10月15日11:35:12
     *
     * @param isbn
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/findBookDetailsByIsbn/{isbn}")
    //指定将要返回给client端的数据标识类型为json
    @Produces({"application/json"})
    @ApiOperation(value = "点击某本书进入书的详情页/添加图书时根据isbn获得本书其他信息", notes = "点击某本书进入书的详情页/根据isbn查询图书详细信息 ", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得查询结果", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult findBookDetailsByIsbn(@ApiParam(value = "图书的ISBN编号", required = true) @PathParam("isbn") String isbn, @Context SecurityContext securityContext)
            throws NotFoundException {
        List<BookViewModel> list = bookService.findBookDetailsByIsbn(isbn);
        if (CollectionUtils.isNotEmpty(list)) {
            BookViewModel BookViewModel = (BookViewModel) list.get(0);
            log.info("根据ISBN查找图书成功");
            return ItooResult.build("0000", "根据ISBN查找图书成功", BookViewModel);

        } else {
            log.info("根据ISBN查找图书失败");
            return ItooResult.build("1111", "根据ISBN查找图书失败");
        }
    }


    /**
     * 根据图书类型查数书+田成荣2017年10月15日11:35:52
     *
     * @param bookType
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBookByBookType/{bookType}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据类型id查询图书", notes = "根根据类型id查询图书 ", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得查询结果", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getBookByBookType(@ApiParam(value = "图书类型", required = true) @PathParam("bookType") String bookType,
                                        @Context SecurityContext securityContext)
            throws NotFoundException {
        List<BookViewModel> list = TBookRepository.TypeIdgetBook(bookType);
        if (list != null && list.size() > 0) {
            log.info("根据图书类型id查找图书成功");
            return ItooResult.build("0000", "根据图书类型id查找图书成功", list);
        } else {
            log.info("根据图书类型id查找图书失败");
            return ItooResult.build("1111", "该类别下没有图书");
        }
    }


    /**
     * 根据父类别ID查询该类别下的所有图书 -武刚鹏-2017年11月15日13:14:53
     *
     * @param parentTypeId
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBookByParentTypeId/{parentTypeId}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据类型id查询图书", notes = "根根据类型id查询图书 ", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得查询结果", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getBookByParentTypeId(@ApiParam(value = "图书类型", required = true) @PathParam("parentTypeId") String parentTypeId,
                                            @Context SecurityContext securityContext)
            throws NotFoundException {
        try {
//            List<BookViewModel> list = bookService.getBookByParentTypeId(parentTypeId);
            //TODO jiexishibai
            List<BookViewModel> list = null;

            if (list != null && list.size() > 0) {
                log.info("根据图书类型id查找图书成功");
                return ItooResult.build("0000", "根据图书类型id查找图书成功", list);
            } else {
                log.info("根据图书类型id查找图书失败");
                return ItooResult.build("1111", "该类别下没有图书");
            }
        } catch (Exception e) {
            log.error("根据图书类型id查找图书失败", e);
            return ItooResult.build("1111", "该类别下没有图书");
        }

    }


    /**
     * 根据索书号查询图书信息-张婷-2017-11-5 09:22:34
     *
     * @param SearchNum
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBookBySearchNum/{SearchNum}")
    @Produces({"application/json"})
    @ApiOperation(value = "根据索书号查询图书信息", notes = "根据索书号查询图书信息 ", response = BookViewModel.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得查询结果", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getBookBysearchNum(@ApiParam(value = "图书类型", required = true) @PathParam("SearchNum") String SearchNum,
                                         @Context SecurityContext securityContext)
            throws NotFoundException, IOException, ParseException {
        List<BookViewModel> list = TBookRepository.getBookBySearchNum(SearchNum);
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.get(0).getpicture() != null) {
                if (!list.get(0).getpicture().contains("http")) {
                    list.get(0).setpicture(FastDfsUtils.getFastDfsIP() + list.get(0).getpicture());
                }
            }
            log.info("根据索书号查询图书信息成功 ");
            return ItooResult.build("0000", "根据索书号查询图书信息成功", list);
        } else {
            log.info("根据索书号查询图书信息失败");
            return ItooResult.build("1111", "根据索书号查询图书信息失败");
        }
    }


    /**
     * 查看一般书还有多个可进行借阅-王雪芬-2018年1月11日11:42---以及物理位置--2018年5月1日16:44:42
     *
     * @param use_count
     * @param securityContext
     * @return
     * @throws NotFoundException
     */

    @GET
    @Path("/bookcount/{isbn}/{location}")
    @Produces({"application/json"})
    @ApiOperation(value = "查看本书剩余数量以及物理位置", response = TWarehuse.class, responseContainer = "List", tags = "Android")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得查询结果", response = TWarehuse.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = TWarehuse.class)})
    public ItooResult getAllcount(@ApiParam(value = "本书剩余数量", required = true) @PathParam("isbn") String isbn, @PathParam("location") String location, @Context SecurityContext securityContext)
            throws NotFoundException {
        try {
            //用list接收Repository的值
            List<TWarehuse> list = bookService.bookUserCount(isbn, location);
            if (list != null && list.size() > 0) {
                log.info("根据图书isbn图书数量成功");
                return ItooResult.build("0000", "根据图书isbn图书数量查找成功", list);
            } else {
                log.info("根据图书isbn图书数量失败");
                return ItooResult.build("1111", "没有图书数量");
            }
        } catch (Exception e) {
            log.error("根据图书isbn图书数量失败", e);
            return ItooResult.build("1111", "没有图书数量");
        }

    }


    /**
     * 分页查出所有的书+田成荣+2017年10月15日11:36:51
     *
     * @param pageNum
     * @param pageSize
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/findAllBookByPagination/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "分页查出所有的书", notes = "分页查出所有的书 ", response = BookViewModel.class, responseContainer = "List", tags = "Android")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得所有图书", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult findAllBookByPagination(@io.swagger.annotations.ApiParam(value = "所有图书信息", required = true) @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize)
            throws NotFoundException {
        PaginationEntity paginationEntity = bookService.findAllBookByPagination(pageNum, pageSize);
        try {
            if (paginationEntity == null && paginationEntity.getListRows() == null && paginationEntity.getListRows()
                    .isEmpty
                            ()) {
                return ItooResult.build("1111", "当前没有可用记录", false);
            } else {
                return ItooResult.build("0000", "分页获取所有图书图书信息成功", paginationEntity);
            }
        } catch (Exception e) {
            log.error("分页查询图书失败！", e);
            return ItooResult.build("1111", "分页查询数据失败", false);

        }

    }

    /**
     * 分页查出所有的书（以basic_id标识）+王雅静+2017年12月3日10:36:41
     *
     * @param pageNum
     * @param pageSize
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getAllbookSum/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "分页查出所有的书", notes = "分页查出所有的书 ", response = BookViewModel.class, responseContainer = "List", tags = "Android")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得所有图书", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getAllbookSum(@io.swagger.annotations.ApiParam(value = "所有图书信息", required = true) @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize)
            throws NotFoundException {
        PaginationEntity paginationEntity = bookService.findAllBookByPaginationSum(pageNum, pageSize);
        try {
            if (paginationEntity == null && paginationEntity.getListRows() == null && paginationEntity.getListRows()
                    .isEmpty
                            ()) {
                return ItooResult.build("1111", "当前没有可用记录", false);
            } else {
                return ItooResult.build("0000", "分页获取所有图书图书信息成功", paginationEntity);
            }
        } catch (Exception e) {
            log.error("分页查询图书失败！", e);
            return ItooResult.build("1111", "分页查询数据失败", false);

        }
    }


    /**
     * 下载模板-郭晶晶-2017-11-4 09:55:09
     *
     * @param requst
     * @param response
     * @return
     */
    @GET
    @Path("/downExcel")
    @ApiOperation(value = "下载模板", response = BookViewModel.class, responseContainer = "List", tags = "Android")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult downExcel(@Context HttpServletRequest requst, @Context HttpServletResponse response) throws ParseException {
        Map<Serializable, Serializable> map = new HashMap<>();
        //sheet的名字
        map.put("sheetName", "图书信息");
        map.put("columns", new String[]{"isbn", "bid", "bookName", "author", "content", "summary", "totalpage", "location", "typeName",
                "owner", "usenum", "publishplace", "publishtime", "remark"});
        //导出的表格标题
        map.put("title", "图书信息");
        List<BookViewModel> bookList = new ArrayList<>();
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setIsbn("9787562936763");
        bookViewModel.setBookName("混凝土结构");
        bookViewModel.setAuthor("叶列平");
        bookViewModel.setContent("0绪论 \n"
                + "　　0.1钢筋混凝土的一般概念 \n"
                + "　　0.2钢筋混凝土的主要优缺点 \n"
                + "　　0.3钢筋混凝土的应用和发展简况 \n"
                + "　　0.4学习本课程需要注意的问题 ……");
        bookViewModel.setsummary("《普通高等学校教材:混凝土结构(第4版)》除可作为高等学校土建类专业教材外，还可作为土木建筑工程技术人员的参考书。");
        bookViewModel.setTotalpage(358);
        bookViewModel.setLocation("万达");
        //bookViewModel.setLanguage("简体中文");

        bookViewModel.setPublishtime("2012年12月");
        bookViewModel.settypeName("文学类");
        bookViewModel.setowner("购买");
        //bookViewModel.setorigin("购买");
        bookViewModel.setremark("版权为建工学院所有");
        bookViewModel.setpublishPlace("武汉理工大学出版社");
        bookViewModel.setuseNum(1);
        bookViewModel.setBid(51.0);

        bookList.add(bookViewModel);
        map.put("dataList", (Serializable) bookList);

        try {
            ExcelUtil.exportExcel(map, response);
            log.info("下载成功！");
            return ItooResult.build("0000", "下载成功");

        } catch (Exception e) {
            log.info("下载失败！");
            return ItooResult.build("1111", "下载失败");
        }
    }

    public ItooResult importExcel(@Context HttpServletResponse response) {

        return ItooResult.build("", "");
    }

    /**
     * 扫描isbn图书获得图书信息-张婷-2017-11-4 09:56:27
     *
     * @param isbn
     * @param securityContext
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     * @throws Exception
     */
    @GET
    @Path("/getBookNameByISBN/{isbn}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //用在方法上，说明方法的作用
    @ApiOperation(value = " 扫描isbn图书获得图书信息（添加图书使用）--暂未使用", notes = " 扫描isbn图书抓取图书信息", response = BookViewModel.class, tags = "book")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = TBookBasic.class)
            ,

            @ApiResponse(code = 400, message = "invalid input, object invalid", response = TBookBasic.class)
            ,

            @ApiResponse(code = 409, message = "an existing item already exists", response = TBookBasic.class)})

    public ItooResult getBookNameByISBN(@ApiParam(value = "note that needs to be added", required = true) @PathParam("isbn") String isbn, @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException, Exception {
        List<TBookBasic> list = bookService.getBookBasicByISBN(isbn);
        if (CollectionUtils.isNotEmpty(list)) {

            return ItooResult.build("0000", "扫描图书获取图书信息成功", list);
        } else {
            return ItooResult.build("1111", "扫描图书获取图书信息失败");
        }
    }

    /**
     * 图书入库页接口-根据isbn获得索书号SearchNum
     *
     * @param isbn
     * @param securityContext
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     * @throws Exception
     */
    @GET
    @Path("/getSearchSumByISBN/{isbn}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //用在方法上，说明方法的作用
    @ApiOperation(value = "根据isbn获得索书号SearchNum（图书入库和添加图书使用）", notes = "根据isbn获得索书号SearchNum", response = BookViewModel.class, tags = "book")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "invalid input, object invalid", response = BookViewModel.class)
            ,

            @ApiResponse(code = 409, message = "an existing item already exists", response = BookViewModel.class)})

    public ItooResult getSearchSumByISBN(@ApiParam(value = "note that needs to be added", required = true) @PathParam("isbn") String isbn, @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException, Exception {
        //如果页面上尚未输入isbn号，提示必须输入
        if (isbn == null) {
            return ItooResult.build("1111", "请输入isbn号！");
        }
        //如果图书不是初次入库，则获取该isbn对应的最大索书号
        List<BookViewModel> list = bookService.getSearchSumByISBN(isbn);
        if (list != null && list.size() > 0) {
            String searchNum = list.get(0).getSearchNum();
            String prefixSearchNum = searchNum.substring(0, searchNum.length() - 2);
            String suffix = searchNum.substring(searchNum.length() - 2, searchNum.length());
            String viewSearchNum = "";
            Long number = Long.parseLong(suffix) + 1;
            if (number < 10) {
                viewSearchNum = prefixSearchNum + "0" + number;
            } else {
                viewSearchNum = prefixSearchNum + number;
            }
            return ItooResult.build("0000", "根据isbn获取索书号成功", viewSearchNum);
        } else {
            //如果图书第一次入库，索书号为isbn+01
            String SearchNuma = isbn + "01";
            return ItooResult.build("0000", "根据isbn获取索书号成功", SearchNuma);
        }
    }

    /**
     * 上传图片-张婷-2017-11-4 10:00:56
     *
     * @param request
     * @param securityContext
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     * @throws Exception
     */
    @POST
    @Path("/uploadPicture")
    @Consumes({"multipart/form-data"})
    @Produces({"application/json"})
    //用在方法上，说明方法的作用
    @ApiOperation(value = "图片上传（入库页面使用）", notes = "图片上传", response = BookViewModel.class, tags = "book")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "invalid input, object invalid", response = BookViewModel.class)
            ,

            @ApiResponse(code = 409, message = "an existing item already exists", response = BookViewModel.class)})
    public ItooResult uploadPicture(@ApiParam(value = "note that needs to be added", required = true) @Context HttpServletRequest request, @Context SecurityContext securityContext) throws Exception {
        //调用文件转字节流方法

        InputStream is = request.getInputStream();
        String fileName = UploadFileUtil.getFileName(is);//获取上传文件的名称
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);//扩展名
        ByteArrayOutputStream os = new ByteArrayOutputStream();//字节数组输出流（ByteArrayOutputStream）
        UploadFileUtil.getFileOutPutStream(is, os);//ByteArrayOutputStream继承与OutputStream
        String url = BookNetInfoUtil.saveImageToFastDfs(os.toByteArray(), extName);//传到服务器

        //关闭流
        is.close();
        os.close();

        //结果回显
        if (url != null) {

            String ip = FastDfsUtils.getFastDfsIP();
            url = ip + url;
            log.info("图片上传成功！");
            return ItooResult.build("0000", "图片上传成功", url);
        } else {
            log.info("图片上传失败！");
            return ItooResult.build("1111", "图片上传失败");
        }
    }


    /**
     * 导出图书信息-田成荣-2017-11-4 10:01:53
     *
     * @param response
     * @return
     * @throws NotFoundException
     * @throws Exception
     */
    @GET
    @Path("/bookExportBookGet/{MoHu_str}/{isbns}")
    @ApiOperation(value = "导出图书记录", notes = "Multiple status values can be provided with comma separated strings", response = BookViewModel.class, responseContainer = "List", tags = "PC")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = Boolean.class)
            ,

            @ApiResponse(code = 400, message = "bad input parameter", response = Boolean.class)})

    public ItooResult bookExportBookGet(@Context HttpServletResponse response, @PathParam("MoHu_str") String MoHu_str, @PathParam("isbns") String[] isbns) throws NotFoundException, Exception {
        Map<Serializable, Serializable> map = new HashMap<>();
        //获取要导出的全部数据
        //List<BookViewModel> booklist = bookService.findAllBook();

        isbns = isbns[0].split(",");
        List<String> book = new ArrayList<String>();

        List<BookViewModel> bookList = null;
        //选中特定数据导出。。。
        if (!isbns[0].trim().isEmpty() && isbns != null) {
            for (int i = 0; i < isbns.length; i++) {
                book.add(i, isbns[i]);
            }
            bookList = bookService.findByIsbns(book);
        } else {
            //模糊查询导出。
            if (!MoHu_str.trim().isEmpty() && MoHu_str != null) {
                bookList = bookService.findByConditionsExport(MoHu_str);
            } else {//导出全部。
                bookList = bookService.findAllBook();
            }
        }

        map.put("sheetName", "图书信息");
        //要导出的字段
        map.put("columns", new String[]{"searchNum", "isbn", "bookName", "author", "content", "summary", "totalpage", "languagea", "publishtime", "typeName", "owner", "remark", "usenum", "publishplace", "picture", "origin", "bid", "primCost", "location"});
        map.put("title", "图书信息");
        map.put("dataList", (Serializable) bookList);

        org.jplus.hyberbin.excel.utils.ExcelUtil.exportExcel(map, response);
        log.info("导出成功！");
        return ItooResult.build("0000", "导出成功");
    }


    /**
     * 导入图书信息-田成荣-2017-11-4 10:01:23
     *
     * @param response
     * @param request
     * @param fileName
     * @return
     * @throws NotFoundException
     * @throws Exception
     */
    @POST
    @Path("/importBook")
    @Consumes({"multipart/form-data"})
    @Produces({"application/json"})
    @ApiOperation(value = "通过excel导入图书信息", notes = "Adds items to the "
            + "system", response = void.class, tags = "PC")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = void.class)
            ,

            @ApiResponse(code = 400, message = "bad input parameter", response = void.class)})
    public ItooResult importBook(@Context HttpServletResponse response, @Context HttpServletRequest request) throws NotFoundException, Exception {

        boolean flag;
        try {
            String filePath = UploadFileUtil.upload(response, request);
            Map<Serializable, Serializable> map = new HashMap<>();
            map.put("sheetName", "图书信息");
            map.put("Class", BookViewModel.class);
            Map<Serializable, Serializable> typeMap = new HashMap<>();
            Map<Serializable, Serializable> dictDataMap = new HashMap<>();
            List<TBookAndType> list = bookTypeService.findAllSonType();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    typeMap.put(list.get(i).getId(), list.get(i).getName());
                }
            }
            dictDataMap.put("typeName", (Serializable) typeMap);
            //获得Excel中数据
            List<BookViewModel> bookList = ExcelUtil.importExcel(filePath, response, map, dictDataMap);
            //当导入数据不为空时，循环调用添加方法
            if (bookList == null || bookList.size() <= 0) {
                return ItooResult.build("1111", "导入失败，请检查类别", false);
            }
            bookService.importBookList(bookList);
            return ItooResult.build("0000", "导入成功", true);
        } catch (Exception e) {
            flag = false;
            log.error("图书信息导入失败", e);
            return ItooResult.build("1111", "导入失败，出版日期为文本格式", false);
        }


    }


    /**
     * 根据索书号获得图书信息
     */
    @GET
    @Path("/getHotBook")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //用在方法上，说明方法的作用
    @ApiOperation(value = "", notes = "获取最热图书信息", response = hotBookViewModel.class, tags = "book")
    //表示一组响应

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = hotBookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "invalid input, object invalid", response = BookViewModel.class)
            ,

            @ApiResponse(code = 409, message = "an existing item already exists", response = BookViewModel.class)})

    public ItooResult getHotBook(@ApiParam(value = "note that needs to be added", required = true) @Context SecurityContext securityContext)
            throws NotFoundException, URISyntaxException, Exception {
        List<hotBookViewModel> list = bookService.getHotBook();
        if (list != null && list.size() > 0) {
            log.info("获取最" + "热图书成功");
            return ItooResult.build("0000", "获取最热图书成功", list);
        } else {
            log.info("获取最热图书失败");
            return ItooResult.build("1111", "获取最热图书失败");
        }
    }

    /**
     * 根据索书号获得图书信息
     *
     * @param pageNum
     * @param pageSize
     */
    @GET
    @Path("/getAllDataAndConSum/{pageNum}/{pageSize}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "", notes = "批量修改图书信息", response = hotBookViewModel.class, tags = "book")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = hotBookViewModel.class),
            @ApiResponse(code = 400, message = "invalid input, object invalid", response = BookViewModel.class),
            @ApiResponse(code = 409, message = "an existing item already exists", response = BookViewModel.class)})
    public ItooResult getAllDataAndConSum(@DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize) throws NotFoundException, URISyntaxException, Exception {
        bookService.getAllDataAndConSum(pageNum, pageSize);
        return ItooResult.build("0000", "修改成功");
    }

    @GET
    @Path("/getAllLocation/{pageNum}/{pageSize}")
    @Produces({"application/json"})
    @ApiOperation(value = "图书管理页面加载图书位置", notes = "查找所有的图书位置分类 ", response = TBookBasic.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得所有位置分类", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getAllbookType(@io.swagger.annotations.ApiParam(value = "图书位置分类", required = true) @DefaultValue("1") @PathParam("pageNum") int pageNum, @DefaultValue("10") @PathParam("pageSize") int pageSize,
                                     @Context SecurityContext securityContext)
            throws NotFoundException {
        List<BookViewModel> list = bookService.findAllbookLocation(pageNum, pageSize);
        if (list != null) {
            log.info("查找所有的图书位置分类成功");
            return ItooResult.build("0000", "查找所有的图图书位置分类成功", list);
        } else {
            log.error("查找所有的图书位置分类失败");
            return ItooResult.build("1111", "查找所有的图书位置分类失败");
        }
    }

    @GET
    @Path("/getCheckInfo/{location}")
    @Produces({"application/json"})
    @ApiOperation(value = "盘点开始", notes = "盘点开始 ", response = TBookBasic.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查找所有盘点信息", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getCheckInfo(@io.swagger.annotations.ApiParam(value = "图书位置", required = true) @PathParam("location") String location,
                                   @Context SecurityContext securityContext)
            throws NotFoundException {
        List<BookViewModel> list = bookService.getCheckInfo(location);
        if (list != null ) {
            log.info("查找所有盘点信息成功");
            return ItooResult.build("0000", "查找所有盘点信息成功", list);
        } else {
            log.error("查找所有盘点信息失败");
            return ItooResult.build("1111", "查找所有盘点信息失败");
        }
    }

    @GET
    @Path("/Checking/{isbn}/{location}")
    @Produces({"application/json"})
    @ApiOperation(value = "盘点中", notes = "盘点中 ", response = TBookBasic.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "盘点信息更新", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getChecking(@io.swagger.annotations.ApiParam(value = "标识号", required = true) @PathParam("isbn") String isbn,@PathParam("location") String location,
                                   @Context SecurityContext securityContext)
            throws NotFoundException {


        List<BookViewModel> list = bookService.getChecking(isbn,location);
        if (list != null ) {
            log.info("盘点信息更新成功");
            return ItooResult.build("0000", "盘点信息更新成功", list);
        } else {
            log.error("盘点信息更新失败");
            return ItooResult.build("1111", "盘点信息更新失败");
        }
    }


    @GET
    @Path("/Checkend/{location}")
    @Produces({"application/json"})
    @ApiOperation(value = "盘点结束", notes = "盘点结束 ", response = TBookBasic.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "盘点信息结束", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getCheckend(@io.swagger.annotations.ApiParam(value = "标识号", required = true) @PathParam("location") String location,
                                  @Context SecurityContext securityContext)
            throws NotFoundException {
        List<BookViewModel> list = bookService.getCheckend(location);
        if (list != null ) {
            log.info("盘点信息结束成功");
            return ItooResult.build("0000", "盘点信息结束成功", list);
        } else {
            log.error("盘点信息结束失败");
            return ItooResult.build("1111", "盘点信息结束失败");
        }
    }

//    checkResult

    @GET
    @Path("/checkResult/{location}")
    @Produces({"application/json"})
    @ApiOperation(value = "查询盘点结果不为0", notes = "查询盘点结果不为0 ", response = TBookBasic.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询盘点结果不为0", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult checkResult(@io.swagger.annotations.ApiParam(value = "书籍位置", required = true) @PathParam("location") String location,
                                  @Context SecurityContext securityContext)
            throws NotFoundException {
        List<BookViewModel> list = bookService.checkResult(location);
        if (list != null ) {
            log.info("查询盘点结果不为0成功");
            return ItooResult.build("0000", "查询盘点结果不为0成功", list);
        } else {
            log.error("查询盘点结果不为0失败");
            return ItooResult.build("1111", "查询盘点结果不为0失败");
        }
    }


    /**
     * 导出
     *
     * @param response
     * @return
     * @throws NotFoundException
     * @throws Exception
     */
    @GET
    @Path("/exportCheck/{location}")
    @Produces({"application/json"})
    @ApiOperation(value = "导出", notes = "导出记录", response = void.class, tags = "PC")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "导出成功", response = void.class),
            @ApiResponse(code = 400, message = "导出失败", response = void.class)})

    public ItooResult exportCheck(@Context HttpServletResponse response,@io.swagger.annotations.ApiParam(value = "书籍位置", required = true) @PathParam("location") String location) throws NotFoundException, Exception {

        Map<Serializable, Serializable> map = new HashMap<>();
        //sheet的名字
        map.put("sheetName", "盘点信息");
        //要导出的字段
        map.put("columns", new String[]{"isbn",
                "bookName", "location",
                "usenum", "addcount", "ischeck","result","location","updatetime"});
        //导出的表格标题
        map.put("title", "盘点信息");
        //getAll()找到导出的数据
        map.put("dataList", (Serializable) bookService.checkResult(location));
        try {
            ExcelUtil.exportExcel(map, response);
            return ItooResult.build("0000", "导出成功", true);
        } catch (Exception e) {
            log.error("盘点信息导出失败", e);
            return ItooResult.build("1111", "导出失败", false);
        }

    }
    /**
     * 查看没有图片的书的书名-电脑-王雪芬-2018年5月15日11:49:35
     *
     * @param securityContext
     * @return
     * @throws NotFoundException
     */

    @GET
    @Path("/getBookinfoName")
    @Produces({"application/json"})
    @ApiOperation(value = "图书管理页面显示图书名字", notes = "查找所有的图书没有图片的图书名字 ", response = TBookBasic.class, responseContainer = "List", tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获得没有图片的图书名字 ", response = BookViewModel.class)
            ,
            @ApiResponse(code = 400, message = "Not Found", response = BookViewModel.class)})
    public ItooResult getBookinfoName(@io.swagger.annotations.ApiParam(value = "图书名字", required = true) @Context SecurityContext securityContext)
            throws NotFoundException {
        List<TBookBasic> list = bookService.getBookinfoNameService();
        if (list != null) {
            log.info("查找所有没有图片的图书名字成功");
            return ItooResult.build("0000", "查找所有的没有图片的图书名字成功", list);
        } else {
            log.error("查找所有没有图片的图书名字分类失败");
            return ItooResult.build("1111", "查找所有没有图片的图书名字失败");
        }
    }

    /**
     * 查看该isbn是否有图片-电脑-王雪芬-2018年5月15日11:49:35
     *
     * @param securityContext
     * @return
     * @throws NotFoundException
     */
    @GET
    @Path("/getBookinfopicture/{isbn}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //用在方法上，说明方法的作用
    @ApiOperation(value = "根据isbn获得索书号SearchNum（图书入库和添加图书使用）", notes = "根据isbn获得索书号SearchNum", response = BookViewModel.class, tags = "book")
    //表示一组响应
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created", response = BookViewModel.class)
            ,

            @ApiResponse(code = 400, message = "invalid input, object invalid", response = BookViewModel.class)
            ,

            @ApiResponse(code = 409, message = "an existing item already exists", response = BookViewModel.class)})

    public ItooResult getBookinfopicture(@ApiParam(value = "note that needs to be added", required = true) @PathParam("isbn") String isbn, @Context SecurityContext securityContext)
            throws NotFoundException {
        List<TBookBasic> list = bookService.getBookinfopicture(isbn);
        if (list.get(0).getPicture() != null) {
            log.info("查找isbn图片");
            return ItooResult.build("0000", "查找isbn图片成功", list);
        } else {
            log.error("查找isbn图片失败");
            return ItooResult.build("1111", "查找isbn图片失败");
        }
    }

    /**
     * 更新bookBasic图片的信息
     * 更新图书图片的信息-王雪芬-2018年5月15日14:26:38
     *
     * @param form
     * @return
     * @throws NotFoundException
     * @throws URISyntaxException
     */
    @POST
    @Path("/updateTBookpicture")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "编辑图书详情图片", notes = "编辑图书详情图片", response = BookViewModel.class, tags = "Admin")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class)
            ,

            @ApiResponse(code = 404, message = "not found", response = void.class)
            ,

            @ApiResponse(code = 405, message = "Validation exception", response = void.class)})
    public ItooResult updateTBookpicture(@ApiParam(value = "", required = true) BookViewModel bookViewModel,
                                         @Context SecurityContext securityContext) throws NotFoundException {

        boolean flag = bookService.updateBookpic(bookViewModel);
        if (flag == true) {
            log.info("更新图片成功");
            return ItooResult.build("0000", "更新成功", flag);
        } else {
            log.error("更新图片失败");
            return ItooResult.build("1111", "更新失败");
        }
    }

}


