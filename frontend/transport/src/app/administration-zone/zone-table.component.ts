import { Component, OnInit, Input } from '@angular/core';
import { ZoneService } from '../_services/zones/zone.service';
import { Zone } from '../model/zone';

@Component({
  selector: 'app-zone-table',
  templateUrl: './zone-table.component.html',
  styleUrls: ['./zone-table.component.css']
})
export class ZoneTableComponent implements OnInit {

  @Input() zones: Zone[];
  
  constructor(private zoneService: ZoneService) { }

  ngOnInit() {
  }

  deleteZone(zoneId : number) : void {
    this.zoneService.deleteZone(zoneId).subscribe(
      () => this.ngOnInit());
  }

}
