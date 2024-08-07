package org.pro.userservice.security.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.pro.userservice.entity.Role;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {

    private String authority;

    public CustomGrantedAuthority() {

    }

    public CustomGrantedAuthority(Role role) {
        this.authority = role.getRoleName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}