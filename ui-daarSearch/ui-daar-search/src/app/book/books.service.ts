import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class BookService {

  constructor(private http: HttpClient) {}

  // get the book list with a given name or regex..
  getBooks(name: string) {
    const books = this.http.get(`http://localhost:3000/api/book/${name}`);
    return books;
  }
  getRegexBooks(name: string) {
    const regexBooks = this.http.get(`http://localhost:3000/api/book/regex/${name}`);
    return regexBooks;
  }
}
