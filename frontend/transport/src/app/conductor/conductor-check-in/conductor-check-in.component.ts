import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConductorCommunicationService } from 'src/app/_services/conductor/communication/conductor-communication.service';


@Component({
  selector: 'app-conductor-check-in',
  templateUrl: './conductor-check-in.component.html',
  styleUrls: ['./conductor-check-in.component.css']
})
export class ConductorCheckInComponent implements OnInit {

  private checkInForm: FormGroup;
  submitted = false;
  private error = false;
  private errorMessage: string;

  constructor(private conductorCommunicationService: ConductorCommunicationService, private router: Router, private formBuidler: FormBuilder) { }

  ngOnInit() {

    this.conductorCommunicationService.checkInErrorEvent.subscribe(
      (error: any) => {
        this.error = true;
        this.errorMessage = error;
      }
    );
    
    this.checkInForm = this.formBuidler.group({
      vehicleNumber: ['', [Validators.required]]
    });
  }

  get f(){ return this.checkInForm.controls; }

  onSubmit(){
    this.submitted = true;

    console.log(this.checkInForm);

    if(this.checkInForm.invalid){
      this.submitted = false;
      
      return;
    }

    this.conductorCommunicationService.checkIn(this.f.vehicleNumber.value);

    this.submitted = false;
  }
}
