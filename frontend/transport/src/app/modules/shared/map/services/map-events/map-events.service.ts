import { Injectable, EventEmitter } from '@angular/core';
import { Point } from '../../../../../model/point';


@Injectable()
export class MapEventsService {

  public focusStreet: EventEmitter<string>;
  public clearStreet: EventEmitter<void>;
  public streetClicked: EventEmitter<string>;
  public stationClicked: EventEmitter<string>;
  public streetEntered: EventEmitter<string>;
  public stationEntered: EventEmitter<string>;
  public drowStationEvent: EventEmitter<Point>;
  public drowTempStationEvent: EventEmitter<Point>;
  public closeTempStationEvent: EventEmitter<void>;
  public toggleStreet: EventEmitter<string>;

  constructor() {
    this.focusStreet = new EventEmitter();
    this.clearStreet = new EventEmitter();
    this.streetClicked = new EventEmitter();
    this.stationClicked = new EventEmitter();
    this.streetEntered = new EventEmitter();
    this.stationEntered = new EventEmitter();
    this.drowStationEvent = new EventEmitter();
    this.drowTempStationEvent = new EventEmitter();
    this.closeTempStationEvent = new EventEmitter();
    this.toggleStreet = new EventEmitter();
  }

  focusStreetEvent(street: string){
    this.focusStreet.emit(street);
  }

  toggleStreetEvent(street: string){
    this.toggleStreet.emit(street);
  }

  clearStreetEvent(){
    this.clearStreet.emit();
  }

  streetClickedEvent(street: string){
    this.streetClicked.emit(street);
  }

  stationClickedEvent(station: string){
    this.stationClicked.emit(station);
  }

  streetEnteredEvent(street: string){
    this.streetEntered.emit(street);
  }

  stationEnteredEvent(station: string){
    this.stationEntered.emit(station);
  }

  drowStation(point: Point){
    this.drowStationEvent.emit(point);
  }

  drowTempStation(point: Point){
    this.drowTempStationEvent.emit(point);
  }

  closeTempStation(){
    this.closeTempStationEvent.emit();
  }
}
