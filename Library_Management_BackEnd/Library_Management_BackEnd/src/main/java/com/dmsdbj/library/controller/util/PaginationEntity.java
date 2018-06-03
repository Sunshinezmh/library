/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmsdbj.library.controller.util;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author 10283_000
 */
public class PaginationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageNum;
    private int pageSize;
    private int totalNum;
    private List<?> listRows;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public PaginationEntity() {

    }

    public PaginationEntity(int pageNum, int pageSize, List<?> list) {
        this.pageNum=pageNum;
        this.pageSize=pageSize;
        this.totalNum = list.size();
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = pageNum * pageSize;
        if (toIndex > this.totalNum) {
            toIndex = this.totalNum;
        }
        if (fromIndex > this.totalNum) {
            fromIndex = this.totalNum;
        }
        this.listRows = list.subList(fromIndex, toIndex);

    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<?> getListRows() {
        return listRows;
    }

    public void setListRows(List<?> listRows) {
        this.listRows = listRows;
    }

}
