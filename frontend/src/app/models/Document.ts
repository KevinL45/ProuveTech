export class Document {

    id?: number;
    name: string;    
    file: File;

    constructor(name: string, file: File, id?: number) {
        this.id = id;
        this.name = name;
        this.file = file;
    }
}