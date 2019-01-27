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
import { TicketComponent } from './ticket/ticket.component';
import { MaterialModule } from './modules/shared/material.module';
import { LineTableComponent } from './line-table/line-table.component';
import { HostComponent } from './host/host.component';
import { EditProfileComponent } from './profile/edit-profile.component';
import { ConductorModule } from './modules/conductor/conductor.module';
import { ProfileComponent } from './profile/profile.component';
import { UserTicketsComponent } from './profile/user-tickets.component';
import { RegistrationComponent } from './registration/registration.component';
import { PricelistComponent } from './pricelist/pricelist.component';
import { FileUploadComponent } from './profile/file-upload.component';
import { MapModule } from './modules/shared/map/map.module';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LinesComponent,
    HomeComponent,
    LoginComponent,
    TicketComponent,
    LineTableComponent,
    HostComponent,
    EditProfileComponent,
    ProfileComponent,
    UserTicketsComponent,
    RegistrationComponent,
    PricelistComponent,
    FileUploadComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MaterialModule,
    MapModule,
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
