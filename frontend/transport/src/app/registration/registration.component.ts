import { Component, OnInit } from '@angular/core';
import { ErrorStateMatcher } from '@angular/material';
import { FormControl, FormGroupDirective, Validators, FormGroup, FormBuilder, NgForm } from '@angular/forms';
import { RegistrationService } from '../_services/registration/registration.service';
import { User } from '../model/user';
import { Route } from '../model/route';
import { Router } from '@angular/router';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    //const invalidCtrl = !!(control && control.invalid && control.parent.dirty);
    //const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty);

    //return (invalidCtrl || invalidParent);

    return (control && control.parent.get('password').value !== control.parent.get('confirmPassword').value && control.dirty)
  }
}


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  myForm: FormGroup;

  matcher = new MyErrorStateMatcher();

  private newUser: User;

  constructor(private formBuilder: FormBuilder, private registrationService: RegistrationService, private router: Router) {
    this.myForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', [Validators.required, Validators.minLength(4)]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: [''],
      email: ['', [Validators.required, Validators.email]]
    }, { validator: this.checkPasswords });

  }

  get f(){return this.myForm.controls;}

  registerUser() {
    if (this.myForm.invalid) {
      return;
    }

    this.newUser = new User();
    this.newUser.firstName = this.f.firstName.value;
    this.newUser.lastName = this.f.lastName.value;
    this.newUser.username = this.f.username.value;
    this.newUser.password = this.f.password.value;
    this.newUser.email = this.f.email.value;
    
    this.registrationService.registerUser(this.newUser).subscribe(
      data => {
        this.router.navigateByUrl("/");
        console.log("uspeoo");
      },
      error => {
        // ishendlaj exceptione
        console.log(error);
      }
    )
  }

  ngOnInit() {
    
  }

  checkPasswords(group: FormGroup) { // here we have the 'passwords' group
  let pass = group.controls.password.value;
  let confirmPass = group.controls.confirmPassword.value;

  return pass === confirmPass ? null : { notSame: true }
}
}


