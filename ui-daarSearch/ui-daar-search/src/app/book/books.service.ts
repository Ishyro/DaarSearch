import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class BookService {

  constructor(private http: HttpClient) {}

  // get the book list with a given name or regex..
  getBooks(name: string, page: string) {
    //console.log(`ohayo :: http://localhost:3000/api/book/${name}/${page}`);
    const books = this.http.get(`http://localhost:3000/api/book/${name}/${page}`);
    return books;
  }
  getRegexBooks(name: string, page: string) {
    const regexBooks = this.http.get(`http://localhost:3000/api/book/regex/${name}/${page}`);
    return regexBooks;
  }

  getRecommendation(name: string) {
    const books = this.http.get(`http://localhost:3000/api/recommendation/${name}`);
    return books;
  }
  getRegexRecommendation(name: string) {
    const regexBooks = this.http.get(`http://localhost:3000/api/recommendation/regex/${name}`);
    return regexBooks;
  }
}
