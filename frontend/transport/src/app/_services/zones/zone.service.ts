import { Injectable } from '@angular/core';
import { Zone } from 'src/app/model/zone';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { Http } from '@angular/http';



@Injectable({
  providedIn: 'root'
})
export class ZoneService {

  private zoneUrl : String ="http://localhost:9003/zone";
  private headers = {headers: new HttpHeaders({'Content-Type':  'application/json'})};
  
  constructor(private http: HttpClient) { }

  getZones(): Observable<Zone[]>{
    return this.http.get<Zone[]>(`${this.zoneUrl}/all`);
  }

  getZone(id: number): Observable<Zone>{
    return this.http.get<Zone>(`${this.zoneUrl}/${id}`);
  }



  deleteZone(zoneId : number): Observable<any>{
    return this.http.delete<void>(`${this.zoneUrl}/delete/${zoneId}`)
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  addZone(zone : Zone) : Observable<Zone>{
    return this.http.post<Zone>(`${this.zoneUrl}/add`, zone, this.headers)
      .pipe(
        catchError(this.handleError)
      );
  }


  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${JSON.stringify(error.error.errorMessage)}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
