package ftn.kts.transport.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_CLIENT, ROLE_CONDUCTER;

    public String getAuthority(){return name();}
}