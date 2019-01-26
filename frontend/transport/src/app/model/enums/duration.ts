export enum Duration{
    OneHour,
    Monthly,
    Yearly,
    OneTime
}

export namespace Duration{
    
    export function values(){
        return Object.keys(Duration).filter(
            (type) => isNaN(<any>type) && type !== 'values'  
        );
    }
}