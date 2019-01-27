import { NgModule } from "@angular/core";
import { MapComponent, StreetClickedDirective, StreetEnterDirective, StreetEventDirective, DrowStationDirective, DrowTempStationDirective } from "./map.component";
import { MapEventsService } from "./services/map-events/map-events.service";

@NgModule({
    declarations: [
        MapComponent,
        StreetClickedDirective,
        StreetEnterDirective,
        StreetEventDirective,
        DrowStationDirective,
        DrowTempStationDirective,
    ],
    exports: [
        MapComponent
    ],
    providers: [
        MapEventsService
    ]
})
export class MapModule { }