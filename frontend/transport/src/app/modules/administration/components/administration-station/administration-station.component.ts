import { Component, OnInit, ViewChild } from '@angular/core';
import { StationService } from '../../../../_services/station/station.service';
import { Station } from '../../../../model/station';
import { Point } from '../../../../model/point';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MapComponent } from 'src/app/modules/shared/map/map.component';
import { TransportType } from 'src/app/model/enums/transportType';
import { AdministrationStationFormComponent } from './administration-station-form/administration-station-form.component';

@Component({
  selector: 'app-administration-station',
  templateUrl: './administration-station.component.html',
  styleUrls: ['./administration-station.component.css']
})
export class AdministrationStationComponent implements OnInit {

  private addingStation: Station = null;
  private editingStation: Station;
  private stations: Station[];
  private edit = false;

  @ViewChild(MapComponent) map: MapComponent;
  @ViewChild(AdministrationStationFormComponent) form: AdministrationStationFormComponent;

  constructor(private stationService: StationService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.stationService.getStations()
      .subscribe( data => this.stations = data );
    
    this.addStation();
  }


  mouseEnter(event: any){
    
    if(this.addingStation === null){
      return;
    }
    
    var svg: any = document.getElementById('novi-sad-map');
    var pt = svg.createSVGPoint();

    pt.x = event.clientX;
    pt.y = event.clientY;

    var svgP = pt.matrixTransform(svg.getScreenCTM().inverse());

    this.addingStation.location.x = svgP.x;
    this.addingStation.location.y = svgP.y;

    this.map.drowTempStation(new Point({x:svgP.x, y:svgP.y}));
  }


  deleteStation(station: Station){
    this.stationService.deleteStation(station.id);
  }

  addStation(){
    this.addingStation = new Station();
    this.addingStation.location = new Point();
  }

  onFormEvent(formdata: any){
    this.addingStation.address = formdata.stationAddress;
    this.addingStation.name = formdata.stationName;
    this.addingStation.vehicleType = TransportType[formdata.stationTransportType];

    this.stationService.addStation(this.addingStation); 
    
    this.addStation();
    this.form.restart();
    this.map.closeTempStation();
  }

  onFormCancel(){
    this.edit = false;
  }

}
