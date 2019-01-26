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
import { AdministrationLineComponent } from './administration/administration-line/administration-line.component';
import { AdministrationStationComponent } from './administration/administration-station/administration-station.component';
import { AdministrationZoneComponent } from './administration/administration-zone/administration-zone.component';
import { ZoneFormComponent } from './administration/administration-zone/zone-form.component';
import { ZoneTableComponent } from './administration/administration-zone/zone-table.component';
import { EditProfileComponent } from './profile/edit-profile.component';
import { ConductorModule } from './modules/conductor/conductor.module';
import { ProfileComponent } from './profile/profile.component';
import { UserTicketsComponent } from './profile/user-tickets.component';
import { RegistrationComponent } from './registration/registration.component';
import { AdministrationDocumentAcceptanceComponent } from './administration/administration-document-acceptance/administration-document-acceptance.component';


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
    EditProfileComponent,
    ProfileComponent,
    UserTicketsComponent,
    RegistrationComponent,
    AdministrationDocumentAcceptanceComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxSmartModalModule.forRoot(),
    AppRoutingModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
