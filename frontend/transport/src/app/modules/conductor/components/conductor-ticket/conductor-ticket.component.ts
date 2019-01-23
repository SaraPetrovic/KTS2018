import { Component, OnInit } from '@angular/core';
import { Ticket } from 'src/app/model/ticket';
import { ConductorDataService } from '../../services/conductor-data.service';

@Component({
  selector: 'app-conductor-ticket',
  templateUrl: './conductor-ticket.component.html',
  styleUrls: ['./conductor-ticket.component.css']
})
export class ConductorTicketComponent implements OnInit {

  private ticket: Ticket;

  constructor(private dataService: ConductorDataService) { }

  ngOnInit() {
    this.dataService.currentTicket.subscribe(ticket =>{
      this.ticket = ticket;
    });
  }

}
