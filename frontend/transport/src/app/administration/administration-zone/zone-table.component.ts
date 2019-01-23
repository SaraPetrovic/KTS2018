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

  zoneEdit(zone : Zone) {
    this.zoneService.onZoneClick(zone);
  }
}
