import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { TransportType } from 'src/app/model/enums/transportType';
import { Ticket } from 'src/app/model/ticket';
import { Line } from 'src/app/model/line';

@Component({
  selector: 'app-administration-line-form',
  templateUrl: './administration-line-form.component.html',
  styleUrls: ['./administration-line-form.component.css']
})
export class AdministrationLineFormComponent implements OnInit {

  private addLineForm: FormGroup;
  submitted = false;
  private TransportType = TransportType;

  private edit: boolean;

  @Output() added = new EventEmitter<any>();
  @Output() canceled = new EventEmitter<void>();

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.edit = false;
    this.addLineForm = this.formBuilder.group({
      lineTransportType: [TransportType.Bus, Validators.required],
      lineName: ['', Validators.required],
      lineDescription: ['', Validators.required]
    });
  }

  restart(){
    this.f.lineTransportType.reset({value: 'Bus', disabled: false});
    this.f.lineName.reset({value: '', disabled: false});
    this.f.lineDescription.reset({value: '', disabled: false});
    this.edit = false;
  }

  editLine(line: Line){
    let x = line['transportType'].toLowerCase();
    x = x.charAt(0).toUpperCase() + x.slice(1);
    this.f.lineTransportType.reset({value: x, disabled: true});
    this.f.lineName.reset({value: line.name, disabled: true});
    this.f.lineDescription.reset({value: line.description, disabled: false});
    this.edit = true;
  }

  get f(){ return this.addLineForm.controls; }

  onSubmit(){
    this.submitted = true;

    if(this.addLineForm.invalid){
      return;
    }

    this.added.emit(this.addLineForm.value);
    this.submitted = false;
  }

  cancel(){
    this.restart();
    this.canceled.emit();
  }

}
