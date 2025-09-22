import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CategoryService } from './service/Category/category.service';
import { HttpClientModule } from '@angular/common/http';
import { ProductService } from './service/Product/product.service';
import { UserService } from './service/User/user.service';
import { PurchaseService } from './service/Purchase/purchase.service';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [CategoryService, ProductService, PurchaseService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
