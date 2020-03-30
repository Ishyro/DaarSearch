import { Component, OnInit } from '@angular/core';
import { BookService } from '../book/books.service';
import { SearchService}  from './search.service';
import { NgForm } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { DocumentViewComponent } from '../document/document-view/document-view.component';


export interface BookElement {
  name: string;
  content: string;

}

const ELEMENT_DATA: BookElement[] = [
  {name: 'e', content: 'the beach'},
  {name: 'l', content: 'The lord of the rings', },

];


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  displayedColumns: string[] = ['name', 'content'];
  dataSource: any = [];
  dataSourceContent: any = [];
  dataSourceSuggestion: any = [];
  suggestions: string[] = [];

  constructor(public dialog: MatDialog, public bookService: BookService, public searchService: SearchService) {}

  ngOnInit() {
    // get the old history from the database..
    console.log("init logs !");
    //this.suggestions = this.searchService.getSearches();
    //console.log(this.suggestions);
  }

  refresh()  {
    console.log("refresh logs");

    //this.suggestions = this.searchService.getSearches();
    this.searchService.getSearches().subscribe( res => {
      console.log("aloha : "+res);
      this.updateSuggestion(res);
    });
  }

  updateSuggestion(suggestions) {
    this.suggestions = suggestions.reverse().slice(0,3);
  }

  search(form: NgForm) {
    console.log('searching!');
    const searchingName = form.value.book;
    if (searchingName) {
      if ( this.isRegex(searchingName)) {
        this.dataSource = this.bookService.getRegexBooks(form.value.book);
        this.searchService.addSearches(form.value.book);
        //this.suggestions = this.searchService.getSearches();

      } else {
        this.dataSource = this.bookService.getBooks(form.value.book);
        this.searchService.addSearches(form.value.book);
        //this.suggestions = this.searchService.getSearches();

      }
    }
  }

  isRegex(str: string) {
    if (str.includes('*')) {
      return true;
    }
    if (str.includes('+')) {
      return true;
    }
    if (str.includes('|')) {
      return true;
    }
    if (str.includes('.')) {
      return true;
    }
    if (str.includes('$')) {
      return true;
    }
    if (str.includes('^')) {
      return true;
    }
    if (str.includes('[')) {
      return true;
    }
    if (str.includes(']')) {
      return true;
    }
    if (str.includes('{')) {
      return true;
    }
    if (str.includes('}')) {
      return true;
    }
    return false;
  }

  show(book: string) {
    console.log(book);

    const dialogRef = this.dialog.open(DocumentViewComponent, {
      width: '800px',
      data: {book}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
