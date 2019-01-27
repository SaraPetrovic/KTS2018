import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AdministrationComponent } from "./administration.component";
import { AdministrationZoneComponent } from "./components/administration-zone/administration-zone.component";
import { AdministrationLineComponent } from "./components/administration-line/administration-line.component";
import { AdministrationStationComponent } from "./components/administration-station/administration-station.component";
import { AdministrationDocumentAcceptanceComponent } from "./components/administration-document-acceptance/administration-document-acceptance.component";

const administrationRoutes: Routes = [
    {
        path: '',
        component: AdministrationComponent,
        children: [
            {
                path: 'zones',
                component: AdministrationZoneComponent
            },
            {
                path: 'lines',
                component: AdministrationLineComponent
            },
            {
                path: 'stations',
                component: AdministrationStationComponent
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
    }
]

@NgModule({
    imports: [
        RouterModule.forChild(administrationRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class AdministrationRoutingModule{}