import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LinesComponent } from './lines/lines.component';
import { HomeComponent } from './home/home.component';
import { AdministrationComponent } from './administration/administration.component';
import { AdministrationLineComponent } from './administration/administration-line/administration-line.component';
import { AdministrationStationComponent } from './administration/administration-station/administration-station.component';
import { AdministrationZoneComponent } from './administration/administration-zone/administration-zone.component';
import { EditProfileComponent } from './profile/edit-profile.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'lines',
    component: LinesComponent
  },
  {
    path: 'conductor',
    loadChildren: './modules/conductor/conductor.module#ConductorModule'
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
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
