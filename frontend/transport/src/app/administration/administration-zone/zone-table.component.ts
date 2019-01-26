import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';
import { Zone } from 'src/app/model/zone';
import { ZoneService } from 'src/app/_services/zones/zone.service';
import { AdministrationZoneComponent } from './administration-zone.component';


@Component({
  selector: 'app-zone-table',
  templateUrl: './zone-table.component.html',
  styleUrls: ['./zone-table.component.css']
})
export class ZoneTableComponent implements OnInit {

  @Input() zones: Zone[];
  @Output() deleteButton = new EventEmitter();
  
  constructor(private zoneService: ZoneService) { }

  ngOnInit() {
  }

  

  zoneEdit(zone : Zone) {
    this.zoneService.onZoneClick(zone.id);
  }
  

}
