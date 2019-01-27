import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Line } from '../../model/line';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { identifierModuleUrl } from '@angular/compiler';

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
    this.http.post("http://localhost:9003/line", line, httpOptions).subscribe();
  }

  deleteLine(id: number){
    this.http.delete("http://localhost:9003/line/" + id).subscribe();
  }

  updateLine(line: Line){
    this.http.put("http://localhost:9003/line/" + line.id, line, httpOptions).subscribe();
  }
}
