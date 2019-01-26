import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Zone } from '../model/zone';
import { TicketService } from '../_services/ticket/ticket.service';
import { TransportType } from '../model/enums/transportType';
import { Duration } from '../model/enums/duration';
import { Line } from '../model/line';
import { LowerCasePipe } from '@angular/common';
import { Route } from '../model/route';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent implements OnInit {

  private firstFormGroup: FormGroup;
  private secondFormGroup: FormGroup;
  private thirdFormGroup: FormGroup;
  private forthFormGroup: FormGroup;
  private selectedTransportType: TransportType;
  private selectedZone: string;
  private selectedLine: string;
  private selectedDuration: string;
  private selectedRoute: string;
  private zones: Zone[];
  private lines: Line[];
  private routes: Route[];
  TransportType = TransportType;
  Duration = Duration;

  constructor(private ticketService: TicketService, private _formBuilder: FormBuilder) {  }

  ngOnInit() {

    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: new FormControl(undefined, Validators.required)
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: new FormControl(undefined, Validators.required)
    });
    this.thirdFormGroup = this._formBuilder.group({
      thirdCtrl: new FormControl(undefined, Validators.required)
    })
    this.forthFormGroup = this._formBuilder.group({
      forthCtrl: new FormControl(undefined, Validators.required)
    })

    this.firstFormGroup.controls['firstCtrl'].valueChanges.subscribe(
      (value) => {
        if(value){
          this.ticketService.getZones().subscribe(data => {
            this.zones = data;
          })
        }
      }
    )

    this.secondFormGroup.controls['secondCtrl'].valueChanges.subscribe(
      (value) => {
        this.ticketService.getLines(value, this.selectedTransportType).subscribe(data => {
          this.lines = data;
        })
      }
    )

    this.forthFormGroup.controls['forthCtrl'].valueChanges.subscribe(
      (value) => {
        this.ticketService.getRoutes(this.selectedLine).subscribe(data => {
          this.routes = data;
        })
      }
    )

  }

  reset(){
    console.log(TransportType[this.selectedTransportType]);
    console.log(this.selectedZone);
    console.log(this.selectedLine);
    console.log(this.selectedDuration);
    console.log(this.forthFormGroup.valid);

    if(!this.firstFormGroup.valid || !this.secondFormGroup.valid ||
        !this.thirdFormGroup.valid || !this.forthFormGroup.valid){
          return;
        }

    if(this.selectedLine !== 'all' && this.selectedDuration !== 'OneTime'){

      this.ticketService.butTicket({
        "transportType": TransportType[this.selectedTransportType],
        "ticketTemporal": Duration[this.selectedDuration],
        "lineId": this.selectedLine
      }).subscribe();
    }else if(this.selectedLine !== 'all' && this.selectedDuration === 'OneTime'){
        this.ticketService.butTicket({
          "transportType": TransportType[this.selectedTransportType],
          "ticketTemporal": Duration[this.selectedDuration],
          "routeId": this.selectedRoute
        }).subscribe();
    }else{
            
      this.ticketService.butTicket({
        "transportType": TransportType[this.selectedTransportType],
        "ticketTemporal": Duration[this.selectedDuration],
        "zoneId": this.selectedZone
      }).subscribe();
    }
  }

}
