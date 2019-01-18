import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LinesComponent } from './lines/lines.component';
import { HomeComponent } from './home/home.component';

import { NgxSmartModalModule } from 'ngx-smart-modal';
import { LoginComponent } from './login/login.component';
import { JwtInterceptor } from './_helper/jwt.interceptor';
import { ZonesComponent } from './zones/zones.component';
import { ZonePopupComponent, ZoneDialogComponent } from './zones/zone-dialog.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LinesComponent,
    HomeComponent,
    LoginComponent,
    ZonesComponent,
    ZoneDialogComponent,
    ZonePopupComponent
  ],
  entryComponents: [
    ZoneDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxSmartModalModule.forRoot(),
    NgbModule.forRoot()
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
