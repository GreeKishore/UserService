package org.pro.userservice.dto;

import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.pro.userservice.entity.Role;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    @ManyToMany
    private List<Role> roles;
    private boolean isEmailVerified;
}