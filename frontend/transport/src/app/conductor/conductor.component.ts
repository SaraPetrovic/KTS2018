import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { QrScannerComponent } from 'angular2-qrscanner';
import { ConductorService } from '../_services/conductor/conductor.service';
import { Ticket } from '../model/ticket';
import { ConductorCommunicationService } from '../_services/conductor/communication/conductor-communication.service';
import { Router } from '@angular/router';
import { Route } from '../model/route';

@Component({
  selector: 'app-conductor',
  templateUrl: './conductor.component.html',
  styleUrls: ['./conductor.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ConductorComponent implements OnInit {

  private ticket: Ticket;
  private route: Route;

  private error: string;

  constructor(private conductorService: ConductorService,
     private conductorCommunicationService: ConductorCommunicationService,
     private router: Router) { }

  ngOnInit() { 
    this.conductorCommunicationService.qrCodeScannedEvent.subscribe((result: any) => {
      this.conductorService.checkTicket(result)
        .subscribe(data => {         
          this.conductorCommunicationService.ticketLoaded(data);
          this.router.navigate(['/conductor/ticket']);
        },
        error =>{
          if(error.status === 0){
            this.conductorCommunicationService.scanError("Unable to connect to the server...");
          } else{
            this.conductorCommunicationService.scanError(error.error.errorMessage);
          }
        });
    });

    this.conductorCommunicationService.checkInEvent.subscribe((vehicleNumber: any) => {
      this.conductorService.checkIn(vehicleNumber)
        .subscribe(data => {
          this.route = data;
          this.router.navigate(['/conductor/scan']);
        },
        error => {
          if(error.status === 0){
            this.conductorCommunicationService.checkInError("Unable to connect to the server...");
          } else{
            this.conductorCommunicationService.checkInError(error.error.errorMessage);
          }
        }
        );
    });
   }
}
