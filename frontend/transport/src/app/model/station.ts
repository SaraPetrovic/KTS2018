import { Point } from "./point";

export class Station{
    id: number;
    name: string;
    location: Point;
    address: string;
    vehicleType: string;
    x?: number;
    y?: number;
}