export class Project{
    
    id?: number;
    name: string; 
    description: string;
    video:File;
    link_video?:string;
    thumbnail:File;
    link_thumbnail?:string;
    sourcecode:string;
    documents?: File[] = [];

    tools ?: number [] = [];
    toolsName ?: string [] = [];

    updatedDate: Date;
    createdDate?: Date;

    fullname?:string;
    titleActor?:string;
    photoProfile?:string;


   

    constructor(name: string, description:string, sourcecode:string, video:File,thumbnail:File,
        tools ?:number[], documents?: File[],
        createdDate?: Date, id?: number, 
        fullname?:string, titleActor?:string, link_video?:string, photoProfile?:string,
        toolsName?:string[], link_thumbnail?:string, ) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.video = video;
        this.sourcecode = sourcecode;
        this.tools = tools;
        this.documents = documents;
        this.updatedDate =  new Date();
        this.createdDate = createdDate;
        this.fullname = fullname;
        this.titleActor= titleActor;
        this.link_video = link_video;
        this.photoProfile = photoProfile;
        this.toolsName = toolsName;
        this.link_thumbnail = link_thumbnail;
        this.thumbnail = thumbnail;

    }
}