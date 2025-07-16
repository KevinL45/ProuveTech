import { Tool } from "./Tool";
import { User } from "./User";

export class Project{
    
    id?: number;
    name: string; 
    description: string;
    video:File;
    link_video?:string;
    thumbnail:File;
    link_thumbnail?:string;
    sourcecode:string;
    updatedDate: Date;
    createdDate?: Date;

    tools ?: Tool [] = [];
    user?:User;
  


   

    constructor(name: string, description:string, sourcecode:string, video:File,thumbnail:File, createdDate?: Date,
        tools ?:Tool[], user?:User, id?: number, link_video?:string, link_thumbnail?:string ) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.video = video;
        this.sourcecode = sourcecode;
        this.tools = tools;
        this.updatedDate =  new Date();
        this.createdDate = createdDate;
        this.link_video = link_video;
        this.link_thumbnail = link_thumbnail;
        this.thumbnail = thumbnail;
        this.user = user;

    }
}