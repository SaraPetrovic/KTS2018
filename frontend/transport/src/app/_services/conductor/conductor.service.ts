import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Ticket } from 'src/app/model/ticket';
import { Observable } from 'rxjs';
import { Route } from 'src/app/model/route';

const httpOptions = {
  headers: new HttpHeaders({'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImp0aSI6IjEyMzQiLCJyb2xlIjoiUk9MRV9DTElFTlQifQ.tG95HDEtuVXC70WwAwcVX53tYFBaovEMzgs2p02lWrJgK_3V5mCIPVbL2hEpCQ3xW2qhI80UkZKIhqWpbxncfw'})
};

@Injectable({
  providedIn: 'root'
})
export class ConductorService {

  constructor(private http: HttpClient) { }

  checkTicket(id: string): Observable<Ticket>{
    return this.http.get<Ticket>("http://localhost:9003/rest/ticket/check/" + id, httpOptions);
  }

  checkIn(vehicleNumber: string): Observable<Route>{
    return this.http.get<Route>("http://localhost:9003/rest/conductor/checkin/" + vehicleNumber);
  }
}
