import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Product } from "src/app/model/Product/product";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class ProductService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    public getProducts(): Observable<Product[]> {
        return this.http.get<Product[]>(`${this.apiServerUrl}/products`);
    }

    public addProduct(product: Product): Observable<Product> {
        return this.http.post<Product>(`${this.apiServerUrl}/products`, product);
    }
}
