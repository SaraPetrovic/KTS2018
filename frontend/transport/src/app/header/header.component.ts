import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../_services/authentication/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  userLogged: boolean = false;

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit() {

    this.authenticationService.currentUser
      .subscribe(
        value => {value === null ? this.userLogged = false : this.userLogged = true;}
      );
  }
}
