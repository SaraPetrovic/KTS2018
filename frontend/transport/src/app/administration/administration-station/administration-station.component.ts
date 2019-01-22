import { Component, OnInit, ViewChild } from '@angular/core';
import { StationService } from '../../_services/station.service';
import { Station } from '../../model/station';
import { MapComponent } from '../../map/map.component';
import { Point } from '../../model/point';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-administration-station',
  templateUrl: './administration-station.component.html',
  styleUrls: ['./administration-station.component.css']
})
export class AdministrationStationComponent implements OnInit {

  private addingStation: Station = null;
  private stations: Station[];
  private addStationForm: FormGroup;
  submitted = false;

  @ViewChild(MapComponent) map: MapComponent;

  constructor(private stationService: StationService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.stationService.getStations()
      .subscribe( data => this.stations = data );

    this.addStationForm = this.formBuilder.group({
      stationName: ['', Validators.required]
    });
  }

  get f(){return this.addStationForm.controls;}

  onSubmit(){
    this.submitted = true;

    if(this.addStationForm.invalid){
      return;
    }

    this.addingStation.name = this.f.stationName.value;
    console.log(this.addingStation);
    this.submitted = false;
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

    this.map.drowStation(new Point({x:svgP.x, y:svgP.y}));
  }

  addStation(){
    this.addingStation = new Station();
    this.addingStation.location = new Point();
  }

}
