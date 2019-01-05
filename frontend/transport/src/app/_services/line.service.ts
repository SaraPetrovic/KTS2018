import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Line } from '../model/line';

@Injectable({
  providedIn: 'root'
})
export class LineService {

  constructor(private http: HttpClient) { }

  getLines(): Observable<Line[]>{
    return this.http.get<Line[]>("http://localhost:9003/line");
  }
}
