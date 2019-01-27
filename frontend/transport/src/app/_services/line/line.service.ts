import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Line } from '../../model/line';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class LineService {

  constructor(private http: HttpClient) { }

  getLines(): Observable<Line[]>{
    
    return this.http.get<Line[]>("http://localhost:9003/line");
  }

  getBusLines(): Observable<Line[]>{
    return this.http.get<Line[]>("http://localhost:9003/line/bus");
  }

  getMetroLines(): Observable<Line[]>{
    return this.http.get<Line[]>("http://localhost:9003/line/subway");
  }

  getTramLines(): Observable<Line[]>{
    return this.http.get<Line[]>("http://localhost:9003/line/tram");
  }

  addLine(line: Line){
    line.name = "11";
    console.log("usao sam u service", line);
    //this.http.post("http://localhost:9003/line", line, httpOptions).subscribe();
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
  
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
  
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
