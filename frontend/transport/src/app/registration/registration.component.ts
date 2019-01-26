import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { RegistrationService } from '../_services/registration.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  private registrationForm: FormGroup;
  submitted = false;
  error = '';
  
  constructor(private registrationService: RegistrationService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(4)]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
      email: ['', [Validators.required, Validators.email]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required]
    }, this.pwdMatchValidator);
  }

  onSubmit() {
    this.submitted = true;

    if(this.registrationForm.invalid){
      return;
    }

    this.submitted = false;
  }

  get f(){ return this.registrationForm.controls; }

  pwdMatchValidator(frm: FormGroup) {
    return frm.get('password').value === frm.get('confirmPassword').value
       ? null : {'mismatch': true};
 }
}
