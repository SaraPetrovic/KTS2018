import { Component, OnInit } from '@angular/core';
import { Ticket } from 'src/app/model/ticket';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { asProviderData } from '@angular/core/src/view';
import { ConductorCommunicationService } from 'src/app/_services/conductor/communication/conductor-communication.service';

@Component({
  selector: 'app-conductor-ticket',
  templateUrl: './conductor-ticket.component.html',
  styleUrls: ['./conductor-ticket.component.css']
})
export class ConductorTicketComponent implements OnInit {

  private ticket: Ticket;

  constructor(private conductorCommunicationService: ConductorCommunicationService){
    
    this.ticket = this.conductorCommunicationService.getLoadedTicket();
  }

  ngOnInit() {
  }

}
