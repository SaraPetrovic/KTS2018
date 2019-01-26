import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

import { User } from '../model/user';
import { map } from 'rxjs/operators';
import { Ticket } from '../model/ticket';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  private API_URL = 'localhost:9003';
  private headers = {headers: new HttpHeaders({'Content-Type':  'application/json'})};

  constructor(private http: HttpClient) { 
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User{
    return this.currentUserSubject.value;
  }

  login(username: string, password: string){
    return this.http.post<any>('http://localhost:9003/user/login', {username, password})
    .pipe(map(user => {
      if(user && user.token){
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
      }
      console.log(user);
      return user;
    }));
  }

  logout(){
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  editProfile(user: User): Observable<User>{
    console.log(this.currentUserValue);
    return this.http.put<User>('http://localhost:9003/user', this.currentUserValue, this.headers);
  }

  getTickets(): Observable<Ticket[]>{
    return this.http.get<Ticket[]>('http://localhost:9003/rest/ticket/me', this.headers);
  }

  activateTicket(ticketId: number): Observable<Ticket>{
    return this.http.put<Ticket>('http://localhost:9003/rest/ticket/activate/' + ticketId, this.headers);
  }

  getUsersForVerification(): Observable<User[]>{
    return this.http.get<User[]>('http://localhost:9003/user/verify', this.headers);
  }

  accept(userId : number): Observable<boolean>{
    return this.http.put<boolean>('http://localhost:9003/user/' + userId + "/accept", this.headers);
  }

  decline(userId : number): Observable<boolean>{
    return this.http.put<boolean>('http://localhost:9003/user/' + userId + "/decline", this.headers);
  }
}
