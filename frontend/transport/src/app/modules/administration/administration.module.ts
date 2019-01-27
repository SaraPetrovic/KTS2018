import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { AdministrationComponent } from "./administration.component";
import { AdministrationLineComponent } from "./components/administration-line/administration-line.component";
import { AdministrationStationComponent } from "./components/administration-station/administration-station.component";
import { AdministrationZoneComponent } from "./components/administration-zone/administration-zone.component";
import { AdministrationRoutingModule } from "./administration-routing.module";
import { AdministrationDocumentAcceptanceComponent } from "./components/administration-document-acceptance/administration-document-acceptance.component";
import { MapModule } from "../shared/map/map.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AdministrationLineTableComponent } from "./components/administration-line/administration-line-table.component";
import { StationTableComponent } from "./components/administration-station/station-table.component";
import { ZoneFormComponent } from "./components/administration-zone/zone-form.component";
import { ZoneTableComponent } from "./components/administration-zone/zone-table.component";

@NgModule({
    declarations: [
        AdministrationComponent,
        AdministrationLineComponent,
        AdministrationStationComponent,
        AdministrationZoneComponent,
        AdministrationDocumentAcceptanceComponent,
        AdministrationLineTableComponent,
        StationTableComponent,
        ZoneFormComponent,
        ZoneTableComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        AdministrationRoutingModule,
        MapModule
    ],
    entryComponents: [
        AdministrationLineComponent
    ],
    exports: [
        AdministrationRoutingModule
    ],
    providers: [

    ]
})
export class AdministrationModule{  }