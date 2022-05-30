package com.imooc.mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author zyy
 * @QQ 1048666899
 * @GitHub https://github.com/HeroZyy
 */
public class ProductListReq {
    private String keyword;
    private Integer categoryId;
    private String orderBy;
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    @Override
    public String toString() {
        return "ProductListReq{" +
                "keyword='" + keyword + '\'' +
                ", categoryId=" + categoryId +
                ", orderBy='" + orderBy + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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
}
