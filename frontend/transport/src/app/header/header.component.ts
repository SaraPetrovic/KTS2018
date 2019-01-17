import { Component, OnInit } from '@angular/core';
import { NgxSmartModalService } from 'ngx-smart-modal';
import { AuthenticationService } from '../_services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  userLogged: boolean = false;

  constructor(
    public ngxSmartModalService: NgxSmartModalService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit() {

    this.authenticationService.currentUser
      .subscribe(
        value => {value === null ? this.userLogged = false : this.userLogged = true;}
      );
  }

  closeLogin(){
    this.ngxSmartModalService.closeLatestModal();
  }
}
