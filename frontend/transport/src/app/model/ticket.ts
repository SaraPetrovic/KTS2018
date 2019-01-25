import { User } from "./user";

export class Ticket{
    id: number;
    price: number;
    user: User;
    transportType: String;
    ticketTemporal: String;
    startTime: Date;
    endTime: Date;
    active: String;
}