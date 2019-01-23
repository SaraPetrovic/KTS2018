import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LinesComponent } from './lines/lines.component';
import { HomeComponent } from './home/home.component';
import { AdministrationComponent } from './administration/administration.component';
import { AdministrationLineComponent } from './administration/administration-line/administration-line.component';
import { AdministrationStationComponent } from './administration/administration-station/administration-station.component';
import { AdministrationZoneComponent } from './administration/administration-zone/administration-zone.component';
import { ConductorComponent } from './conductor/conductor.component';
import { ConductorCheckInComponent } from './conductor/conductor-check-in/conductor-check-in.component';
import { ConductorScanComponent } from './conductor/conductor-scan/conductor-scan.component';
import { ConductorTicketComponent } from './conductor/conductor-ticket/conductor-ticket.component';
import { EditProfileComponent } from './profile/edit-profile.component';

const routes: Routes = [
  {
    path: 'lines',
    component: LinesComponent
  },
  {
    path: 'conductor',
    component: ConductorComponent,
    children: [
      {
        path: 'checkIn',
        component: ConductorCheckInComponent
      },
      {
        path: 'scan',
        component: ConductorScanComponent
      },
      {
        path: 'ticket',
        component: ConductorTicketComponent
      },
      {
        path: '',
        redirectTo: 'checkIn',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: 'profile',
    component: EditProfileComponent
  },
  {
    path: 'administration',
    component: AdministrationComponent,
    children: [
      {
        path: 'lines',
        component: AdministrationLineComponent
      },
      {
        path: 'stations',
        component: AdministrationStationComponent
      },
      {
        path: 'zones',
        component: AdministrationZoneComponent,
      },
      {
        path: '',
        redirectTo: 'lines',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
