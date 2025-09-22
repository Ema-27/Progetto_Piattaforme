package com.educative.ecommerce.dto.product;

import com.educative.ecommerce.model.ProductInPurchase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInPurchaseDto {
    private Integer productId;
    private String name;
    private Double price;
    private String imageUrl; // AGGIUNGI QUESTO CAMPO

    public ProductInPurchaseDto(ProductInPurchase pip) {
        this.productId = pip.getProduct().getId();
        this.name = pip.getProduct().getName();
        this.price = pip.getPrice();
        this.imageUrl = pip.getProduct().getImageUrl(); // AGGIUNGI QUESTO
    }
}
