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
  private currentDate: String;
  constructor(private userService: AuthenticationService) { }

  ngOnInit() {
    this.userService.getTickets().subscribe(
      (tickets) => {
        this.tickets = tickets;
        console.log(tickets);
        this.tickets.forEach(element => {
          console.log(element.startDate);
          console.log(element.endTime);
          });
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
