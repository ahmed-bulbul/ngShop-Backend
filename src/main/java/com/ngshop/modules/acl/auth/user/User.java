package com.ngshop.modules.acl.auth.user;

import com.ngshop.modules.acl.auth.role.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACL_USER")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank(message = "*Name is mandatory")
    @Column(name = "USERNAME",length = 255)
    private String username;

    @NotBlank
    private String password;
    private String userTitle;
    private Boolean groupUser;  // 0 or 1
    private String groupUsername;


    private Boolean enabled = false;
    private Boolean isEmpCreated = false;

    private Boolean accountExpired;
    private Boolean accountLocked;
    private Boolean passwordExpired;

    //For ngShop User
    private Boolean isAdmin=false;
    @Email
    @NotBlank(message = "email must not be empty")
    private String email;
    private String phone;
    private String city;
    private String country;
    private String street;
    private String zip;
    private String apartment;
    private String name;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "acl_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(String username, String email, String firstName, String lastName, String phone, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public User( String email, String userTitle, Boolean groupUser, String groupUsername,
                String name, String phone, String street, String zip, String apartment,
                 String country,Boolean isAdmin, String encode) {

        this.email=email;
        this.userTitle=userTitle;
        this.groupUser=groupUser;
        this.groupUsername=groupUsername;
        this.name=name;
        this.phone=phone;
        this.street=street;
        this.zip=zip;
        this.apartment=apartment;
        this.country=country;
        this.isAdmin=isAdmin;
        this.password=encode;

    }
}