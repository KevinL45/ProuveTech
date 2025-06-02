
export class User {
    id?: number;
    firstname?:string;
    lastname?:string;
    mail: string;
    password: string;
    cv?:string;
    title?: string;
    description?:string;
    telephone?:string;
    photo?:string;
    statusId?:number;

    constructor(mail: string, password: string, firstname?:string, lastname?:string, statusId?:number,
        cv?:string, title?: string, description?:string, telephone?:string, photo?:string, id?: number) {

        this.id = id;
        this.mail = mail;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.statusId=statusId;
        this.cv = cv;
        this.title = title
        this.description = description;
        this.photo = photo

    }
}