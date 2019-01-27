import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
import { AdministrationLineComponent } from './administration/administration-line/administration-line.component';
import { AdministrationStationComponent } from './administration/administration-station/administration-station.component';
import { AdministrationZoneComponent } from './administration/administration-zone/administration-zone.component';
import { ZoneFormComponent } from './administration/administration-zone/zone-form.component';
import { ZoneTableComponent } from './administration/administration-zone/zone-table.component';
import { TicketComponent } from './ticket/ticket.component';
import { MaterialModule } from './modules/shared/material.module';
import { LineTableComponent } from './line-table/line-table.component';
import { HostComponent } from './host/host.component';
import { EditProfileComponent } from './profile/edit-profile.component';
import { ConductorModule } from './modules/conductor/conductor.module';
import { ProfileComponent } from './profile/profile.component';
import { UserTicketsComponent } from './profile/user-tickets.component';
import { RegistrationComponent } from './registration/registration.component';
import { AdministrationDocumentAcceptanceComponent } from './administration/administration-document-acceptance/administration-document-acceptance.component';
import { PricelistComponent } from './pricelist/pricelist.component';
import { FileUploadComponent } from './profile/file-upload.component';
import { StationTableComponent } from './administration/administration-station/station-table.component';
import { AdministrationLineTableComponent } from './administration/administration-line/administration-line-table.component';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LinesComponent,
    HomeComponent,
    LoginComponent,
    ZoneFormComponent,
    ZoneTableComponent,
    MapComponent,
    DrowStationDirective,
    StreetClickedDirective,
    StreetEnterDirective,
    StreetEventDirective,
    AdministrationComponent,
    AdministrationLineComponent,
    AdministrationStationComponent,
    AdministrationZoneComponent,
    TicketComponent,
    LineTableComponent,
    HostComponent,
    EditProfileComponent,
    ProfileComponent,
    UserTicketsComponent,
    RegistrationComponent,
    AdministrationDocumentAcceptanceComponent,
    PricelistComponent,
    FileUploadComponent,
    StationTableComponent,
    AdministrationLineTableComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MaterialModule,
    BrowserAnimationsModule,
    NgxSmartModalModule.forRoot(),
    AppRoutingModule,
  ],
  entryComponents:[
    HostComponent,
    LoginComponent,
    RegistrationComponent
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
