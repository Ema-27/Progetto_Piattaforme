package com.educative.ecommerce.dto.product;

import com.educative.ecommerce.model.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductDto {

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String code;

    private String imageUrl;

    private double price;

    private String description;

    @NotNull
    private Integer categoryId;

    private long version;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.code = product.getCode();
        this.imageUrl = product.getImageUrl(); // Nota: usa imageUrl, non imageURL per coerenza!
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.version = product.getVersion();
        this.categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
    }

    public ProductDto() {
    }
}
