package com.fastfood.api.domain.product;

public class ProductImage {
    private String url;

    public ProductImage() {
    }

    public ProductImage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
