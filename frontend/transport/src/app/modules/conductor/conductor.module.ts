import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConductorCheckinComponent } from './components/conductor-checkin/conductor-checkin.component';
import { ConductorScanComponent } from './components/conductor-scan/conductor-scan.component';
import { ConductorTicketComponent } from './components/conductor-ticket/conductor-ticket.component';
import { ConductorRoutingModule } from './conductor-routing.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgQrScannerModule } from 'angular2-qrscanner';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { ConductorDataService } from './services/conductor-data.service';
import { ConductorComponent } from './conductor.component';

@NgModule({
  declarations: [
    ConductorCheckinComponent,
    ConductorScanComponent,
    ConductorTicketComponent,
    ConductorComponent,
  ],
  imports: [
    CommonModule,
    HttpModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgQrScannerModule,
    ConductorRoutingModule
  ],
  entryComponents: [
    ConductorCheckinComponent
  ],
  exports: [
    ConductorRoutingModule
  ],
  providers: [
    ConductorDataService
  ]
})
export class ConductorModule { }
