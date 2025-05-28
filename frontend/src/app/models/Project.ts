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
   

    constructor(name: string, description:string, sourceCode:string, video:File, 
        user:User,tools :Tool[], documents: Document[],
        createdDate?: Date, id?: number) {

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

    }
}