package ru.deft.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

/*
 * Created by sgolitsyn on 12/11/19
 */
@Getter
@Setter
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
}
