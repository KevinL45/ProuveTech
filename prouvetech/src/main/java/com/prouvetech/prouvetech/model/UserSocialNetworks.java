package com.prouvetech.prouvetech.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_social_networks", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "social_networks_id" })
})
public class UserSocialNetworks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "social_networks_id")
    private SocialNetworks socialNetwork;

    @Column(nullable = false, length = 255)
    private String profileUrl;

    public UserSocialNetworks() {
    }

    public UserSocialNetworks(User user, SocialNetworks socialNetwork, String profileUrl) {
        this.user = user;
        this.socialNetwork = socialNetwork;
        this.profileUrl = profileUrl;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SocialNetworks getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(SocialNetworks socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Override
    public String toString() {
        return "UserSocialNetwork{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", socialNetwork=" + socialNetwork.getName() +
                ", profileUrl='" + profileUrl + '\'' +
                '}';
    }
}
