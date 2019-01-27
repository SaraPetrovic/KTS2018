export class Pricelist {
    id?: number;
    monthlyCoeff: number;
    yearlyCoeff: number;
    onehourCoeff: number;
    studentDiscount: number;
    seniorDiscount: number;
    lineDiscount: number;
    zonePrices: object;
    startDate: Date;
}