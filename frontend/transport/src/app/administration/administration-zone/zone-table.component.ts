import { Component, OnInit, Input } from '@angular/core';
import { Zone } from 'src/app/model/zone';
import { ZoneService } from 'src/app/_services/zones/zone.service';


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
    this.zoneService.deleteZone(zoneId).subscribe();
  }

  zoneEdit(zone : Zone) {
    
  }
}
