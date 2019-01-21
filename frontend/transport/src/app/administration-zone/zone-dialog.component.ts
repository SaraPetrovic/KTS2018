import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { ActivatedRoute, ChildActivationEnd, Router } from '@angular/router';
import { ZonePopupService } from '../_services/zones/zone-popup.service';
import { Zone } from '../model/zone';
import { ZoneService } from '../_services/zones/zone.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AdministrationZoneComponent } from './administration-zone.component';

@Component({
  selector: 'app-zone-dialog',
  templateUrl: './zone-dialog.component.html',
  styleUrls: ['./zone-dialog.component.css']
})
export class ZoneDialogComponent implements OnInit {

    private zone: Zone;

    constructor(private zoneService: ZoneService,
         private router: Router,
            private activeModal: NgbActiveModal) { }

    ngOnInit() {
    }

    save() {
        //this.isSaving = true;
        //if (this.zone.id !== undefined) {
                //this.zoneService.updateZone(this.zone));
        //} else {
                //this.zoneService.addZone(this.zone);
        //}

        
    }

    addZone(): void {
        this.zoneService.addZone(this.zone).subscribe();
    }

    clear() {
        this.activeModal.dismiss('cancel');
        this.router.navigateByUrl('http://localhost:4200/administration/zones');
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
