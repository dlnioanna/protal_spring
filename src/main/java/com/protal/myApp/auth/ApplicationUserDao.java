package com.protal.myApp.auth;

import java.util.Optional;

public interface ApplicationUserDao {

    ApplicationUser selectApplicationUserByUsername(String username);

}
