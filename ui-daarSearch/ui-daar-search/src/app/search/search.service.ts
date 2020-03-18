import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject, Observable } from 'rxjs';



@Injectable({ providedIn: 'root' })
export class SearchService {

  constructor(private http: HttpClient) {}

  // get the book list with a given name or regex..
  getSearches() {
    console.log("requesting searches");
    var nameArray;
    const searches = new Observable( observer => {
      this.http.get('http://localhost:3000/api/suggestion/logs')
      .subscribe( response => {
        console.log(response);
         nameArray = response.result.map( result => {
          return result.name;
        });
        console.log(nameArray);
        observer.next(nameArray);
      });
    });
    return searches;



    //return nameArray;
    //this.http.get(`http://localhost:3000/api/book/hi`);
    //console.log("searches are :" + searches);
    //return searches;
  }

  addSearches(search: string) {
    const sugg = { name: search };
    this.http
      .post('http://localhost:3000/api/suggestion/logs', sugg)
      .subscribe(response => {
        console.log(response);
      });
  }
}
