export class SocialNetworks {
    
    id?: number;
    name: string;    
    logo: string;


    constructor(name: string, logo: string, id?: number) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    }
}