import { ProductInPurchase } from "../ProductInPurchase/productInPurchase";
import { User } from "../User/user";

export interface Prova {
    id?: number;
    price: number;
    purchaseTime: Date;
    buyerId: number;
    buyer?: User;
    productsInPurchaseList?: ProductInPurchase[];
    productIds?: number[]; // Lista di id prodotto (per POST/PUT)
}
