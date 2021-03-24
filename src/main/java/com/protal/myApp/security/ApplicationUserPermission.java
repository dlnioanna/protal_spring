package com.protal.myApp.security;

public enum ApplicationUserPermission {
    MOVIE_READ("movie:read"),
    MOVIE_WRITE("movie:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    MOVIESHOW_READ("movieshow:read"),
    MOVIESHOW_WRITE("movieshow:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
