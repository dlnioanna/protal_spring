package com.protal.myApp.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import static com.protal.myApp.security.ApplicationUserPermission.*;


public enum ApplicationUserRole {
    USER(Sets.newHashSet(MOVIE_READ,MOVIESHOW_READ)),
    ADMIN(Sets.newHashSet(MOVIE_READ, MOVIE_WRITE,USER_READ, MOVIESHOW_READ, MOVIESHOW_WRITE)),
    ADMIN_CS(Sets.newHashSet(MOVIE_READ, MOVIE_WRITE,USER_READ, USER_WRITE, MOVIESHOW_READ, MOVIESHOW_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
