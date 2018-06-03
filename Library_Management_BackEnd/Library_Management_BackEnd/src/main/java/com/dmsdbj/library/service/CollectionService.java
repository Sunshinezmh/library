/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmsdbj.library.service;

import com.dmsdbj.library.app.util.UUID.UuidUtils;
import com.dmsdbj.library.controller.util.PaginationEntity;
import com.dmsdbj.library.entity.TCollection;
import com.dmsdbj.library.repository.TCollectionRepository;
import com.dmsdbj.library.viewmodel.BookViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 10283_000
 */
public class CollectionService {
    @Inject
    private TCollectionRepository TCollectionRepository;

    @Inject
    BookService bookService;

    /**
     * 删除书架上的书。田成荣
     * 修改：优化调用删除方法 郑晓东 2017年11月9日13点08分
     *
     * @param Id
     * @return
     */
    public boolean deleteCollections(String Id) {
        TCollection tCollection = TCollectionRepository.getCollectionInfo(Id);
        if (tCollection == null) {
            return false;
        } else {
            tCollection.setIsDelete(1);
            TCollectionRepository.edit(tCollection);
            return true;
        }
    }

    /**
     * 批量删除收藏（假删除） 郑晓东 2017年11月9日10点14分
     *
     * @param strArr 收藏ID字符串数组
     * @return 成功或失败
     */
    public boolean deleteCollections(String[] strArr) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strArr.length; i++) {
            list.add(strArr[i]);
        }
        return TCollectionRepository.deleteCollections(list);
    }

    /**
     * 根据userID查书架上书的信息。田成荣
     * 修改：调用bookService接口查询图书信息 郑晓东 2017年11月9日12点30分
     *修改：返回结果添加location 张明慧  2018年5月1日18:44:42
     * * @return
     */
    public List<BookViewModel> getCollectionByuserId(String userId) {
        List<BookViewModel> listborrowing = TCollectionRepository.findBookDetailsByIsbnList(userId);
        if(listborrowing.size() <0){
            return null;
        }

        return listborrowing;

    }

    /**
     * 添加图书收藏  田成荣
     * 修改：添加不能重复添加的判断-王雅静
     *
     * @param tCollection 图书收藏实体
     * @return 成功true，失败false
     */
    public boolean addCollection(TCollection tCollection) {
        //判断概述是否已经被收藏--添加location条件判断--张明慧
        Boolean isCollectionResult = TCollectionRepository.isCollectioned(tCollection.getIsbn(), tCollection.getUserId(),tCollection.getLocation());
        if (isCollectionResult == true) {
            return false;
        }
        tCollection.setId(UuidUtils.base58Uuid());
        if (tCollection.getIsbn() == null && tCollection.getIsbn().isEmpty()) {
            return false;
        }
        TCollectionRepository.create(tCollection);
        return true;

    }

    //TODO 未知功能
    //根据bookID查isbn,拿着isbn去查CollectionId。田成荣
    public List<TCollection> getCollectionIdBybookId(String bookid, String userId) {
        return TCollectionRepository.getCollectionbookid(bookid, userId);
    }


     /*
       输入书名在书架中搜索图书--张明慧--2018年5月21日21:03:32
       @param MoHu_str 书名
       @param userid   用户名
       @return 成功true，失败false
    */

     public List<BookViewModel> findColByCondition(String MoHu_str,String userId){
         List<BookViewModel> listCollection=TCollectionRepository.findColByCondition(MoHu_str,userId);
         return listCollection;
     }

}
