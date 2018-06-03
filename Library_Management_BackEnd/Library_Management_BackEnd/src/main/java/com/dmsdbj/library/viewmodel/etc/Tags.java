package com.dmsdbj.library.viewmodel.etc;

/**
 * 标签类，接收网络数据时使用，系统未用
 * @author 郑晓东
 * 2017年10月27日12点10分
 */
public class Tags {
    private int count;//使用数量
    private String name;//标签名称
    private String title;//标签标题
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
