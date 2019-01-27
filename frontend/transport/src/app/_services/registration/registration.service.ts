import { Injectable } from '@angular/core';
import { User } from '../../model/user';
import { Observable, Subject, throwError } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private registerUrl: String = "http://localhost:9003/user/register";
  private headers = {headers: new HttpHeaders({'Content-Type':  'application/json'})};
  private subject = new Subject<any>();

  constructor(private http: HttpClient) { }

  registerUser(u: User): Observable<User> {
    return this.http.post<User>(`${this.registerUrl}`, u, this.headers);
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
