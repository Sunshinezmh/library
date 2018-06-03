package com.dmsdbj.library.service;

import com.dmsdbj.library.app.util.BookNetInfoUtil;
import com.dmsdbj.library.app.util.FastDfsUtils;
import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.entity.*;
import com.dmsdbj.library.repository.*;
import com.dmsdbj.library.viewmodel.BookViewModel;
import com.dmsdbj.library.viewmodel.hotBookViewModel;
import com.dmsdbj.library.viewmodel.Borrowing;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

import org.apache.velocity.runtime.directive.Foreach;
import org.slf4j.Logger;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-09-24T17:35:42.344+08:00")
public class BookService {

    @Inject
    private Logger log;
    @Inject
    private TBookRepository TBookRepository;

    @Inject
    private TBookBasicRepository TBookBasicRepository;
    @Inject
    private TBorrowingRepository TBorrowingRepository;
    @Inject
    private TWarehuseRepository TWarehuseRepository;
    @Inject
    private BookTypeService bookTypeService;
    @Inject
    private TBookAndTypeRepository TbookAndTypeRespository;
    //@Resource
    //private DataSourceTransactionManager transactionManager;


    Date createTime = new Timestamp(System.currentTimeMillis());
    //根据bookid获得tbook单表信息-张婷-2017年11月4日10:33:47

    public List<TBook> getBook(String bookId) {
        return TBookRepository.getBook(bookId);
    }

    /**
     * 扫描图书抓取图书基本信息-张婷-2017年11月4日10:34:18
     **/
    public List<TBookBasic> getBookBasicByISBN(String isbn) {
        return TBookRepository.getBookBasicByISBN(isbn);
    }


    public List<TBook> getAllBook() {
        return TBookRepository.getAllBook();
    }

    public List<TBookAndType> getAllBooktype(String typeId) {
        return TBookRepository.getAllBooktype(typeId);
    }

    public List<BookViewModel> getBookByISBN(String serchNum) {
        return TBookRepository.getBookBySearchNum(serchNum);
    }

    public List<TBookBasic> getBookBasicById(String Id) {
        return TBookRepository.getBookBasicById(Id);
    }

    /**
     * 图书分页查询 -武刚鹏 -21点43分
     **/
    public PaginationEntity findAllBookByPagination(int pageNum, int pageSize) {
        PaginationEntity paginationEntity = new PaginationEntity();
        List<BookViewModel> list = TBookRepository.findAllBookByPagination(pageNum, pageSize);
        paginationEntity.setListRows(list);
        int total = TBookRepository.findBookCount();
        paginationEntity.setTotalNum(total);
        paginationEntity.setPageNum(pageNum);
        paginationEntity.setPageSize(pageSize);
        return paginationEntity;
    }

    /**
     * 图书详情页面 查询Book表 -王雅静 -2017年12月3日10:43:35
     **/
    public PaginationEntity findAllBookByPaginationSum(int pageNum, int pageSize) {

        PaginationEntity paginationEntity = new PaginationEntity();
        List<BookViewModel> list = TBookRepository.findAllBookByPaginationSum(pageNum, pageSize);
        paginationEntity.setListRows(list);
        int total = TBookRepository.findBookCount();
        paginationEntity.setTotalNum(total);
        paginationEntity.setPageNum(pageNum);
        paginationEntity.setPageSize(pageSize);
        return paginationEntity;
    }


    /**
     * 查询所有图书信息-田成荣-2017-11-4 10:34:35
     **/
    public List<BookViewModel> findAllBook() {
        List<BookViewModel> list = TBookRepository.findAllBook();
        return list;
    }/**
     * 查询所有图书basic信息-田成荣-2018年5月6日09:55:41
     **/
    public List<TBookBasic> getBasicIdByisbn(String isbn) {
        List<TBookBasic> list = TBookRepository.getBasicIdByisbn(isbn);
        return list;
    }

    /**
     * 根据图书类型id查找图书-田成荣-2017-11-4 10:35:23
     **/

    public List<BookViewModel> TypeIdgetBook(String bookType) {
        return TBookRepository.TypeIdgetBook(bookType);
    }

    /**
     * 分月查询
     *
     * @param bookType
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PaginationEntity getBookByTypeIdGroupByIsbnByPagination(String bookType, Integer pageNum, Integer pageSize) {
        PaginationEntity paginationEntity = new PaginationEntity();
        List<BookViewModel> list = TBookRepository.getBookByTypeIdGroupByIsbn(bookType, pageNum, pageSize);
        paginationEntity.setListRows(list);
        paginationEntity.setPageSize(pageSize);
        paginationEntity.setPageNum(pageNum);
        int total = TBookRepository.bookCountByTypeId(bookType);
        paginationEntity.setTotalNum(total);
        return paginationEntity;
    }

    /**
     * 批量删除图书-张婷-2017-11-3 22:07:09
     * 删除Book表的信息
     *
     * @param bookId
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean deleteBook(String[] bookIds) {
        if (bookIds == null || bookIds.length == 0) {
            return false;
        }
        for (int i = 0; i < bookIds.length; i++) {
            String bookId = bookIds[i];
            TWarehuse warehuse = new TWarehuse();
            //删除book表的信息
            TBook book = TBookRepository.find(bookId);
            book.setIsDelete(1);
            book.setCreateTime(createTime);
            book.setUpdateTime(createTime);
            TBookRepository.edit(book);
            //当入库表中图书可用量为0的时候再删除bookbasic表的信息
            //获得bookbasicId
            String bookBasicId = book.getBasicId();
            TBookBasic bookbasic = TBookBasicRepository.find(bookBasicId);
            if (bookbasic != null) {
                String isbn = bookbasic.getIsbn();
                String location=bookbasic.getLocation();
                warehuse = TWarehuseRepository.getUseCountBylocation(isbn,location).get(0);
                if (warehuse != null) {
                    Integer useNum = warehuse.getuseNum();
                    //数量不能为负数
                    if (useNum > 0) {
                        warehuse.setuseNum(warehuse.getuseNum() - 1);
                    } else {
                        warehuse.setuseNum(0);
                    }
                    Integer wareNum = warehuse.getWareCount();
                    if (wareNum > 0) {
                        warehuse.setWareCount(warehuse.getWareCount() - 1);
                    } else {
                        warehuse.setWareCount(0);
                    }
                    warehuse.setCreateTime(createTime);
                    warehuse.setUpdateTime(createTime);
                    TWarehuseRepository.edit(warehuse);
                }
            }


        }
        return true;
    }

    /**
     * 批量删除图书-王雅静-2017年12月3日16:35:56
     *
     * @param basicId
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean deleteAllBook(String basicId) {
        TWarehuse warehuse = new TWarehuse();
        TBookBasic bookbasic = TBookBasicRepository.find(basicId);
        String isbn = bookbasic.getIsbn();
        String location=bookbasic.getLocation();
        //根据ISBN获取warehuse表的ID
        Boolean warehuseResult = TWarehuseRepository.deleteWarehuse(isbn,location);
        if (warehuseResult == true) {
            //根据basicID查询bookID，然后删除
            Boolean book = TBookRepository.deleteBookByBasicId(basicId);
            if (book == true) {
                //删除basic
                if (bookbasic != null) {
                    bookbasic.setIsDelete(1);
                    bookbasic.setCreateTime(createTime);
                    bookbasic.setUpdateTime(createTime);
                    TBookBasicRepository.edit(bookbasic);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * 武刚鹏我需改-2017年12月3日11:23:12
     * 图书入库-张婷-2017-11-4 10:03:07
     *
     * @param form
     */
    //添加
    @Transactional(rollbackOn = Exception.class)
    public void addBookInfo(BookViewModel form) {
        //对isbn去除“-” 和去除 “.”
        form.setIsbn(form.getIsbn().replace("-", "").replace(".", "").replace("/", ""));
        //然后判断前端是否传递过来索书号
        if (form.getSearchNum() == null) {
            List<BookViewModel> list = getSearchSumByISBN(form.getIsbn());
            //索书号
            String SearchNum = "";
            if (list != null && list.size() > 0) {
                SearchNum = list.get(0).getSearchNum();
                int length = (SearchNum).length();
                //获得索书号的数字部分
                String prefixSearchNum = (SearchNum).substring(0, length - 2);
                //获得索书号的非数字部分
                String suffixSearchNum = (SearchNum).substring(length - 2, length);
                int number = Integer.parseInt(suffixSearchNum) + 1;
                if (number < 10) {
                    SearchNum = prefixSearchNum + 0 + number;
                } else {
                    SearchNum = prefixSearchNum + number;
                }
            } else {
                //如果图书第一次入库，索书号为isbn+01
                SearchNum = form.getIsbn() + "01";
            }
            form.setSearchNum(SearchNum);
        }
        int i;
        //添加入库表信息
        addTWarehuse(form);
        //添加bookbasic表信息
        TBookBasic tBookBasic = addBookBasic(form);
        //根据isbn查询bookbasic表，如果该书已经存在则不插入bookbasic表，只更新updatetime
//        List<TBookBasic> listBasic = TBookRepository.getBasicIdByisbn(form.getIsbn());
        List<TBookBasic> listBasic = TBookRepository.getBookbasicLocation(form.getIsbn(),form.getLocation());

        //如果该书已经存在，则不录入bookbasic表
        if (listBasic != null && listBasic.size() > 0) {
            listBasic.get(0).setUpdateTime(createTime);
            tBookBasic.setId(listBasic.get(0).getId());
            TBookBasicRepository.edit(listBasic.get(0));
        } else
        //如果该书初次录入，则插入表
        {
            TBookBasicRepository.create(tBookBasic);
        }
        //根据入库数量循环添加book表记录
        //截取索书号的数字部分，索书号=isbn+数字
        int length = (form.getSearchNum()).length();
        //截取索书号的后两位
        Integer num = Integer.parseInt((form.getSearchNum()).substring(length - 2, length));
        for (i = 0; i < form.getuseNum(); i++) {
            //添加，传入基本的书籍信息，TBookBasic的basic_id，索书号的后两位
            addTbook(form, i, tBookBasic.getId(), num + i);
        }
    }

    /**
     * 添加book表记录-张婷-2017-11-4 10:04:30
     *
     * @param BookViewModel
     * @param i             循环变量
     * @param bookBasicId
     * @throws java.lang.Exception
     */
    public void addTbook(BookViewModel bookViewModel, int i, String bookBasicId, Integer num) {
        String SearchNum = "";
        TBook book = new TBook();
        //添加book表的信息
        book.setId(UuidUtils.base58Uuid());
        book.setBasicId(bookBasicId);
        book.setOrigin(bookViewModel.getorigin());
        book.setStatus(1);
        book.setPublishTime(bookViewModel.getPublishtime());
        book.setTotalPage(bookViewModel.getTotalpage());
        String typeName = bookViewModel.gettypeName();
        List<TBookAndType> list = bookTypeService.findTypeIdByTypeName(typeName);
        if (list != null && list.size() > 0) {
            String typeID = list.get(0).getId();
            book.setTypeId(typeID);
            book.setOwner(bookViewModel.getowner());
            book.setCreateTime(createTime);
            book.setUpdateTime(createTime);
            book.setIsDelete(0);
            final int endValue = 10;
            //noNum 除了后两位的索书号的前面
            String noNum = (bookViewModel.getSearchNum()).substring(0, bookViewModel.getSearchNum().length() - 2);
            //要加的数小于10，要拼0
            if (num < endValue) {
                //获得原数据的非数字部分
                //拼接索书号=字母+0+数字部分；
                SearchNum = noNum + 0 + num;
            } else {
                //isbn部分和数字拼接
                SearchNum = noNum + num;
            }
            book.setSearchNum(SearchNum);
            book.setRemark(bookViewModel.getremark());
            book.setoperator(bookViewModel.getOperator());
            book.setBookShelfId("");
            TBookRepository.create(book);
        }
    }

    /**
     * 添加bookbasic表信息-张婷-2017-11-4 10:05:07
     *
     * @param form
     * @return
     */
    public TBookBasic addBookBasic(BookViewModel form) {
        TBookBasic bookbasic = new TBookBasic();
        //添加bookbasic表的信息
        bookbasic.setId(UuidUtils.base58Uuid());
        bookbasic.setsummary(form.getsummary());
        bookbasic.setAuthor(form.getAuthor());
        bookbasic.setContent(form.getContent());
        bookbasic.setIsbn(form.getIsbn());
        bookbasic.setLanguagea(form.getLanguage());
        bookbasic.setName(form.getBookName());
        bookbasic.setPicture(form.getpicture());
        //原价
        if (form.getPrimCost() != null) {
            bookbasic.setPrimCost(form.getPrimCost());
        } else {
            bookbasic.setPrimCost(0.0);
        }
        //进价
        if (form.getBid() != null) {
            bookbasic.setBid(form.getBid());
        } else {
            bookbasic.setBid(0.0);
        }
        bookbasic.setPublishPlace(form.getpublishPlace());
        bookbasic.setRank(form.getRank());
        bookbasic.setRemark(form.getremark());
        //售价
        if (form.getSellPrice() != null) {
            bookbasic.setSellPrice(form.getSellPrice());
        } else {
            bookbasic.setSellPrice(0.0);
        }
        bookbasic.setoperator(form.getOperator());
        bookbasic.setCreateTime(createTime);
        bookbasic.setUpdateTime(createTime);
        bookbasic.setIsDelete(0);
        bookbasic.setLocation(form.getLocation());
        return bookbasic;
    }

    /**
     * 添加入库表信息-张婷-2017-11-4 10:05:28
     * 添加入库表信息-张婷-2017-11-4 10:05:28
     *
     * @param form
     */
    public void addTWarehuse(BookViewModel form) {

//        List<TWarehuse> listw = TWarehuseRepository.getUseCountBylocation(form.getIsbn());
        //添加location的条件
        List<TWarehuse> listw = TWarehuseRepository.getUseCountBylocation(form.getIsbn(),form.getLocation());

        if (listw != null && listw.size() > 0) {
            TWarehuse warehuse = (TWarehuse) listw.get(0);
            Integer useNum = (listw.get(0)).getuseNum();
            Integer wareCount = (listw.get(0)).getWareCount();
            warehuse.setuseNum(form.getuseNum() + useNum);
            warehuse.setWareCount(form.getuseNum() + wareCount);
            warehuse.setUpdateTime(createTime);
            warehuse.setLocation(form.getLocation());
            TWarehuseRepository.edit(warehuse);
        } else {
            TWarehuse warehuse = new TWarehuse();
            warehuse.setuseNum(form.getuseNum());
            warehuse.setWareCount(form.getuseNum());
            warehuse.setId(UuidUtils.base58Uuid());
            warehuse.setAuthor(form.getAuthor());
            warehuse.setIsbn(form.getIsbn());
            warehuse.setShelfId("");
            warehuse.setOrigin(form.getorigin());
            warehuse.setoperator(form.getOperator());
            warehuse.setCreateTime(createTime);
            warehuse.setUpdateTime(createTime);
            warehuse.setIsDelete(0);
            warehuse.setRemark(form.getremark());
            warehuse.setLocation(form.getLocation());
            TWarehuseRepository.create(warehuse);
        }

    }

    /**
     * 更新三张表的信息
     * 更新图书表信息-张婷-2017-11-4 10:05:55
     *
     * @param form
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean updateBook(BookViewModel form) {

        //book表更新
        TBook book = TBookRepository.find(form.getId());
        String bookbasicId = book.getBasicId();
        book.setBasicId(bookbasicId);
        book.setOrigin(form.getorigin());
        book.setTotalPage(form.getTotalpage());
        book.setPublishTime(form.getPublishtime());
        String typeName = form.gettypeName();
        List<TBookAndType> list = bookTypeService.findTypeIdByTypeName(typeName);
        if (list != null && list.size() > 0) {
            String typeID = list.get(0).getId();
            book.setTypeId(typeID);
            book.setOwner(form.getowner());
            book.setUpdateTime(createTime);
            book.setSearchNum(form.getSearchNum());
            book.setRemark(form.getremark());
            book.setoperator(form.getOperator());
            book.setBookShelfId("");
            TBookRepository.edit(book);
        }

        return true;
    }

    /**
     * 更新bookbasic表的记录---不修改位置信息的修改---王华伟添加注释--2018年5月7日17:47:48
     *
     * @param form
     * @return
     */
    public boolean updateBookBasic(BookViewModel bookViewModel) {

        TBookBasic tBookBasic = TBookBasicRepository.find(bookViewModel.getId());
        if (tBookBasic == null) {
            return false;
        }
        tBookBasic.setName(bookViewModel.getBookName());
        tBookBasic.setAuthor(bookViewModel.getAuthor());
        tBookBasic.setsummary(bookViewModel.getsummary());
        tBookBasic.setContent(bookViewModel.getContent());
        tBookBasic.setRemark(bookViewModel.getremark());
        tBookBasic.setPublishPlace(bookViewModel.getpublishPlace());
        tBookBasic.setLocation(bookViewModel.getLocation());
        if (bookViewModel.getpicture() != null) {
            tBookBasic.setPicture(bookViewModel.getpicture());
        }
        TBookBasicRepository.edit(tBookBasic);
        return true;
    }

    /**
     * 更新图书的物理位置----张明慧让王华伟在原方法上修改--2018年5月7日16:26:03
     * @param bookViewModel
     * @return
     */
    public boolean updateBookBasicBylocation(BookViewModel bookViewModel) {
        TBookBasic tBookBasic = TBookBasicRepository.find(bookViewModel.getId());
        if (tBookBasic == null) {
            return false;
        }
        tBookBasic.setName(bookViewModel.getBookName());
        tBookBasic.setAuthor(bookViewModel.getAuthor());
        tBookBasic.setsummary(bookViewModel.getsummary());
        tBookBasic.setContent(bookViewModel.getContent());
        tBookBasic.setRemark(bookViewModel.getremark());
        tBookBasic.setPublishPlace(bookViewModel.getpublishPlace());
        tBookBasic.setLocation(bookViewModel.getLocation());
        if (bookViewModel.getpicture() != null) {
            tBookBasic.setPicture(bookViewModel.getpicture());
        }
        TBookBasicRepository.edit(tBookBasic);

        //--------------------------------------------位置修改以后库存量发生变化Start---------------------------------------
        //1、如果位置发生改变则t_warehuse表中use_count和ware_count的数量将会发生变化
        //获取位置信息、useCount、wareCount
        String isbn = bookViewModel.getIsbn();
        String Position = bookViewModel.getLocation();
        Integer useCount = bookViewModel.getuseNum();
        Integer wareCount = bookViewModel.getuseNum();
        //2、根据ISBN和Location去t_warehuse查询这条数据是否存在；如果存在只需在原有use_count和ware_count的基础上加上已获取的useCount和ware_count
        //根据ISBN和Location去t_warehuse查询这条数据是否存在
        List<TWarehuse> tWarehuse = TWarehuseRepository.getUseCountBylocation(isbn, Position);

        TWarehuse twarehuse = new TWarehuse();
        if (tWarehuse.isEmpty()){
            //删除原有的数据
            //为了删除原有数据使用
            List<TWarehuse> tWareHuseNoPosition = TWarehuseRepository.getUseCount(isbn);
            TWarehuse twarehusenoposition = TWarehuseRepository.find(tWareHuseNoPosition.get(0).getId());
            twarehusenoposition.setIsDelete(1);
            TWarehuseRepository.edit(twarehusenoposition);
            //向数据库中添加数据
            twarehuse.setId(UuidUtils.base58Uuid());
            twarehuse.setAuthor(bookViewModel.getAuthor());
            twarehuse.setIsbn(isbn);
            twarehuse.setShelfId(null);
            twarehuse.setOrigin(null);
            twarehuse.setuseNum(useCount);
            twarehuse.setWareCount(wareCount);
            twarehuse.setRemark("位置修改添加");
            twarehuse.setoperator(null);
            twarehuse.setCreateTime(new Date());
            twarehuse.setUpdateTime(new Date());
            twarehuse.setIsDelete(0);
            twarehuse.setLocation(Position);
            TWarehuseRepository.edit(twarehuse);
        }else{
            twarehuse.setuseNum(tWarehuse.get(0).getWareCount()+wareCount);
            twarehuse.setuseNum(tWarehuse.get(0).getuseNum()+useCount);
            TWarehuseRepository.edit(twarehuse);
        }
        //--------------------------------------------位置修改以后库存量发生变化END---------------------------------------
        return true;
    }

    /**
     * 根据条件进行模糊查询+田成荣+2017年10月12日14:57:41
     *
     * @param MoHu_str
     * @return
     */
    public PaginationEntity findByConditions(String MoHu_str, int pageNum, int pageSize) {
        List<BookViewModel> list = TBookBasicRepository.findByConditions(MoHu_str);
        //王雪芬-排除重复项-2018年4月16日17:17:18
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getBookName().equals(list.get(i).getBookName()) && list.get(j).getLocation().equals(list.get(i).getLocation())) {
                    list.remove(j);
                }
            }
        }
        PaginationEntity paginationEntity = new PaginationEntity(pageNum, pageSize, list);
        return paginationEntity;
    }
    /**
     * 根据条件进行模糊查询+王雪芬+2018年5月13日15:03:01+不可借阅+手机端
     *
     * @param MoHu_str
     * @return
     */
    public PaginationEntity findByConditionselect(String MoHu_str,String MoHu_s,int pageNum, int pageSize) {
        List<BookViewModel> list=null;
        switch (MoHu_s){
            case "全部":
                list = TBookBasicRepository.findByConditions(MoHu_str);
                break;
            case"可借阅":
             list = TBookBasicRepository.findcanborrow(MoHu_str);
                break;
            default:
              list = TBookBasicRepository.findnotborrow(MoHu_str);
                break;
        }
        //王雪芬-排除重复项-2018年4月16日17:17:18
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getBookName().equals(list.get(i).getBookName()) && list.get(j).getLocation().equals(list.get(i).getLocation())) {
                    list.remove(j);
                }
            }
        }
        PaginationEntity paginationEntity = new PaginationEntity(pageNum, pageSize, list);
        return paginationEntity;
    }

    /**
     * 根据条件进行模糊查询，添加location+张明慧+2018年4月29日16:51:34
     *
     * @param MoHu_str
     * @return
     */
    public PaginationEntity findByCondition(String MoHu_str, int pageNum, int pageSize) {
        List<BookViewModel> list = TBookBasicRepository.findByConditions(MoHu_str);
        PaginationEntity paginationEntity = new PaginationEntity(pageNum, pageSize, list);
        return paginationEntity;
    }
    /**
     * 根据条件进行模糊查询返回basic表中的ID+王雅静+2017年12月3日11:03:05
     * 修改为真分页查询 -武刚鹏修改-2017年12月5日19:58:33
     *
     * @param conditions
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PaginationEntity findByConditionsSum(String conditions, int pageNum, int pageSize) {
        List<BookViewModel> list = TBookBasicRepository.findByConditionsSum(conditions, pageNum, pageSize);
        PaginationEntity paginationEntity = new PaginationEntity();
        paginationEntity.setListRows(list);
        paginationEntity.setTotalNum(TBookBasicRepository.findByConditionsSumCount(conditions));
        paginationEntity.setPageSize(pageSize);
        paginationEntity.setPageNum(pageNum);
        return paginationEntity;
    }

    /**
     * 猜你喜欢分页查阅图书信息 -武刚鹏-2017年11月23日14:01:26
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PaginationEntity getBookFavorite(String userId, int pageNum, int pageSize) {
        PaginationEntity paginationEntity = new PaginationEntity();
        List<BookViewModel> list = null;
        int totalNum = 0;
        //先根据用户借阅记录中的书的类别查书
        List<String> typeIds = TBorrowingRepository.findTypeIdListByUserId(userId);
        if (typeIds != null && typeIds.size() > 0) {
            list = TBookBasicRepository.findBooksByTypeIds(typeIds, pageNum, pageSize);
            totalNum = TBookBasicRepository.findBooksCountByTypeIds(typeIds);
        }
        //如果list没有查到
        if (list == null || list.size() == 0) {
            list = TBookBasicRepository.findBookListByPagination(pageNum, pageSize);
            totalNum = TBookBasicRepository.findBookCount(null);
        }
        paginationEntity.setPageNum(pageNum);
        paginationEntity.setPageSize(pageSize);
        paginationEntity.setListRows(list);
        paginationEntity.setTotalNum(totalNum);
        return paginationEntity;
    }


    /**
     * 图书详情页-田成荣-2017-11-4 10:37:23
     **/
    public List<BookViewModel> findBookDetailsByIsbn(String isbn) {
        return TBookBasicRepository.findBookDetailsByIsbn(isbn);
    }

    /**
     * 根据isbn获得索书号 getSearchSumByISBN-张婷-2017-11-4 10:37:51
     **/
    public List<BookViewModel> getSearchSumByISBN(String isbn) {
        return TBookRepository.getSearchSumByISBN(isbn);
    }

    /**
     * 借阅记录表调用-郭晶晶-2017年11月5日16:58:33
     **/
    public List<BookViewModel> getAllBookList() {
        return TBookRepository.getAllBookList();
    }


    /**
     * 图书详情信息传booklist-张婷-2017年11月9日10:29:07
     **/
//    public List<BookViewModel> findBookDetailsByIsbnList(List<String> isbnList,List<String> locationList) {
////        return TBookBasicRepository.findBookDetailsByIsbnList(isbnList,locationList);
//    }

    /**
     * 根据bookid获得图书信息-张婷-2017-11-9 11:47:55
     **/
    public List<BookViewModel> getBookInfoBybookId(String bookId) {
        return TBookRepository.getBookInfoBybookId(bookId);
    }

    /**
     * 根据索书号获得图书信息（借阅使用）-张婷-2017-11-13 09:37:50
     */
    public List<BookViewModel> getBookBySearchNum(String searchNum) {
        return TBookRepository.getBookBySearchNum(searchNum);

    }
    //查询5条最热图片--张明慧--2018年4月30日21:36:39
    public List<hotBookViewModel> getHotBook(){
        return TBorrowingRepository.getHotBook();
    }



    //查询5条最热图书 -王雪芬-2018年4月21日11:23:07--此方法有bug，不可以使用两次next
//    public List<hotBookViewModel> getHotBook() {
//        //接收客户端代码
//        List<TBorrowing> list = TBorrowingRepository.GotBook();
//        //定义一个数组
//        List<hotBookViewModel> listNoRe = new ArrayList<>();
//        //声明一个Hashset用来存放不同数据，去重
//        HashSet hashSet = new HashSet();
//        //增强for循环使用，他与普通for循环的区别是，他是有目标的遍历，而普通的是没有的
//        for(TBorrowing tBorrowing:list){
//            String isbn = tBorrowing.getIsbn();
//            hashSet.add(isbn);
//        }
//         //迭代器模式。可以访问容器对象各个元素，为容器工作，提供方法顺序访问聚合对象中的元素
//        Iterator iterator = hashSet.iterator();
//        //判断容器内是否有可供访问的元素
//        while (iterator.hasNext()){
//            //设定一个计时器，判断有多少个重复的数据
//            int count = 0;
//            //下一个元素，指的是越过该元素的索引
//            String isbn = ((String)iterator.next());
//            //判断hashset中不重复的元素与存在重复元素之间进行比较是否相等
//            for (int j = 0; j < list.size(); j++) {
//                //如果相等则让及时器加1
//                if(list.get(j).getIsbn().equals(isbn)){
//                    count ++;
//                }
//            }
//            //如果有重复数据，这里指的是第一次while循环因为还没有调出while循环，count>1指的是isbn与list所有数据比较出的结果
//            if(count > 1){
//                hotBookViewModel hotBookViewModel = new hotBookViewModel();
//                //把有重复的数据添加到viewmodel中
//                hotBookViewModel.setIsbn((String) iterator.next());
//                //再添加到list中
//                listNoRe.add(hotBookViewModel);
//            }
//
//        }
//
//        List<TBookBasic> listbookname = TBorrowingRepository.GotBookName(listNoRe);
//        for (int i = 0; i <listbookname.size() ; i++) {
//            hotBookViewModel hotBookModel = new hotBookViewModel();
//            hotBookModel.setBookName(listbookname.get(i).getName());
//            listNoRe.set(i,hotBookModel);
//        }
//       return listNoRe;
//    }


    /**
     * 根据父类别ID查询该父类别下的所有书籍 -武刚鹏-2017年11月15日13:15:27
     *
     * @param parentTypeId
     * @return
     */
    public List<BookViewModel> getBookByParentTypeId(String parentTypeId) {
        //先查询该父类下所有的子类ID集合
        List<TBookAndType> tBookAndTypeList = bookTypeService.findBookTypeBypId(parentTypeId);
        if (tBookAndTypeList == null || tBookAndTypeList.size() == 0) {
            return null;
        }
        List<String> bookTypeList = new ArrayList<String>();
        for (int i = 0; i < tBookAndTypeList.size(); i++) {
            bookTypeList.add(tBookAndTypeList.get(i).getId());
        }
        //根据子类别ID集合，查询这些子类的书籍
        return TBookRepository.getBookListByParentTypeId(bookTypeList);
    }

    /**
     * 查询还有多少书可以借-王雪芬-2018年1月11日14:53:50---添加物理位置条件--2018年5月1日16:46:13
     *
     * @param use_count
     * @return
     */
   public List<TWarehuse> bookUserCount(String isbn,String location) {
        return TWarehuseRepository.getUseCountBylocation(isbn,location);
    }


    /**
     * 查询图书信息,不存在调抓取图书的接口--刘雅雯--2017年11月18日20:45:44
     *
     * @param isbn
     * @return
     * @throws Exception
     */

    public TBookBasic findBookByIsbn(String isbn) throws Exception {
        List<TBookBasic> tBookBasicsList = TBookRepository.getBasicIdByisbn(isbn);
        TBookBasic tBookBasic = null;
        if (tBookBasicsList == null || tBookBasicsList.size() == 0) {
            tBookBasic = BookNetInfoUtil.getBookBasicByISBN(isbn);
        } else {
            tBookBasic = tBookBasicsList.get(0);
        }
        //处理图片路径问题（用于图片显示）
        if (tBookBasic != null) {
            if (tBookBasic.getPicture() != null) {
                if (!tBookBasic.getPicture().contains("http://")) {
                    tBookBasic.setPicture(FastDfsUtils.getFastDfsIP() + tBookBasic.getPicture());
                }
            }

        }
        return tBookBasic;
    }

    /**
     * 根据ISBN直接从网上抓取图书基本信息--张明慧--2018年1月13日20:51:12
     *
     * @param isbn
     * @return
     * @throws Exception
     */
    public TBookBasic findgetBookByIsbn(String isbn) throws Exception {

        TBookBasic tBookBasic = null;
        tBookBasic = BookNetInfoUtil.getBookBasicByISBN(isbn);
        //   处理图片路径问题（用于图片显示）
        if (tBookBasic != null) {
            if (tBookBasic.getPicture() != null) {
                if (!tBookBasic.getPicture().contains("http://")) {
                    tBookBasic.setPicture(FastDfsUtils.getFastDfsIP() + tBookBasic.getPicture());
                }
            }

        }
        return tBookBasic;
    }

    /**
     * 王华伟--2017年12月1日--实现按条件导出
     *
     * @param moHu_str
     * @return
     */
    public List<BookViewModel> findByConditionsExport(String MoHu_str) {
        List<BookViewModel> list = TBookBasicRepository.findByConditions(MoHu_str);
        return list;
    }

    //根据isbn导出书的信息。
    public List<BookViewModel> findByIsbns(List<String> isbns) {
        List<BookViewModel> list = TBookBasicRepository.findByIsbns(isbns);
        return list;
    }

    public boolean importBookList(List<BookViewModel> bookList) {
        List<BookViewModel> bookViewModels = new ArrayList<>();
        HashMap<String, BookViewModel> map = new HashMap<>();
        for (int i = 0; i < bookList.size(); i++) {
            if (!map.containsKey(bookList.get(i).getIsbn())) {
                map.put(bookList.get(i).getIsbn(), bookList.get(i));
            } else {
                BookViewModel model = map.get(bookList.get(i).getIsbn());
                model.setuseNum(model.getuseNum() + bookList.get(i).getuseNum());
                map.put(model.getIsbn(), model);
            }
        }
        //当导入数据不为空时，循环调用添加方法
        if (bookList == null || bookList.size() <= 0) {
            return false;
        }
        if (map != null && !map.isEmpty()) {
            for (String m : map.keySet()) {
                //要生成的索书号
                String searchNum = "";
                //将List中的数据循环加到单个实体中
                BookViewModel form = map.get(m);
                //根据isbn获取索书号
                form.setIsbn(form.getIsbn().replace("-", "").replace(".", ""));
                String isbn = form.getIsbn();
                if (isbn == null) {
                    return false;
                }
                if (isbn != null) {
                    List<BookViewModel> list = getSearchSumByISBN(isbn);
                    //索书号
                    if (list != null && list.size() > 0) {
                        String SearchNum = list.get(0).getSearchNum();
                        int length = (SearchNum).length();
                        //获得索书号的数字部分
                        String prefixSearchNum = (SearchNum).substring(0, length - 2);
                        //获得索书号的非数字部分
                        String suffixSearchNum = (SearchNum).substring(length - 2, length);
                        int number = Integer.parseInt(suffixSearchNum) + 1;
                        if (number < 10) {
                            searchNum = prefixSearchNum + 0 + number;
                        } else {
                            searchNum = prefixSearchNum + number;
                        }
                    } else {
                        //如果图书第一次入库，索书号为isbn+01
                        searchNum = isbn + "01";
                    }
                    //查到的索书号赋值到实体中
                    form.setSearchNum(searchNum);
                    form.setremark("批量插入");
                }
                //调用插入方法,添加图书信息
                addBookInfo(form);
            }
        }
        return true;
    }


    /**
     * 根据条件分页查询Book表的信息 武刚鹏-2017年12月4日21:04:52
     *
     * @param conditions
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PaginationEntity findTBookByConditions(String conditions, int pageNum, int pageSize) {
        PaginationEntity paginationEntity = new PaginationEntity();
        List<BookViewModel> list = TBookRepository.findTBookByConditions(conditions, pageNum, pageSize);
        paginationEntity.setListRows(list);
        paginationEntity.setPageSize(pageSize);
        paginationEntity.setPageNum(pageNum);
        int total = TBookRepository.findBookInfoCount(conditions);
        paginationEntity.setTotalNum(total);
        return paginationEntity;

    }
    /**
     * 根据条件分页查询Book表的信息 王雅静-2018年5月5日11:27:36
     *
     * @param isbn
     * @param location
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PaginationEntity findTBookByIsbnAndLocation(String isbn,String location, int pageNum, int pageSize) {
        PaginationEntity paginationEntity = new PaginationEntity();
        List<BookViewModel> list = TBookRepository.findTBookByIsbnAndLocation(isbn,location, pageNum, pageSize);
        paginationEntity.setListRows(list);
        paginationEntity.setPageSize(pageSize);
        paginationEntity.setPageNum(pageNum);
        return paginationEntity;

    }
    /**
     * 给数据库中数据添加目录和简介  张明慧-2018年1月13日20:53:48
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public void getAllDataAndConSum(int pageNum, int pageSize) throws Exception {
        boolean flag = true;
        //1、获取数据库所有书籍
        PaginationEntity paginationEntity = findAllBookByPagination(pageNum, pageSize);
        List<BookViewModel> list = (List<BookViewModel>) paginationEntity.getListRows();
        //2、判断Content、summer值是否为空
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                BookViewModel model = list.get(i);
                //3、如果为空，抓取图书信息
                if (model.getContent() == null || model.getsummary() == null || model.getContent().trim() == "" || model.getsummary().trim() == "" || model.getsummary().equals("")) {
                    //4\赋值
                    TBookBasic book = findgetBookByIsbn(model.getIsbn());
                    if (book == null) {
                        continue;
                    }
                    if (book.getContent() != null) {
                        model.setContent(book.getContent());
                    }
                    if (book.getsummary() != null) {
                        model.setsummary(book.getsummary());
                    }
                    if (book.getPicture() != null || book.getPicture().trim() == "") {
                        model.setpicture(book.getPicture());
                    }
                    //5、保存
                    updateBookBasic(model);
                    //6、打印日志
                    log.info("添加此条数据成功");

                }
            }
        }

        System.out.println("批量更新有效图书信息完成！");
    }

    /**
     * 根据ISBN查询信息并把简介和目录保存到数据库，显示到页面 张明慧-2018年1月13日20:56:31
     *
     * @param isbn
     * @return
     */
    public BookViewModel getSearchBookByISBN(String isbn) throws Exception {
        boolean flag = true;
        BookViewModel model = new BookViewModel();
        //1、根据ISBN查询数据库所有书籍
        List<TBookBasic> listBasic = TBookRepository.getBasicIdByisbn(isbn);
        //2、判断此条数据数据库中的Content、summer值是否为空
        if (listBasic.get(0).getContent() == null || listBasic.get(0).getsummary() == null || listBasic.get(0).getAuthor().trim() == ""|| listBasic.get(0).getAuthor() == null || listBasic.get(0).getContent().trim() == "" || listBasic.get(0).getsummary().trim() == "" || listBasic.get(0).getsummary().equals("")) {
            //3、如果为空，抓取图书信息
            TBookBasic book = findgetBookByIsbn(isbn);
            //4、如果没有查到这本书，则返回数据库信息
            if (book == null) {
                model.setBookName(listBasic.get(0).getName());
                model.setsummary(listBasic.get(0).getsummary());
                model.setContent(listBasic.get(0).getContent());
                model.setPublishtime(listBasic.get(0).getPublishTime());
                model.setpicture(listBasic.get(0).getPicture());
                model.setLocation(listBasic.get(0).getLocation());
                model.setAuthor(listBasic.get(0).getAuthor());

            } else {
                //5、根据抓取的数据进行赋值
                if (book.getContent() != null) {
                    listBasic.get(0).setContent(book.getContent());
                }
                if (book.getsummary() != null) {
                    listBasic.get(0).setsummary(book.getsummary());
                }
                if (book.getAuthor() != null) {
                        listBasic.get(0).setAuthor(book.getAuthor().replace(";",""));
                }
                if (book.getPicture() != null || book.getPicture().trim() == "") {
                    if (book.getPicture().contains("http")) {
                        String pictureURL = book.getPicture().substring(book.getPicture().indexOf(FastDfsUtils.getFastDfsIP()) + FastDfsUtils.getFastDfsIP().length());
                        book.setPicture(pictureURL);
                        listBasic.get(0).setPicture(book.getPicture());
                    }
                }
                model.setId(listBasic.get(0).getId());
                model.setIsbn(listBasic.get(0).getIsbn());
                model.setBookName(listBasic.get(0).getName());
                model.setAuthor(listBasic.get(0).getAuthor().replace(";", ""));
                model.setsummary(listBasic.get(0).getsummary());
                model.setContent(listBasic.get(0).getContent());
                model.setremark(listBasic.get(0).getRemark());
                model.setpublishPlace(listBasic.get(0).getPublishPlace());
                model.setpicture(listBasic.get(0).getPicture());
                model.setLocation(listBasic.get(0).getLocation());

                // 6、保存
                updateBookBasic(model);

            }
        } else {
            model.setBookName(listBasic.get(0).getName());
            model.setAuthor(listBasic.get(0).getAuthor().replace(";", ""));
            model.setsummary(listBasic.get(0).getsummary());
            model.setContent(listBasic.get(0).getContent());
            model.setPublishtime(listBasic.get(0).getPublishTime());
            model.setpicture(listBasic.get(0).getPicture());
            model.setLocation(listBasic.get(0).getLocation());

        }
        return model;
    }

    /**
     * 得到所有的图书位置分类--PC端-张明慧-2018年4月28日14:24:00
     * @return
     */
    public List<BookViewModel> findAllbookLocation(int pageNum, int pageSize){

        return TBookBasicRepository.findAllbookLocation(pageNum,pageSize);

    }
    //得到盘点信息
    public List<BookViewModel> getCheckInfo(String location){

        //根据location查询盘点表是否存在。
        List<BookViewModel> list =TBookRepository.getCheckBylocation(location);
        List<String> listID = new ArrayList<>();
        //如果存在就批量删除。
        if(list.size()!=0)
        {
            for(int i=0;i<list.size();i++)
            {
                String id = list.get(i).getId();
                listID.add(id);
            }
            //删除
            TBookRepository.deleteCheckById(listID);
        }
        //根据location查询basic和库存表给到盘点所需的信息。
        List<BookViewModel> checkList = TBookBasicRepository.getCheckInfo(location);
        //插入到盘点表。
        boolean result = TBookBasicRepository.insertChrckList(checkList,createTime);
        List<BookViewModel> checkList1=null;
        if(result==true)
        {
            //得到盘点表的所有信息。
            checkList1 =  TBookRepository.getCheckInfo(location);
        }
        else
        {
            checkList1 = null;
        }
        return checkList1;

    }

    //盘点中。。。--田成荣--2018年5月17日16:03:45
    public List<BookViewModel> getChecking(String isbn,String location) {
       //根据isbn查询盘点表信息
        List<BookViewModel> list = TBookRepository.getCheckByisbn(isbn,location);
        List<BookViewModel> listCheck = null;
        //有此信息，进行更新add_count +1。
         if(list.size()!=0)
        {
            // 进行 +1。。
            if(TBookRepository.updateCheckByisbn(isbn,createTime)==false)
            {
                return listCheck;
            }
            else
            {
                //查询所有。。
                return TBookRepository.getCheckInfo(location);
            }
        }
        else
        {
            return listCheck;
        }
    }
    //盘点结束--田成荣--2018年5月17日19:50:19
    public List<BookViewModel> getCheckend(String location) {
        List<BookViewModel> listCheck = null;
        if(TBookRepository.updateCheckAll(createTime)==false)
        {
            return listCheck;
        }
        else {

            if(TBookRepository.updateCheckHistoryAll(location)==true)
            {
                listCheck = TBookRepository.getCheckResult(location);
            }
        }
        return listCheck;
    }

    //查询盘点有差值的（盘点结果不为0的）
    public List<BookViewModel> checkResult(String location)
    {
        return TBookRepository.getCheckResult(location);
    }

    /**
     * 书架得到所有的图书详情--手机端-张明慧-2018年4月28日14:24:00
     * @return
     */
    public List<BookViewModel> GetbookDetatil(String isbn,String location){

        return TBookBasicRepository.GetbookDetatil(isbn,location);

    }

    /**
     * 查询所有书籍没有从网上抓取信息-王雪芬-2018年5月10日11:39:48
     * @return
     */
    public List<TBookBasic>Getbookisbn(){
        return TBookBasicRepository.Getbookisbn();
    }

    /**
     * 图书管理页面显示图书名字-王雪芬-电脑端-2018年5月15日10:09:09
     * @return
     */
    public List<TBookBasic> getBookinfoNameService() {
        return TBookBasicRepository.getBookinfoNameRepository();

    }


    /**
     * 图书管理页面上传图片-王雪芬-电脑端-2018年5月15日10:09:09
     * @return
     * @param bookViewModel
     */
    @Transactional(rollbackOn = Exception.class)
    public boolean updateBookpic( BookViewModel bookViewModel) {
        return TBookBasicRepository.updateBookpic(bookViewModel);
    }
    /**
     * 查看该isbn是否有图片-王雪芬-电脑端-2018年5月15日10:09:09
     * @return
     */
    public List<TBookBasic> getBookinfopicture(String isbn) {
        return TBookBasicRepository.getBookinfopicture(isbn);
    }
}