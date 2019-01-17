import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Station } from '../model/station';

@Injectable({
  providedIn: 'root'
})
export class StationService {

  constructor(private http: HttpClient) { }

  getStations(): Observable<Station[]>{
    return this.http.get<Station[]>("http://localhost:9003/station");
  }
}
