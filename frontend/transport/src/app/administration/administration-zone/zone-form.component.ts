import { Component, OnInit, OnDestroy, ViewChild, Input } from '@angular/core';
import { ActivatedRoute, ChildActivationEnd, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ZoneTableComponent } from './zone-table.component';
import { Zone } from 'src/app/model/zone';
import { ZoneService } from 'src/app/_services/zones/zone.service';
import { Subscription } from 'rxjs';
import { forEach } from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-zone-form',
  templateUrl: './zone-form.component.html',
  styleUrls: ['./zone-form.component.css']
})
export class ZoneFormComponent implements OnInit {

    private addZoneForm : FormGroup;
    submitError = false;
    private zone: Zone = new Zone();
    @Input() zones : Zone[];
    private disabledOption = "";
    private zoneClickedSubscription : Subscription;
    private formLabel: String = "Add zone";

    constructor(private zoneService: ZoneService, private formBuilder: FormBuilder) { }

    ngOnInit() {
        this.addZoneForm = this.formBuilder.group({
            zoneName: ['', Validators.required],
            subzone: ['']
        });

        this.zoneClickedSubscription = this.zoneService.getClickedZone().subscribe(
            zone => {
                this.zone = zone;
                this.formLabel = "Edit Zone";
                console.log(this.formLabel);
                this.zones.forEach(element => {
                    if(element.id === zone.subZoneId){
                        this.disabledOption = zone.name;
                    }
                }); 
            }
        );
    }

    get f(){ return this.addZoneForm.controls; }

    onSubmit(){
        this.submitError = true;
        if(this.addZoneForm.invalid){
          return;
        }
        this.submitError = false;
        this.zone.name = null;
        this.disabledOption = "";
    }

    onSelect(zoneId : number){
        this.zone.subZoneId = zoneId;
    }

    addZone(): void {
        if(this.formLabel === "Add Zone"){
            this.zoneService.addZone(this.zone).subscribe(
                (zone) => {
                    this.zones.push(zone);
                },
                error => {
                    alert(error.error.errorMessage)
                }
            );
        }else if(this.formLabel === "Edit Zone"){
            this.zoneService.editZone(this.zone).subscribe(
                (zone) => {
                    this.zones.forEach(function (value){
                        if(zone.id === value.id){
                            value = zone;
                        }
                    });
                }
            );
        }
    }
 
    clearForm(){
        this.formLabel = "Add Zone";
        this.disabledOption = "";
    }
    
}
