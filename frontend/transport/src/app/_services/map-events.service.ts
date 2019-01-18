import { Injectable, EventEmitter } from '@angular/core';
import { Point } from '../model/point';


@Injectable({
  providedIn: 'root'
})
export class MapEventsService {

  public focusStreet: EventEmitter<string>;
  public clearStreet: EventEmitter<void>;
  public streetClicked: EventEmitter<string>;
  public streetEntered: EventEmitter<string>;
  public drowStationEvent: EventEmitter<Point>;

  constructor() {
    this.focusStreet = new EventEmitter();
    this.clearStreet = new EventEmitter();
    this.streetClicked = new EventEmitter();
    this.streetEntered = new EventEmitter();
    this.drowStationEvent = new EventEmitter();
  }

  focusStreetEvent(street: string){
    this.focusStreet.emit(street);
  }

  clearStreetEvent(){
    this.clearStreet.emit();
  }

  streetClickedEvent(street: string){
    this.streetClicked.emit(street);
  }

  streetEnteredEvent(street: string){
    this.streetEntered.emit(street);
  }

  drowStation(point: Point){
    this.drowStationEvent.emit(point);
  }
}
