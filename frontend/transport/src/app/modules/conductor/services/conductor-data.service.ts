import { Injectable } from "@angular/core";
import { Ticket } from "src/app/model/ticket";
import { BehaviorSubject, Observable } from "rxjs";
import { ConductorModule } from "../conductor.module";

@Injectable()
export class ConductorDataService{

    private ticket: BehaviorSubject<Ticket>;
    currentTicket: Observable<Ticket>;

    constructor(){
        this.ticket = new BehaviorSubject<Ticket>(null);
        this.currentTicket = this.ticket.asObservable();
    }

    scanTicket(ticket: Ticket){
        this.ticket.next(ticket);
    }
}