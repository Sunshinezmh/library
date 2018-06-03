package com.dmsdbj.library.viewmodel;/*
 @auther 张婷
 @DESCRIPTION ${DESCRIPTION}
 @create 2017/11/13
*/

import java.io.Serializable;

public class hotBookViewModel implements Serializable {

    public hotBookViewModel(){

    }
    public hotBookViewModel(String bookName,String isbn,String location,int count){
        this.bookName = bookName;
        this.isbn = isbn;
        this.location=location;
        this.count = count;


    }
   private String bookName =null;
    private String isbn =null;
    private int count =0;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location=null;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

