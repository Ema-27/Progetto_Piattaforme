import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Category } from './model/Category/category';
import { Product } from './model/Product/product';
import { Purchase } from './model/Purchase/purchase';
import { User } from './model/User/user';
import { CategoryService } from './service/Category/category.service';
import { ProductService } from './service/Product/product.service';
import { PurchaseService } from './service/Purchase/purchase.service';
import { UserService } from './service/User/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public categories: Category[] = [];
  public products: Product[] = [];
  public users: User[] = [];
  public purchases: Purchase[] = [];
  public cart: Product[] = [];
  public user: User | null = null;
  public loggedIn: boolean = false;
  public userOrders: Purchase[] = [];
  public searchKey: string = '';

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private userService: UserService,
    private purchaseService: PurchaseService
  ) {}

  ngOnInit(): void {
    this.getCategories();
    this.getProducts();
    this.getUsers();
    this.getPurchases();
  }

  public getCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: (response: Category[]) => this.categories = response,
      error: (error: HttpErrorResponse) => alert(error.message)
    });
  }

  public getProducts(): void {
    this.productService.getProducts().subscribe({
      next: (response: Product[]) => this.products = response,
      error: (error: HttpErrorResponse) => alert(error.message)
    });
  }

  public getUsers(): void {
    this.userService.getUsers().subscribe({
      next: (response: User[]) => this.users = response,
      error: (error: HttpErrorResponse) => alert(error.message)
    });
  }

  public getPurchases(): void {
    this.purchaseService.getPurchases().subscribe({
      next: (response: Purchase[]) => {
        this.purchases = response;
        this.refreshUserOrders(); // Aggiorna lo storico ordini dopo aver caricato gli acquisti
      },
      error: (error: HttpErrorResponse) => alert(error.message)
    });
  }

  // ----------- AUTH & USER -----------
  public loginUser(form: NgForm): void {
    const email = form.value.email;
    const user = this.users.find(u => u.email === email);
    if (user) {
      this.user = user;
      this.loggedIn = true;
      this.refreshUserOrders();
      form.resetForm();
      this.closeModal('userModal');
    } else {
      window.alert("Utente non trovato");
    }
  }

  public registerUser(form: NgForm): void {
    this.userService.addUser(form.value).subscribe({
      next: (newUser: User) => {
        this.getUsers();
        this.user = newUser;
        this.loggedIn = true;
        form.resetForm();
        this.closeModal('userModal');
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
        form.resetForm();
      }
    });
  }

  public logout(): void {
    this.loggedIn = false;
    this.user = null;
    this.cart = [];
    this.userOrders = [];
    this.closeModal('orderHistoryModal');
  }

  private refreshUserOrders(): void {
    if (this.user && this.user.id !== undefined) {
      this.userOrders = this.purchases.filter(p => p.buyerId === this.user!.id);
    } else {
      this.userOrders = [];
    }
  }

  // ----------- CART (CARRELLO) -----------
  public addToCart(product: Product): void {
    if (product.id == null) {
      window.alert("Prodotto non valido.");
      return;
    }
    if (!this.cart.find(p => p.id === product.id)) {
      this.cart.push(product);
    } else {
      window.alert("Il prodotto è già nel carrello!");
    }
  }

  public removeFromCart(product: Product): void {
    if (product.id == null) return;
    this.cart = this.cart.filter(p => p.id !== product.id);
  }

  public totalPrice(): number {
    return this.cart.reduce((sum, p) => sum + p.price, 0);
  }

  // ----------- ORDER (ACQUISTO) -----------
  public placeOrder(): void {
    if (!this.loggedIn || !this.user || this.user.id == null) {
      window.alert("Devi essere loggato per effettuare un ordine!");
      return;
    }
    if (this.cart.length === 0) {
      window.alert("Il carrello è vuoto!");
      return;
    }
    const order: Purchase = {
      price: this.totalPrice(),
      purchaseTime: new Date(),
      buyerId: this.user.id,
      productIds: this.cart.map(p => p.id!)
    };
    this.purchaseService.addPurchase(order).subscribe({
      next: (response: Purchase) => {
        this.getPurchases();  // Aggiorna acquisti e storico ordini
        this.cart = [];
        this.closeModal('cartModal');
        window.alert("Ordine effettuato con successo!");
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  // ----------- SEARCH (RICERCA) -----------
  public searchProduct(): void {
    if (!this.searchKey) {
      this.getProducts();
      return;
    }
    this.productService.getProducts().subscribe({
      next: (response: Product[]) => {
        this.products = response.filter(product =>
          product.name.toLowerCase().includes(this.searchKey.toLowerCase())
        );
      },
      error: (error: HttpErrorResponse) => alert(error.message)
    });
  }

  // ----------- UTILITY -----------
  public getCategoryName(categoryId: number): string {
    return this.categories.find(c => c.id === categoryId)?.categoryName || 'N/A';
  }

  public shortDescription(desc: string): string {
    return desc.length > 100 ? desc.substring(0, 100) + "..." : desc;
  }

  // ----------- MODALS -----------
  public openUserModal(): void { this.openModal('userModal'); }
  public openCartModal(): void { this.openModal('cartModal'); }
  public openOrderHistoryModal(): void { this.openModal('orderHistoryModal'); }

  private openModal(modalId: string): void {
    setTimeout(() => {
      (window as any).$(`#${modalId}`).modal('show');
    });
  }
  private closeModal(modalId: string): void {
    setTimeout(() => {
      (window as any).$(`#${modalId}`).modal('hide');
    });
  }
}
