import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LinesComponent } from './lines/lines.component';
import { HomeComponent } from './home/home.component';
import { AdministrationComponent } from './administration/administration.component';
import { AdministrationLineComponent } from './administration/administration-line/administration-line.component';
import { AdministrationStationComponent } from './administration/administration-station/administration-station.component';
import { AdministrationZoneComponent } from './administration/administration-zone/administration-zone.component';
import { ConductorModule } from './modules/conductor/conductor.module';
import { TicketComponent } from './ticket/ticket.component';
import { HostComponent } from './host/host.component';
import { EditProfileComponent } from './profile/edit-profile.component';
import { ProfileComponent } from './profile/profile.component';
import { UserTicketsComponent } from './profile/user-tickets.component';
import { RegistrationComponent } from './registration/registration.component';
import { AdministrationDocumentAcceptanceComponent } from './administration/administration-document-acceptance/administration-document-acceptance.component';
import { FileUploadComponent } from './profile/file-upload.component';

const routes: Routes = [
  {
    path: '',
    component: RegistrationComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: HostComponent
  },
  {
    path: 'ticket',
    component: TicketComponent
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
    component: ProfileComponent,
    children: [
      {
        path: 'edit',
        component: EditProfileComponent
      },
      {
        path: 'tickets',
        component: UserTicketsComponent
      },
      {
        path: 'upload',
        component: FileUploadComponent
      }
    ]
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
        path: 'documents',
        component: AdministrationDocumentAcceptanceComponent
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
