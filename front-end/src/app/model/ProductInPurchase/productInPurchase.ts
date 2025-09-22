import { Product } from "../Product/product";
import { Purchase } from "../Purchase/purchase";

export interface ProductInPurchase {
    id: number;
    price: number;
    purchase?: Purchase;
    product: Product;
}
