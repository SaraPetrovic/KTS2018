import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../_services/authentication/authentication.service';
import { User } from '../model/user';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  userLogged: User = null;
  

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.userLogged = null;

    this.authenticationService.currentUser
      .subscribe(
        value => {value === null ? this.userLogged = null : this.userLogged = value;}
      );
  }
}
