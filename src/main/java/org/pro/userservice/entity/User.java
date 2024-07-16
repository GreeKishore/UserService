package org.pro.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel {
    private String name;
    private String hashedPassword;
    private String email;
    @ManyToMany
    private List<Role> role;
    private boolean isEmailVerified;
}