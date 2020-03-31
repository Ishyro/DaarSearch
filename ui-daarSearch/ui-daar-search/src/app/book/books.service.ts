import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject, Observable } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class BookService {

  constructor(private http: HttpClient) {}

  // get the book list with a given name or regex..
  getBooks(name: string, page: string) {
    // return observable
    //console.log(`ohayo :: http://localhost:3000/api/book/${name}/${page}`);
    const books = new Observable( observer => {
        this.http.get(`http://localhost:3000/api/book/${name}/${page}`).
        subscribe( response => {
          observer.next(response);
        })
    });
    //const books = this.http.get(`http://localhost:3000/api/book/${name}/${page}`);
    return books;
  }
  getRegexBooks(name: string, page: string) {
    const regexBooks = new Observable( observer => {
        this.http.get(`http://localhost:3000/api/book/regex/${name}/${page}`).
        subscribe( response => {
          console.log(response);
          observer.next(response);
        })
    });

    //const regexBooks = this.http.get(`http://localhost:3000/api/book/regex/${name}/${page}`);
    return regexBooks;
  }

  getRecommendation(name: string, page: string) {
    const recoBooks = new Observable( observer => {
        this.http.get(`http://localhost:3000/api/recommendation/${name}/${page}`).
        subscribe( response => {
          console.log(response);
          observer.next(response);
        })
    });
    //const books = this.http.get(`http://localhost:3000/api/recommendation/${name}/${page}`);
    return recoBooks;
  }

}
