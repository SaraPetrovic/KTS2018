import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { TransportType } from 'src/app/model/enums/transportType';

@Component({
  selector: 'app-administration-station-form',
  templateUrl: './administration-station-form.component.html',
  styleUrls: ['./administration-station-form.component.css']
})
export class AdministrationStationFormComponent implements OnInit {

  private addStationForm: FormGroup;
  submitted = false;
  private TransportType = TransportType;

  @Input() edit: boolean;
  
  @Output() added = new EventEmitter<any>();
  @Output() canceled = new EventEmitter<void>();

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.addStationForm = this.formBuilder.group({
      stationTransportType: [TransportType.Bus, Validators.required],
      stationName: ['', Validators.required],
      stationAddress: ['', Validators.required]
    });
  }

  
  get f(){return this.addStationForm.controls;}

  onSubmit(){
    this.submitted = true;

    if(this.addStationForm.invalid){
      return;
    }

    this.added.emit(this.addStationForm.value);
    this.submitted = false;
  }

  restart(){
    this.f.stationTransportType.reset({value: 'Bus', disabled: false});
    this.f.stationName.reset({value: '', disabled: false});
    this.f.stationAddress.reset({value: '', disabled: false});
  }
}
