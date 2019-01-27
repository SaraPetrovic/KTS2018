import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { StationService } from '../../../../../_services/station/station.service';
import { Station } from '../../../../../model/station';

@Component({
  selector: 'app-station-table',
  templateUrl: './station-table.component.html',
  styleUrls: ['./station-table.component.css']
})
export class StationTableComponent implements OnInit {

  private stations: Station[];

  @Output() deleted = new EventEmitter<Station>();
  constructor(private stationService: StationService) { }

  ngOnInit() {
    this.stationService.getStations()
      .subscribe( data => this.stations = data );

  }

  
  delete(station: Station){
    this.deleted.emit(station);
  }

}
