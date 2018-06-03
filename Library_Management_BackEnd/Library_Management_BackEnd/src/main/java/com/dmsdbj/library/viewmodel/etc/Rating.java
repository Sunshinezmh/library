package com.dmsdbj.library.viewmodel.etc;

/**
 * 图书评分类
 * @author 郑晓东
 * 2017年10月27日12点10分
 */
public class Rating {
    private int max;
    private int numRaters;
    private String average;
    private int min;
    public void setMax(int max) {
        this.max = max;
    }
    public int getMax() {
        return max;
    }

    public void setNumRaters(int numRaters) {
        this.numRaters = numRaters;
    }
    public int getNumRaters() {
        return numRaters;
    }

    public void setAverage(String average) {
        this.average = average;
    }
    public String getAverage() {
        return average;
    }

    public void setMin(int min) {
        this.min = min;
    }
    public int getMin() {
        return min;
    }
}
