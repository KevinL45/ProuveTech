export class Project{
    
    id?: number;
    name: string; 
    description: string;
    video:File;
    link_video?:string;
    sourceCode:string;
    documents?: File[] = [];
    tools ?: number [] = [];

    updatedDate: Date;
    createdDate?: Date;

    fullname?:string;
    titleActor?:string;
    photoActor?:string;


   

    constructor(name: string, description:string, sourceCode:string, video:File,
        tools ?:number[], documents?: File[],
        createdDate?: Date, id?: number, 
        fullname?:string, titleActor?:string, link_video?:string, photoActor?:string) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.video = video;
        this.sourceCode = sourceCode;
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