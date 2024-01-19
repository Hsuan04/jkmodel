package com.jkmodel.store.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    USER, GUEST;


    @Override
    public String getAuthority() {
        return name();
    }
}
