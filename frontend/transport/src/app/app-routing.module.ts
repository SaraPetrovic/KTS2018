import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LinesComponent } from './lines/lines.component';
import { HomeComponent } from './home/home.component';
import { ZonesComponent } from './zones/zones.component';
import { ZonePopupComponent } from './zones/zone-dialog.component';

const routes: Routes = [
  {
    path: 'lines',
    component: LinesComponent
  },
  {
    path: 'zones',
    component: ZonesComponent
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
