package com.educative.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "products", schema = "esame")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "image_url")
    private String imageUrl;

    @Min(0)
    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "description")
    private String description;

    @Version
    @Column(name = "version", nullable = false)
    @JsonIgnore
    private long version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ProductInPurchase> productInPurchases;

    public Product() {}

    public Product(String name, String code, String imageUrl, double price, String description, Category category) {
        this.name = name;
        this.code = code;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.category = category;
    }
}
