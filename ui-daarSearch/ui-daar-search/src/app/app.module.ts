import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { SearchComponent } from './search/search.component';
import { FooterComponent } from './footer/footer.component';
import { DocumentViewComponent } from './document/document-view/document-view.component';
import { DocumentRankComponent } from './document/document-rank/document-rank.component';
import { DocumentRandomComponent } from './document/document-random/document-random.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import {
  MatInputModule,
  MatCardModule,
  MatButtonModule,
  MatToolbarModule,
  MatExpansionModule,
  MatProgressSpinnerModule,
  MatPaginatorModule,
  MatDialog,
  MatDialogRef,
  MatDialogModule,
  MatTableModule,
  MatAutocompleteModule,
  MatOptionModule
} from '@angular/material';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BookComponent } from './book/book.component';
import { SingupComponent } from './auth/singup/singup.component';
import { LoginComponent } from './auth/login/login.component';
import { AuthInterceptor } from './auth/auth-interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SearchComponent,
    FooterComponent,
    DocumentViewComponent,
    DocumentRankComponent,
    DocumentRandomComponent,
    BookComponent,
    SingupComponent,
    LoginComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    MatToolbarModule,
    MatExpansionModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    HttpClientModule,
    MatDialogModule,
    MatTableModule,
    MatAutocompleteModule,
    MatOptionModule
  ],
  entryComponents: [
    LoginComponent,
    SingupComponent,
    DocumentViewComponent
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
