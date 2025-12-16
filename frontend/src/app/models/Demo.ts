import { Project } from "./Project";

export class Demo{
        id?: number;
        name: string; 
        description: string;
        thumbnail:File;
        link_thumbnail?:string;
        video:File;
        link_video?:string
        updatedDate: Date;
        createdDate?: Date;
        project?: Project
    
        constructor(name: string, description:string, thumbnail:File, video: File,
            createdDate?: Date, id?: number, link_thumbnail?:string, link_video?:string  ) {
    
            this.id = id;
            this.name = name;
            this.description = description;
            this.updatedDate =  new Date();
            this.createdDate = createdDate;
            this.link_thumbnail = link_thumbnail;
            this.thumbnail = thumbnail;
            this.video = video;
            this.link_video = link_video
            
        }
}