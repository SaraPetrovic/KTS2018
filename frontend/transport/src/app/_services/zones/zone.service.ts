import { Injectable } from '@angular/core';
import { Zone } from 'src/app/model/zone';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError, Subject } from 'rxjs';
import { Http } from '@angular/http';



@Injectable({
  providedIn: 'root'
})
export class ZoneService {

  private zoneUrl : String ="http://localhost:9003/rest/zone";
  private headers = {headers: new HttpHeaders({'Content-Type':  'application/json'})};
  private subject = new Subject<any>();
  
  constructor(private http: HttpClient) { }

  getZones(): Observable<Zone[]>{
    return this.http.get<Zone[]>("http://localhost:9003/zone");
  }

  getZone(id: number): Observable<Zone>{
    return this.http.get<Zone>("http://localhost:9003/zone" + id);
  }

  deleteZone(zoneId: number): Observable<boolean>{
    return this.http.delete<boolean>(`${this.zoneUrl}/${zoneId}`);
  }

  addZone(zone: Zone): Observable<Zone>{
    return this.http.post<Zone>(`${this.zoneUrl}`, zone, this.headers);
  }

  editZone(zone: Zone): Observable<Zone>{
    return this.http.put<Zone>(`${this.zoneUrl}/${zone.id}`, zone, this.headers);
  }

  onZoneClick(zoneId: number){
    this.subject.next(zoneId);
  }

  getClickedZone(): Observable<any>{
    return this.subject.asObservable();
  }

  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // server-side error
      if(error.error.errorMessage != null){
        errorMessage = `Error Code: ${error.status}\nMessage: ${JSON.stringify(error.error.errorMessage)}`;
        window.alert(errorMessage);
        return throwError(errorMessage);
      }
    }
  }
}
