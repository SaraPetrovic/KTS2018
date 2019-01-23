import { Routes, RouterModule } from "@angular/router";
import { ConductorCheckinComponent } from "./components/conductor-checkin/conductor-checkin.component";
import { ConductorScanComponent } from "./components/conductor-scan/conductor-scan.component";
import { NgModule } from "@angular/core";
import { ConductorTicketComponent } from "./components/conductor-ticket/conductor-ticket.component";
import { ConductorComponent } from "./conductor.component";

const conductorRoutes: Routes = [
    {
        path: '',
        component: ConductorComponent,
        children: [
            {
                path: 'checkin',
                component: ConductorCheckinComponent
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
                redirectTo: 'checkin',
                pathMatch: 'full'
            }
        ]
    }
]

@NgModule({
    imports: [
        RouterModule.forChild(conductorRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class ConductorRoutingModule{

}