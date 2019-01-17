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

  constructor(private lineService: LineService) { }

  ngOnInit() {
    
    this.lineService.getLines()
    .subscribe(data => {
      this.lines = data;
    });
  }

  LineClicked(line: Line){
    this.cityMap.clearStreets();

    line.streetPath.forEach(street => {
      this.cityMap.focusStreet(street);
    });
  }
}
