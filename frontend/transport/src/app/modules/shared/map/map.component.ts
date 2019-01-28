import { Component, OnInit, Directive, ElementRef, HostListener, Output, EventEmitter, AfterViewInit, Renderer2 } from '@angular/core';
import { MapEventsService } from './services/map-events/map-events.service';
import { Point } from 'src/app/model/point';
import { StationService } from 'src/app/_services/station/station.service';


@Directive({
  selector: '.street'
})
export class StreetClickedDirective{

  constructor(private el: ElementRef, private mapEventsService: MapEventsService){}

  @HostListener('click') streetClicked(){
    this.mapEventsService.streetClickedEvent(this.el.nativeElement.attributes['name'].value);
  }
}


@Directive({
  selector: '.tempStanica'
})
export class DrowTempStationDirective{
  constructor(private el: ElementRef,
              private mapEventService: MapEventsService,
              private renderer: Renderer2){
    
    this.mapEventService.drowTempStationEvent.subscribe((point: Point) => this.drowTempStation(point))
    this.mapEventService.closeTempStationEvent.subscribe(() => this.closeTempStation())
  }

  closeTempStation(){
    this.renderer.setAttribute(this.el.nativeElement, 'style', 'display: none;')
  }

  drowTempStation(point: Point){
    this.renderer.setAttribute(this.el.nativeElement, 'cx', point.x.toString());
    this.renderer.setAttribute(this.el.nativeElement, 'cy', point.y.toString());
    this.renderer.setAttribute(this.el.nativeElement, 'style', 'display: inline;');
  }
}

@Directive({
  selector: '.stanice'
})
export class DrowStationDirective{
  constructor(private el: ElementRef, 
              private mapEventsService: MapEventsService,
              private renderer: Renderer2){
    this.mapEventsService.drowStationEvent.subscribe( (point: Point) => this.drowStation(point) )
  }

  drowStation(point: Point){
    let station = this.renderer.createElement('circle', 'svg');
    this.renderer.setAttribute(station, 'cx', point.x.toString());
    this.renderer.setAttribute(station, 'cy', point.y.toString());
    this.renderer.setAttribute(station, 'r', '6');
    this.renderer.setAttribute(station, 'fill', 'yellow');
    this.renderer.setAttribute(station, 'stroke', 'red');
    this.renderer.setAttribute(station, 'stroke-width', '1');
    this.renderer.setAttribute(station, 'z-index', '-1');
    this.renderer.setAttribute(station, 'class', 'station');
    this.renderer.setAttribute(station, 'name', '1');
    this.renderer.appendChild(this.el.nativeElement, station);
  }
}

@Directive({
  selector: '.station'
})
export class StationEnterDirective{
  constructor(private el: ElementRef, private mapEventsService: MapEventsService){}

  @HostListener('click', ['$event']) stationEntered(e: any){
    this.mapEventsService.stationEnteredEvent(e);
  }
}

@Directive({
  selector: '.street'
})
export class StreetEnterDirective{

  constructor(private el: ElementRef, private mapEventsService: MapEventsService){}

  @HostListener('click', ['$event']) streetEntered(e: any){
    this.mapEventsService.streetEnteredEvent(e);
  }
}



@Directive({
  selector: ".street",
})
export class StreetEventDirective{
  constructor(private el: ElementRef, private mapEventsService: MapEventsService){
    this.mapEventsService.focusStreet.subscribe(street => this.focusStreet(street));
    this.mapEventsService.clearStreet.subscribe(() => this.clearStreet());
    this.mapEventsService.toggleStreet.subscribe(street => this.toggleStreet(street));
  }

  clearStreet(){
    this.el.nativeElement.style.fill = 'yellow';
  }

  focusStreet(street: string){
    if(this.el.nativeElement.attributes['name'].value === street){
      this.el.nativeElement.style.fill = 'red';
    }
  }

  toggleStreet(street: string){
    if(this.el.nativeElement.attributes['name'].value === street){
      if(this.el.nativeElement.style.fill === 'red'){
        this.el.nativeElement.style.fill = 'yellow';
      }else{
        this.el.nativeElement.style.fill = 'red';
      }
    }
  }
}


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  @Output() streetEnter: EventEmitter<string>;
  @Output() streetClick: EventEmitter<string>;

  constructor(private mapEventsService: MapEventsService, private stationService: StationService) { 
    this.streetClick = new EventEmitter<string>();
    this.streetEnter = new EventEmitter<string>();
  }

  ngOnInit() {
    this.mapEventsService.streetClicked.subscribe(street => this.streetClicked(street));
    this.mapEventsService.streetEntered.subscribe(street => this.streetEntered(street));

    this.stationService.getStations().subscribe(data => {
      data.forEach(element => {
        this.drowStation(new Point({x: element.location.x, y:element.location.y}));
      });
    })
  }

  streetClicked(street: string){
    this.streetClick.emit(street);
  }


  focusStreet(street: string){
    this.mapEventsService.focusStreetEvent(street);
  }

  toggleStreet(street: string){
    this.mapEventsService.toggleStreetEvent(street);
  }

  clearStreets(){
    this.mapEventsService.clearStreetEvent();
  }

  streetEntered(e){
    this.streetEnter.emit(e);
  }


  drowStation(point: Point){
    this.mapEventsService.drowStation(point);
  }

  drowTempStation(point: Point){
    this.mapEventsService.drowTempStation(point);
  }

  closeTempStation(){
    this.mapEventsService.closeTempStation();
  }
}
