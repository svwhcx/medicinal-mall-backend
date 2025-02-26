package com.medicinal.mall.mall.demos.query;

/**
 * @description
 * @Author cxk
 * @Date 2025/2/26 22:24
 */
public class PageQuery {

    private Integer pageNum;

    private Integer pageSize;

    private Integer start;


    public PageQuery() {
    }

    public PageQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        return (pageNum - 1) * pageSize;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
