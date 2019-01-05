import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LinesComponent } from './lines/lines.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {
    path: 'lines',
    component: LinesComponent
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

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
