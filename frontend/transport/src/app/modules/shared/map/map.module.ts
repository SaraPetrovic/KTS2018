import { NgModule } from "@angular/core";
import { MapComponent, StreetClickedDirective, StreetEnterDirective, StreetEventDirective } from "./map.component";
import { MapEventsService } from "./services/map-events/map-events.service";

@NgModule({
    declarations: [
        MapComponent,
        StreetClickedDirective,
        StreetEnterDirective,
        StreetEventDirective
    ],
    exports: [
        MapComponent
    ],
    providers: [
        MapEventsService
    ]
})
export class MapModule { }