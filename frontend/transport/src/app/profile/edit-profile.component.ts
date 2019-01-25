import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, MinLengthValidator } from '@angular/forms';
import { User } from '../model/user';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../_services/authentication.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

  private editProfileForm : FormGroup;
  private user : User;
  submitError = false;
  private loggedUserSubscription : Subscription;
  private repeatedPass: String;
  passwordError = false;

  constructor(private formBuilder: FormBuilder,
              private userService: AuthenticationService) { }

  ngOnInit() {
    this.editProfileForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]],
      repeatedPassword: ['', [Validators.required, Validators.minLength(8)]]
    });

    this.user = this.userService.currentUserValue;
    console.log(this.user);

    this.repeatedPass = this.user.password;
  }

  get f(){ return this.editProfileForm.controls; }

  onSubmit(){
    this.submitError = true;
  
    if(this.repeatedPass !== this.user.password){
      this.passwordError = true;
      return;
    }
    if(this.editProfileForm.invalid){
      return;
    }
    this.submitError = false;
  }

  editProfile(){
    this.userService.editProfile(this.user);
  }
}
