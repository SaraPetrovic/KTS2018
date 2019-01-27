import { Component, OnInit } from '@angular/core';
import { StationService } from 'src/app/_services/station.service';
import { Station } from 'src/app/model/station';

@Component({
  selector: 'app-station-table',
  templateUrl: './station-table.component.html',
  styleUrls: ['./station-table.component.css']
})
export class StationTableComponent implements OnInit {

  private stations: Station[];
  constructor(private stationService: StationService) { }

  ngOnInit() {
    this.stationService.getStations()
      .subscribe( data => this.stations = data );

  }

}
