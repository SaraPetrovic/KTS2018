export class User{
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    password: string;
    email: string;
    token?: string;

    public constructor(init?: Partial<User>) {
        Object.assign(this, init);
    }
}