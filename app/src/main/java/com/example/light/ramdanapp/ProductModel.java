package com.example.light.ramdanapp;

import java.io.Serializable;

class ProductModel implements Serializable {

    String unite;
    String product;
    int quantite;

    public ProductModel(String product, int quantite, String unite){
        this.unite = unite;
        this.product = product;
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
