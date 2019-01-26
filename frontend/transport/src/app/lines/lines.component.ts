import { Component, OnInit, ViewChild } from '@angular/core';
import { Line } from '../model/line';
import { LineService } from '../_services/line.service';
import { MapComponent } from '../map/map.component';
import { map } from 'rxjs/operators';
import { MapEventsService } from '../_services/map-events.service';

@Component({
  selector: 'app-lines',
  templateUrl: './lines.component.html',
  styleUrls: ['./lines.component.css']
})
export class LinesComponent implements OnInit {
  
  @ViewChild(MapComponent) cityMap: MapComponent;
  private lines: Line[];
  private currentTransportType: string;

  constructor(private lineService: LineService) { }

  ngOnInit() {
    this.currentTransportType = 'bus';
    
    this.lineService.getBusLines()
    .subscribe(data => {
      this.lines = data;
    });
  }

  lineClicked(line: Line){
    this.cityMap.clearStreets();

    line.streetPath.forEach(street => {
      this.cityMap.focusStreet(street);
    });
  }

  changeTransportType(type: string){
    if(type === this.currentTransportType){
      return;
    }
    
    if(type === 'bus'){
      this.lineService.getBusLines().subscribe(data => {
        this.lines = data;
      })
    }else if(type === 'metro'){
      this.lineService.getMetroLines().subscribe(data => {
        this.lines = data;
      })
    }else if(type === 'tram'){
      this.lineService.getTramLines().subscribe(data => {
        this.lines = data;
      })
    }else{
      return;
    }

    this.currentTransportType = type;
  }
}
