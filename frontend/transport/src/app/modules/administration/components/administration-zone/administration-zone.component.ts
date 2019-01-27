import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Zone } from 'src/app/model/zone';
import { ZoneService } from 'src/app/_services/zones/zone.service';

@Component({
  selector: 'app-administration-zone',
  templateUrl: './administration-zone.component.html',
  styleUrls: ['./administration-zone.component.css']
})
export class AdministrationZoneComponent implements OnInit {
  
    private zones : Zone[];
    private formLabel: String = "Add zone";
    constructor(private zoneService : ZoneService) { }

    ngOnInit() {
      this.getZones();
    }

    getZones(){
      this.zoneService.getZones()
        .subscribe(data => {
          this.zones = data;
        });
    }

    deleteZone(zone : Zone) {
      this.zoneService.deleteZone(zone.id).subscribe(
        data=>{
          this.zones = this.zones.filter(obj => obj !== zone);
        },
        error => {
          alert(error.error.errorMessage)
        }
      );
    }

    addZone(zone: Zone){

      if(zone.id === undefined)
      {
        this.zoneService.addZone(zone).subscribe(
          (zone) => {
              this.zones.push(zone);
          },
          error => {
              alert(error.error.errorMessage)
          }
        );
      }
      else{
        this.zoneService.editZone(zone).subscribe(
            (data) => {
              this.formLabel = "Add zone";
              console.log(this.formLabel);
              for(let i=0; i < this.zones.length; i++){
                if(this.zones[i].id === data.id){
                  this.zones[i] = data;
                }
              }
            },
            error => {
              alert(error.error.errorMessage)
            } 
        );
      }
  }
}
