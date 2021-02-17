package com.protal.myApp.auth;

import com.google.common.collect.Lists;
import com.protal.myApp.Entity.User;
import com.protal.myApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.protal.myApp.security.ApplicationUserRole.ADMIN;
import static com.protal.myApp.security.ApplicationUserRole.USER;

@Repository
public class ApplicationUserDaoService implements ApplicationUserDao {

    @Autowired
    UserRepository userRepository;


    @Override
    public ApplicationUser selectApplicationUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        ApplicationUser applicationUser = createNewAppUser(user);
        return applicationUser;
    }


    private ApplicationUser createNewAppUser(User user){
        final Set<GrantedAuthority> grntdAuths = new HashSet<GrantedAuthority>();
        grntdAuths.add(new SimpleGrantedAuthority(user.getRole()));
        ApplicationUser applicationUser = new ApplicationUser(user.getUsername(), user.getPassword(),
                grntdAuths,true, true, true,true);
        return applicationUser;
    }

}
