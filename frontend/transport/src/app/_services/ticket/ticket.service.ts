import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Zone } from 'src/app/model/zone';
import { Line } from 'src/app/model/line';
import { Route } from 'src/app/model/route';
import { Ticket } from 'src/app/model/ticket';
import { TransportType } from 'src/app/model/enums/transportType';

const headers = {headers: new HttpHeaders({'Content-Type':  'application/json'})};
  

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private http: HttpClient) { }

  getZones(): Observable<Zone[]>{
    return this.http.get<Zone[]>("http://localhost:9003/zone");
  }

  getLines(id: number, transportType): Observable<Line[]>{
    return this.http.get<Line[]>("http://localhost:9003/line/zone/" + id + "/" + transportType);
  }

  getRoutes(id): Observable<Route[]>{
    return this.http.get<Route[]>("http://localhost:9003/route/line/" + id);
  }

  butTicket(ticket){
    return this.http.post("http://localhost:9003/rest/ticket/buy", ticket, headers);
  }
}
