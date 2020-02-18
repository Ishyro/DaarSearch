import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatToolbarModule} from '@angular/material/toolbar';
import { SearchComponent } from './search/search.component';
import { FooterComponent } from './footer/footer.component';
import { DocumentListComponent } from './document/document-list/document-list.component';
import { DocumentViewComponent } from './document/document-view/document-view.component';
import { DocumentRankComponent } from './document/document-rank/document-rank.component';
import { DocumentRandomComponent } from './document/document-random/document-random.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SearchComponent,
    FooterComponent,
    DocumentListComponent,
    DocumentViewComponent,
    DocumentRankComponent,
    DocumentRandomComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
