package com.strawberry.util.bean;

import java.util.List;

/**
 * 功能：用于分页插件的包装类
 *
 * @author alan.wang
 *
 * @since 2018.09.27
 *
 * @version 1.0.0
 *
 */
public class PageViewBean {

    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 起始行
     */
    private int startRow;
    /**
     * 末行
     */
    private int endRow;
    /**
     * 总数
     */
    private int total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 排序
     */
    private String orderBy;
    /**
     * 搜索词
     */
    private String searchKeyword;
    /**
     * 搜索区域
     */
    private List<String> searchField;
    /**
     * 分页数据
     */
    private List<?> records;
    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 结束时间
     */
    private String endDate;

    public PageViewBean() {
    }

    @Override
    public String toString() {
        return "PageViewBean [ pages=" + pages + ", pageSize=" + pageSize + ", pageNum=" + pageNum
                + ", total=" + total + ", startRow=" + startRow + ", endRow=" + endRow + ",orderBy=" + orderBy
                + ",searchKeyword=" + searchKeyword + ",searchField=" + searchField + "]";
    }

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

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<?> getRecords() {
        return records;
    }

    public void setRecords(List<?> records) {
        this.records = records;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public List<String> getSearchField() {
        return searchField;
    }

    public void setSearchField(List<String> searchField) {
        this.searchField = searchField;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
