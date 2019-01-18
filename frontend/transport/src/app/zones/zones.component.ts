import { Component, OnInit, OnDestroy } from '@angular/core';
import { ZoneService } from '../_services/zones/zone.service';
import { ZonePopupService } from '../_services/zones/zone-popup.service';
import { Zone } from '../model/zone';
import { Router, ActivatedRoute } from '@angular/Router';

@Component({
  selector: 'app-zones',
  templateUrl: './zones.component.html',
  styleUrls: ['./zones.component.css']
})
export class ZonesComponent implements OnInit {

  private zones: Zone[];

  constructor(private zoneService: ZoneService) { }

  ngOnInit() {
    this.zoneService.getZones()
      .subscribe(data => {
        this.zones = data;
        console.log(data);
      });
  }

  deleteZone(zoneId : number) : void {
    this.zoneService.deleteZone(zoneId).subscribe(
      () => this.ngOnInit());
  }

  addZone(zone : Zone): void {
    this.zoneService.addZone(zone).subscribe(
      (zone) => this.zones.push(zone));
  }

}


