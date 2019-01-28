import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Subject, Observable } from 'rxjs';
import { Pricelist } from 'src/app/model/pricelist';

@Injectable({
  providedIn: 'root'
})
export class PricelistService {

  private registerUrl: String = "http://localhost:9003/priceList";
  private headers = {headers: new HttpHeaders({'Content-Type':  'application/json'})};
  private subject = new Subject<any>();

  constructor(private http: HttpClient) { }

  addPriceList(priceList: Pricelist): Observable<Pricelist> {
    return this.http.post<Pricelist>(`${this.registerUrl}`, priceList, this.headers);
  }
}
