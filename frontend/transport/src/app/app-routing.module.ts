import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LinesComponent } from './lines/lines.component';
import { HomeComponent } from './home/home.component';
import { AdministrationComponent } from './administration/administration.component';
import { AdministrationLineComponent } from './administration-line/administration-line.component';
import { AdministrationStationComponent } from './administration-station/administration-station.component';
import { AdministrationZoneComponent } from './administration-zone/administration-zone.component';
import { ConductorComponent } from './conductor/conductor.component';
import { ZonePopupComponent } from './administration-zone/zone-dialog.component';

const routes: Routes = [
  {
    path: 'lines',
    component: LinesComponent
  },{
    path: 'conductor',
    component: ConductorComponent
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
        component: AdministrationZoneComponent
      },
      {
        path: '',
        redirectTo: 'lines',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: 'zzz',
    component: ZonePopupComponent

  },
  {
    path: '',
    component: HomeComponent
  },
  {
    path: '**',
    redirectTo: ''
  }
];

const zonesRoutes: Routes = [
  {
    path: 'zone-new',
    component: ZonePopupComponent,
    outlet: 'popup'
  },
  /*{
    path: 'zone/:id/delete',
    component: ZoneDeletePopupComponent,
    outlet: 'popup'
  }*/
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
