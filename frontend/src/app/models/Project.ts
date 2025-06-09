import { Tool } from "./Tool";
import { User } from "./User";

export class Project{
    
    id?: number;
    name: string; 
    description: string;
    video:File;
    sourceCode:string;
    user:User;
    documents: Document[] = [];
    tools : Tool [] = [];
    updatedDate: Date;
    createdDate?: Date;
    fullname?:string;
    titleActor?:string;
    link_video?:string;
    photoActor?:string;


   

    constructor(name: string, description:string, sourceCode:string, video:File, 
        user:User,tools :Tool[], documents: Document[],createdDate?: Date, id?: number, 
        fullname?:string, titleActor?:string, link_video?:string, photoActor?:string) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.video = video;
        this.sourceCode = sourceCode;
        this.user = user;
        this.tools = tools;
        this.documents = documents;
        this.updatedDate =  new Date();
        this.createdDate = createdDate;
        this.fullname = fullname;
        this.titleActor= titleActor;
        this.link_video = link_video;
        this.photoActor = photoActor;

    }
}