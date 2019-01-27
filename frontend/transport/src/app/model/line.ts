import { TransportType } from "./enums/transportType";

export class Line{
    id: number;
    name: string;
    description: string;
    vehicleType: string;
    streetPath?: string[];
    stations: {};
}