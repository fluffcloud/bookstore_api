package com.example.bookstore_final_app.user_access_management;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    protected InMemoryUserDetailsManager configureAuthentication() {

        List<UserDetails> userDetails=new ArrayList<>();
        List<GrantedAuthority> employeeRoles=new ArrayList<>();
        employeeRoles.add(new SimpleGrantedAuthority("EMPLOYEE"));

        List<GrantedAuthority> adminRoles=new ArrayList<>();
        adminRoles.add(new SimpleGrantedAuthority("ADMIN"));

        userDetails.add( new User("user1","$2a$10$CW2C8M6zT8uEtaJ7ZxffTuuvAldJ10Kbvlm4YHnG1TOmBMgLQGySe",employeeRoles));
        userDetails.add( new User("admin1","$2a$10$CW2C8M6zT8uEtaJ7ZxffTuuvAldJ10Kbvlm4YHnG1TOmBMgLQGySe",adminRoles));

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity https) throws Exception{
        https.csrf().disable();

        https.authorizeRequests()
                .antMatchers("/DeleteBookById/*").hasAnyAuthority("ADMIN")
                .antMatchers("/*").permitAll().and().httpBasic();
        return https.build();

    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }


}





