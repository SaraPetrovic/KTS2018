import { Injectable, Component, Inject, PLATFORM_ID, Injector } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ZoneService } from './zone.service';
import { Zone } from 'src/app/model/zone';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
    providedIn: 'root'
})

export class ZonePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        @Inject(PLATFORM_ID) private platformId: Object,
        private injector:Injector,
        private modalService: NgbModal,
        private router: Router,
        private zoneService: ZoneService

    ) {
        if (isPlatformBrowser(this.platformId)){
            this.modalService = this.injector.get(NgbModal);
        }
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        console.log('aaaaaa');
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.zoneService.getZone(id)
                    .subscribe((zoneResponse: Zone) => {
                        const zone: Zone = zoneResponse;
                        this.ngbModalRef = this.zoneModalRef(component, zone);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.zoneModalRef(component, new Zone());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    zoneModalRef(component: Component, zone: Zone): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.zone = zone;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
