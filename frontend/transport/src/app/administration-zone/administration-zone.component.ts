import { Component, OnInit } from '@angular/core';
import { Zone } from '../model/zone';
import { ZoneService } from '../_services/zones/zone.service';

@Component({
  selector: 'app-administration-zone',
  templateUrl: './administration-zone.component.html',
  styleUrls: ['./administration-zone.component.css']
})
export class AdministrationZoneComponent implements OnInit {
    // static addZone(zone: Zone): any {
    //     throw new Error("Method not implemented.");
    // }

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
