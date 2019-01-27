import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user';
import { AuthenticationService } from 'src/app/_services/authentication/authentication.service';

@Component({
  selector: 'app-administration-document-acceptance',
  templateUrl: './administration-document-acceptance.component.html',
  styleUrls: ['./administration-document-acceptance.component.css']
})
export class AdministrationDocumentAcceptanceComponent implements OnInit {

  private users: User[];

  constructor(private userService: AuthenticationService) { }

  ngOnInit() {
    this.userService.getUsersForVerification().subscribe(
      data => {
        this.users = data;
        console.log("USERS");
        console.log(data);
      });
  }

  accept(user : User){
    this.userService.accept(user.id).subscribe(
      data=>{
        this.users = this.users.filter(obj => obj !== user);
    });
  }

  decline(user : User){
    this.userService.decline(user.id).subscribe(
      data=>{
        this.users = this.users.filter(obj => obj !== user);
    });
  }
}
