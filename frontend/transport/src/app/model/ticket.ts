import { User } from "./user";

export class Ticket{
    id: number;
    price: number;
    user: User;
    transportType: String;
    ticketTemporal: String;
    startDate: Date;
    endTime: Date;
    active: String;
}