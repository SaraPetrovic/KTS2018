import { Injectable, EventEmitter } from '@angular/core';
import { Ticket } from 'src/app/model/ticket';

@Injectable({
  providedIn: 'root'
})
export class ConductorCommunicationService {

  private loadedTicket: Ticket;
  public qrCodeScannedEvent: EventEmitter<string>;
  public checkInEvent: EventEmitter<string>;
  public checkInErrorEvent: EventEmitter<string>;
  public scanErrorEvent: EventEmitter<string>;

  constructor() {
    this.qrCodeScannedEvent = new EventEmitter();
    this.checkInEvent = new EventEmitter();
    this.checkInErrorEvent = new EventEmitter();
    this.scanErrorEvent = new EventEmitter();
  }

  qrCodeScanned(scan: string){
    this.qrCodeScannedEvent.emit(scan);
  }

  ticketLoaded(ticket: Ticket){
    this.loadedTicket = ticket;
  }

  getLoadedTicket(){
    return this.loadedTicket;
  }

  checkIn(vehicleNumber: string){
    this.checkInEvent.emit(vehicleNumber);
  }

  checkInError(error: string){
    this.checkInErrorEvent.emit(error);
  }

  scanError(error: string){
    this.scanErrorEvent.emit(error);
  }
}
