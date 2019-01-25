import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../_services/authentication.service';
import { Ticket } from '../model/ticket';

@Component({
  selector: 'app-user-tickets',
  templateUrl: './user-tickets.component.html',
  styleUrls: ['./user-tickets.component.css']
})
export class UserTicketsComponent implements OnInit {

  private tickets: Ticket[];
  private currentDate: Date;
  constructor(private userService: AuthenticationService) { }

  ngOnInit() {
    this.currentDate = new Date();

    this.userService.getTickets().subscribe(
      (tickets) => {
        this.tickets = tickets;
        console.log(tickets);
      });
  }

  getTickets(){
    this.userService.getTickets().subscribe(
      (tickets) => {
        this.tickets = tickets;
        console.log(tickets);
      });
  }

  activateTicket(ticketId: number){
    this.userService.activateTicket(ticketId).subscribe(
      (ticket) => {
        this.tickets.forEach(element => {
          if(element.id === ticket.id){
            element = ticket;
          }
        });
      }
    );
  }
}
