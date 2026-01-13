import { User } from "./User";

export class Role{
    
    id?: number;
    name: string;
    users?: User[];    

    constructor(name: string, id?: number, users?: User[]) {
        this.id = id;
        this.name = name;
        this.users = users;
    }
}