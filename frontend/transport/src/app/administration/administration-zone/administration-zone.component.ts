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
}
