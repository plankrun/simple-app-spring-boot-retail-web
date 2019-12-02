package com.example.retailapplication.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String USER_QUERY =
            "select username, password, status as enabled " +
                    "from user as users " +
                    "where username = ?";

    private static final String ROLE_QUERY =
            "select user.username, role.role_name " +
                "from user inner join user_role " +
                "on (user.user_id = user_role.user_id) " +
                "inner join role " +
                "on (user_role.role_id = role.role_id) " +
                "where user.username = ?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(USER_QUERY)
                .authoritiesByUsernameQuery(ROLE_QUERY)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/css/**", "/images/**");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                .antMatchers("/h2/**", "/login", "/user/register").permitAll()
                .antMatchers("/product", "/home", "/transaction").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers("/user/**", "/product/add", "/product/update/*", "/product/delete/*").hasRole("ADMIN")
                .antMatchers("/transaction/checkout/*").hasRole("CUSTOMER")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home")

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");

        // To access H2 database console
//        httpSecurity.csrf().disable();
//        httpSecurity.headers().frameOptions().disable();
    }
}
