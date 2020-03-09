import { Component, OnInit } from '@angular/core';
import { DataSource } from '@angular/cdk/table';
import { BookService } from '../book/books.service';
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


  constructor(public dialog: MatDialog, public bookService: BookService) {}

  ngOnInit() {
  }

  search(form: NgForm) {
    console.log('searching!');
    const searchingName = form.value.book;
    if (searchingName) {
      if ( this.isRegex(searchingName)) {
        this.dataSource = this.bookService.getRegexBooks(form.value.book);
      } else {
        this.dataSource = this.bookService.getBooks(form.value.book);
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
      width: '400px',
      data: {book}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
