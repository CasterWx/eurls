package com.antzuhl.mall.vo;

import com.antzuhl.mall.pojo.Product;
import com.antzuhl.mall.util.PropertiesUtil;

import java.math.BigDecimal;
import java.util.Date;

public class ProductDetail {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String imageHost;

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public String getImageHost() {
        return imageHost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage == null ? null : mainImage.trim();
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages == null ? null : subImages.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public ProductDetail toData(Product product) {
        ProductDetail detail = new ProductDetail();
        detail.setId(product.getId());
        detail.setCategoryId(product.getCategoryId());
        detail.setDetail(product.getDetail());
        detail.setName(product.getName());
        detail.setMainImage(product.getMainImage());
        detail.setPrice(product.getPrice());
        detail.setStatus(product.getStatus());
        detail.setStock(product.getStock());
        detail.setSubtitle(product.getSubtitle());
        detail.setSubImages(product.getSubImages());
        detail.setCreateTime(product.getCreateTime());
        detail.setUpdateTime(product.getUpdateTime());
        detail.setImageHost(PropertiesUtil.getProperty("image.host"));

        return detail;
    }
}