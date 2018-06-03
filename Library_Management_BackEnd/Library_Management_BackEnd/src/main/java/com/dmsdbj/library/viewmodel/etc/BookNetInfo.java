package com.dmsdbj.library.viewmodel.etc;

import java.util.List;

/**
 * 图书信息抓取数据
 * @author 郑晓东
 * 2017年10月27日12点10分
 */
public class BookNetInfo {

    private Rating rating;//图书评分（豆瓣评分）

    private String subtitle;//图书副标题

    private List<String> author;//图书作者

    private String pubdate;//图书出版日期

    private List<Tags> tags;//图书常用标签

    private String origin_title;

    private String image;//图书封面地址（url）

    private String binding;//装帧

    private List<String> translator;//图书翻译者

    private String catalog;//图书目录

    private String pages;//图书页码数

    private Images images;//图书封面（大中小）（url）

    private String alt;//图书访问地址

    private String id; //豆瓣图书ID

    private String publisher;//图书出版社

    private String isbn10;//图书ISBN 10 位

    private String isbn13;//图书ISBN 13位

    private String title;//图书标题

    private String url;//返回图书信息的URL

    private String alt_title;

    private String author_intro;//作者介绍

    private String summary;//图书摘要，概述

    private Series series;//图书系列

    private String price;//图书价格

    private String ebook_url;//电子书地址

    private String ebook_price;//电子书价格

    private String msg;//查询失败的返回信息

    private int code;//查询失败的错误码

    private String request;//查询失败的错误url

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEbook_url() {
        return ebook_url;
    }

    public void setEbook_url(String ebook_url) {
        this.ebook_url = ebook_url;
    }

    public String getEbook_price() {
        return ebook_price;
    }

    public void setEbook_price(String ebook_price) {
        this.ebook_price = ebook_price;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
