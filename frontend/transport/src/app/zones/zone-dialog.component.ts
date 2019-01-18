import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ZonePopupService } from '../_services/zones/zone-popup.service';
import { Zone } from '../model/zone';
import { ZoneService } from '../_services/zones/zone.service';

@Component({
  selector: 'app-zone-dialog',
  templateUrl: './zone-dialog.component.html',
  styleUrls: ['./zone-dialog.component.css']
})
export class ZoneDialogComponent implements OnInit {

    private zone: Zone;
    display = 'none';

    constructor(private zoneService: ZoneService) { }

    ngOnInit() {
        this.zone.id = 1;
        this.zone.name = 'aa';
    }

    save() {
        //this.isSaving = true;
        if (this.zone.id !== undefined) {
                //this.zoneService.updateZone(this.zone));
        } else {
                this.zoneService.addZone(this.zone);
        }
    }

    onCloseHandled(){
        this.display='none'; 
     }
}

@Component({
  selector: 'jhi-drug-popup',
  template: ''
})
export class ZonePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zonePopupService: ZonePopupService
    ) {}

    ngOnInit() {
        console.log('bbbbb');
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.zonePopupService
                    .open(ZoneDialogComponent as Component, params['id']);
            } else {
                this.zonePopupService
                    .open(ZoneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
