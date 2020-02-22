import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { SearchComponent } from './search/search.component';
import { FooterComponent } from './footer/footer.component';
import { DocumentListComponent } from './document/document-list/document-list.component';
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
  MatPaginatorModule
} from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { BookComponent } from './book/book.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SearchComponent,
    FooterComponent,
    DocumentListComponent,
    DocumentViewComponent,
    DocumentRankComponent,
    DocumentRandomComponent,
    BookComponent

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
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
