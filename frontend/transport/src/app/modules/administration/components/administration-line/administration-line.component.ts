import { Component, OnInit, ViewChild } from '@angular/core';
import { LineService } from '../../../../_services/line/line.service';

import { Line } from '../../../../model/line';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MapComponent } from 'src/app/modules/shared/map/map.component';
import { TransportType } from 'src/app/model/enums/transportType';
import { AdministrationLineFormComponent } from './administration-line-form/administration-line-form.component';

@Component({
  selector: 'app-administration-line',
  templateUrl: './administration-line.component.html',
  styleUrls: ['./administration-line.component.css']
})
export class AdministrationLineComponent implements OnInit {

  private addLineForm: FormGroup;
  submitted = false;
  private lines: Line[];
  private addingLine: Line = null;
  private editingLine: Line = null;
  private edit = false;
  @ViewChild(MapComponent) map: MapComponent;
  @ViewChild(AdministrationLineFormComponent) form: AdministrationLineFormComponent;

  constructor(private lineService: LineService) { }

  ngOnInit() {
    this.lineService.getLines()
      .subscribe( data => this.lines = data );

    this.createLine();
  }


  createLine(){
    this.addingLine = new Line();
    this.addingLine.streetPath = [];
  }

  addLine(){
    if(this.addingLine !== null){ 
      this.lineService.addLine(this.addingLine);
      this.addingLine = null;
    }
  }

  deleteLine(line: Line){
    this.lineService.deleteLine(line.id);
  }

  editLine(line: Line){
    this.edit = true;
    this.editingLine = line;
    this.form.editLine(line);

    this.map.clearStreets();

    line.streetPath.forEach(element => {
      this.map.focusStreet(element);
    });
  }

  streetClicked(street: string){
    if(this.edit){
      if(this.editingLine !== null){
        this.editingLine.streetPath.push(street);
        this.map.toggleStreet(street);
      }
    }else{
      if(this.addingLine !== null){
        this.addingLine.streetPath.push(street);
        this.map.toggleStreet(street);
      }
    }
  }

  stationClicked(station: string){
    console.log("primecen sam");
    if(this.edit){
      if(this.editingLine !== null){
        let keys = Object.keys(this.editingLine);
        let len = keys.length;
        this.editingLine.stations[len] = station;
        //this.map.toggleStation(station);
      }
    }else{
      if(this.addingLine !== null){
        let keys = Object.keys(this.addingLine);
        let len = keys.length;
        this.addingLine.stations[len] = station;
        //this.map.toggleStation(station);
      }
    }
  }

  onFormEvent(formdata: any){
    if(this.edit){
      this.editingLine.description = formdata.lineDescription;

      this.lineService.updateLine(this.editingLine);
    }else{
      this.addingLine.name = formdata.lineName;
      this.addingLine.description = formdata.lineDescription;
      this.addingLine.vehicleType = TransportType[formdata.lineTransportType];

      this.lineService.addLine(this.addingLine);

    }
    
    this.createLine();
    this.form.restart();
    this.map.clearStreets();
  }

  onFormCancel(){
    this.edit = false;
  }

}
