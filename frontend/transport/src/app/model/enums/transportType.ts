export enum TransportType{
    Bus = 0,
    Tram,
    Subway
}

export namespace TransportType{

    export function values(){
        return Object.keys(TransportType).filter(
          (type) => isNaN(<any>type) && type !== 'values'  
        );
    }
}