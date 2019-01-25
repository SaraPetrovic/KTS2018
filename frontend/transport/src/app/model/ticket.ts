import { User } from "./user";

export class Ticket{
    id: number;
    price: number;
    user: User;
    active: boolean;
    transportType: number;
    ticketTemporal: number;
    startTime: Date;
    endTime: Date;
}