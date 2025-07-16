import { Status } from "./Status";

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
    photo?:File;
    linkPhoto?:string;
    status?:Status;
    statusId?:number;


    constructor(mail: string, password: string, firstname?:string, lastname?:string, statusId?:number, status?:Status, 
        cv?:string, title?: string, description?:string, telephone?:string, 
        photo?:File, id?: number,linkPhoto?:string) {

        this.id = id;
        this.mail = mail;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status=status;
        this.cv = cv;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.telephone = telephone;
        this.linkPhoto=linkPhoto;
        this.statusId =statusId;

    }
}