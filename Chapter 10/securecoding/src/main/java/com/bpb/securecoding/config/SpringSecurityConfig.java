package com.bpb.securecoding.config;

import java.security.SecureRandom;
import java.util.function.Function;

import com.bpb.securecoding.filter.RequestValidatorFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {

    // Create 2 users for demo
    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {
        UserDetails userDetailsOne = createNewUser("bpbuser", "secret1","USER");
        UserDetails userDetailsTwo = createNewUser("bpbadmin", "secret1","USER", "ADMIN");

        return new InMemoryUserDetailsManager(userDetailsOne, userDetailsTwo);
    }

    @Bean
    public PasswordEncoder securePasswordEncoder() {
        return new BCryptPasswordEncoder(11, new SecureRandom());
    }

    /*
    @Bean
    public PasswordEncoder pbkdf2PasswordEncoder() {
        String pepper = "secureJava"; // secret key used by password encoding
        int hashWidth = 512;      // hash width in bits
        int iterations = 100_000;  // number of hash iteration

        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder =
                new Pbkdf2PasswordEncoder(pepper, iterations, hashWidth, PBKDF2WithHmacSHA512);
        pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);
        return pbkdf2PasswordEncoder;
    }*/

    private UserDetails createNewUser(String username, String password,String... userRoles) {
        Function<String, String> passwordEncoder = input -> securePasswordEncoder().encode(input);

        return User
                .builder()
                .roles(userRoles)
                .username(username)
                .password(password)
                .passwordEncoder(passwordEncoder)
                .build();
    }

    // Secure the endpoins with HTTP Basic authentication
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ObjectMapper objectMapper) throws Exception {
        http
                .httpBasic(withDefaults())
                .authorizeRequests(authorize -> authorize
                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .requestMatchers(HttpMethod.GET, "/students/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/students").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/students/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/students/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/students/**").hasRole("ADMIN"))
                .csrf(csrf -> csrf.disable())
                .formLogin(formlogin -> formlogin.disable())
                .headers(headers ->
                        headers
                                .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )
                .addFilterAfter(new RequestValidatorFilter(objectMapper), BasicAuthenticationFilter.class)
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .sessionFixation()
                                    .changeSessionId()
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                )
                .logout(logoutCustomizer -> logoutCustomizer
                        .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(COOKIES)))
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                ;
        return http.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        //ok for demo
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
        return manager;
    }*/

}
