import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { NgQrScannerModule } from 'angular2-qrscanner';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LinesComponent } from './lines/lines.component';
import { HomeComponent } from './home/home.component';

import { NgxSmartModalModule } from 'ngx-smart-modal';
import { LoginComponent } from './login/login.component';
import { JwtInterceptor } from './_helper/jwt.interceptor';
import { MapComponent, StreetEventDirective, StreetClickedDirective, StreetEnterDirective, DrowStationDirective } from './map/map.component';
import { AdministrationComponent } from './administration/administration.component';
import { AdministrationLineComponent } from './administration-line/administration-line.component';
import { AdministrationStationComponent } from './administration-station/administration-station.component';
import { AdministrationZoneComponent } from './administration-zone/administration-zone.component';
import { ZonesComponent } from './zones/zones.component';
import { ConductorComponent } from './conductor/conductor.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LinesComponent,
    HomeComponent,
    LoginComponent,
    MapComponent,
    StreetEventDirective,
    StreetClickedDirective,
    StreetEnterDirective,
    DrowStationDirective,
    AdministrationComponent,
    AdministrationLineComponent,
    AdministrationStationComponent,
    AdministrationZoneComponent,
    ZonesComponent,
    ConductorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxSmartModalModule.forRoot(),
    NgQrScannerModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
