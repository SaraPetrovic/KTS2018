import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Station } from '../../model/station';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class StationService {

  constructor(private http: HttpClient) { }

  getStations(): Observable<Station[]>{
    return this.http.get<Station[]>("http://localhost:9003/station");
  }

  addStation(station: Station){
    this.http.post("http://localhost:9003/station", station, httpOptions).subscribe();
  }

  updateStation(station: Station){
    this.http.put("http://localhost:9003/station/" + station.id, station).subscribe();
  }

  deleteStation(id: number){
    this.http.delete("http://localhost:9003/station/" + id).subscribe();
  }
}
