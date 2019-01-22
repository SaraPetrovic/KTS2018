import { Component, OnInit, ViewChild } from '@angular/core';
import { LineService } from '../../_services/line.service';

import { Line } from '../../model/line';
import { MapComponent } from '../../map/map.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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
  @ViewChild(MapComponent) map: MapComponent;

  constructor(private lineService: LineService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.lineService.getLines()
      .subscribe( data => this.lines = data );

    this.addLineForm = this.formBuilder.group({
      lineName: ['', Validators.required],
      lineDescription: ['', Validators.required]
    });
  }

  get f(){ return this.addLineForm.controls; }

  onSubmit(){
    this.submitted = true;

    if(this.addLineForm.invalid){
      return;
    }

    console.log(this.addLineForm.value);
    this.submitted = false;
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

  streetClicked(street: string){
    if(this.addingLine !== null){
      this.addingLine.streetPath.push(street);
      this.map.focusStreet(street);
    }
  }

}
