import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LinesComponent } from './lines/lines.component';
import { HomeComponent } from './home/home.component';
import { ConductorModule } from './modules/conductor/conductor.module';
import { TicketComponent } from './ticket/ticket.component';
import { EditProfileComponent } from './profile/edit-profile.component';
import { ProfileComponent } from './profile/profile.component';
import { UserTicketsComponent } from './profile/user-tickets.component';
import { RegistrationComponent } from './registration/registration.component';
import { PricelistComponent } from './pricelist/pricelist.component';
import { FileUploadComponent } from './profile/file-upload.component';
import { LoginComponent } from './login/login.component';
import { PricelistTableComponent } from './pricelist/pricelist-table.component';


const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'registration',
    component: RegistrationComponent
  },
  {
    path: 'pricelist',
    component: PricelistComponent
  },
  {
    path: 'ticket',
    component: TicketComponent
  },
  {
    path: 'notifications',
    component: PricelistTableComponent
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
    loadChildren: './modules/administration/administration.module#AdministrationModule'
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
