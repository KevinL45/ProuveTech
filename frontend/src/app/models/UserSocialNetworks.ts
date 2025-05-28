import { SocialNetworks } from "./SocialNetworks";
import { User } from "./User";

export class UserSocialNetworks{

    user: User;
    social_networks: SocialNetworks;
    profilUrl: String;

    constructor(user:User, social_networks: SocialNetworks, profilUrl: string){
        this.user = user;
        this.social_networks = social_networks;
        this.profilUrl = profilUrl;
    }

}