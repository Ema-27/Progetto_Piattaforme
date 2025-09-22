package com.educative.ecommerce.dto.product;

import com.educative.ecommerce.model.ProductInPurchase;
import com.educative.ecommerce.model.Purchase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PurchaseDto {

    private Integer id;

    @NotNull
    private Date purchaseTime;

    @NotNull
    private Integer buyerId;

    @NotNull
    private Double price;

    @NotNull
    private List<Integer> productIds;

    private List<ProductInPurchaseDto> products; // AGGIUNGI QUESTO

    public PurchaseDto(Purchase purchase){
        this.id = purchase.getId();
        this.purchaseTime = purchase.getPurchaseTime();
        this.buyerId = purchase.getBuyer() != null ? purchase.getBuyer().getId() : null;
        this.price = purchase.getPrice();
        this.productIds = new ArrayList<>();
        if (purchase.getProductsInPurchaseList() != null) {
            for(ProductInPurchase pip: purchase.getProductsInPurchaseList()) {
                if (pip.getProduct() != null)
                    this.productIds.add(pip.getProduct().getId());
            }
            // Aggiungi la lista dettagliata dei prodotti ordinati
            this.products = purchase.getProductsInPurchaseList().stream()
                    .map(ProductInPurchaseDto::new)
                    .collect(Collectors.toList());
        } else {
            this.products = new ArrayList<>();
        }
    }

    public PurchaseDto(){
        this.productIds = new ArrayList<>();
        this.products = new ArrayList<>();
    }
}
