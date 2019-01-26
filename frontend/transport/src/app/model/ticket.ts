import { User } from "./user";
import { Route } from "./route";
import { Line } from "./line";
import { Zone } from "./zone";

export class Ticket{
    id: number;
    price: number;
    user: User;
    transportType: String;
    ticketTemporal: String;
    startTime: Date;
    endTime: Date;
    active: String;
    route: Route;
    line: Line;
    zone: Zone;
}