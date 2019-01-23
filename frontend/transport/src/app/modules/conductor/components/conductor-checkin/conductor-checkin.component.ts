import { Component, OnInit } from '@angular/core';
import { ConductorService } from 'src/app/_services/conductor/conductor.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-conductor-checkin',
  templateUrl: './conductor-checkin.component.html',
  styleUrls: ['./conductor-checkin.component.css']
})
export class ConductorCheckinComponent implements OnInit {

  private checkInForm: FormGroup;
  submitted = false;
  private error = false;
  private errorMessage: string

  constructor(private conductorService: ConductorService,
              private router: Router,
              private formBuidler: FormBuilder) { }

  ngOnInit() {
    this.checkInForm = this.formBuidler.group({
      vehicleNumber: ['', Validators.required]
    })
  }

  get f(){return this.checkInForm.controls;}


  onSubmit(){
    this.submitted = true;

    if(this.checkInForm.invalid){
      this.submitted = false;
      return;
    }

    this.conductorService.checkIn(this.f.vehicleNumber.value).subscribe(
      data => {
        this.submitted = false;
        this.router.navigate(['/conductor/scan']);
      },
      error => {
        this.error = true;
        if(error.status === 0){
          this.errorMessage = "Unable to connect to the server..."
        }else{
          this.errorMessage = error.error.errorMessage;
        }
      }
    )
  }  
}
