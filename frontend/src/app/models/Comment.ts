import { Project } from "./Project";
import { User } from "./User";

export class Comment {
    
    project: Project;
    user: User;
    content: string;

    constructor(content: string, user: User, project:Project) {

        this.content = content;
        this.user = user;
        this.project = project;
    }
}