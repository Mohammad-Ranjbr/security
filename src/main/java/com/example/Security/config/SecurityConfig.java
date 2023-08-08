package com.example.Security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Security.jwt.JwtFilter;
import com.example.Security.users.service.UsersService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    // JDBC Authentication
    //@Autowired
    //private DataSource dataSource ;
    @Autowired
    private UsersService usersService ;
    @Autowired
    private JwtFilter jwtFilter ;

    // @Autowired 
    // public SecurityConfig(DataSource dataSource , UsersService usersService){
    //     this.dataSource = dataSource ;
    //     this.usersService = usersService ;
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {   
                http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login","/error","/info","/jwt/login").permitAll()
                // Approach 1 to assign Roles to users
                //.antMatchers("/user/**").hasAnyAuthority("ADMIN","USER") //.hasRole("ROLE_ADMIN")  
                //.antMatchers("/admin/**").hasAuthority("ADMIN") 
                .anyRequest().authenticated() 
                .and()
                .formLogin().loginPage("/login").usernameParameter("email")
                //.defaultSuccessUrl("/index",true);  
                .successHandler(new LoginSuccessHandler()) 
                .and().rememberMe().rememberMeCookieName("remember")
                .tokenValiditySeconds(60).rememberMeParameter("remember")
                .and().exceptionHandling().accessDeniedPage("/error") 
                .and().logout().logoutUrl("/logout").deleteCookies("remember")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);  
    }

    // In Memory Authentication
    // @Override 
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.inMemoryAuthentication()
    //     .withUser("Mohammad")
    //     .password("123456")
    //     .roles("USER");
    // }

    // first way to JDBC Authentication
    // @Override 
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
    //     auth.jdbcAuthentication()
    //     .dataSource(this.dataSource)
    //     .usersByUsernameQuery("select email,password,enabled from users where email=?")
    //     .authoritiesByUsernameQuery("select email,user_roles from authorities where email=?"); 
    // }

    // second way to JDBC Authentication
     @Override 
     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService);
     }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance(); 
    }

}
