
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
    link_photo?:string;
    statusId?:number;
    statusName?:string;
    link_social_networks?: string []= []


    constructor(mail: string, password: string, firstname?:string, lastname?:string, statusId?:number,
        cv?:string, title?: string, description?:string, telephone?:string, 
        photo?:File, link_social_networks?: string [],link_photo?:string,
        id?: number, statusName?:string) {

        this.id = id;
        this.mail = mail;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.statusId=statusId;
        this.cv = cv;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.statusName = statusName;
        this.telephone = telephone;
        this.link_social_networks = link_social_networks;
        this.link_photo=link_photo;

    }
}