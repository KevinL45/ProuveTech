import { Status } from "./Status";

export class User {
    id?: number;
    firstname:string;
    lastname:string;
    mail: string;
    password: string;
    cv?:string;
    title?: string;
    description?:string;
    telephone?:string;
    photo?:string;
    status:Status

    constructor(mail: string, password: string, firstname:string, lastname:string, status:Status,
        cv?:string, title?: string, description?:string, telephone?:string, photo?:string, id?: number) {

        this.id = id;
        this.mail = mail;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status=status;
        this.cv = cv;
        this.title = title
        this.description = description;
        this.photo = photo

    }
}