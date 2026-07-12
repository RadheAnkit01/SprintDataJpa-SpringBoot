package com.radheankit.SpringBootLearning.security;

import com.radheankit.SpringBootLearning.entities.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((s) -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) ->
                        //Login & Register
                        auth.requestMatchers("/api/v1/auth/**").permitAll()

                                //Products Admin
                                .requestMatchers("/api/v1/products/admin/**").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers("/api/v1/products/**").hasRole(Role.ADMIN.name())

                                //User
                                .requestMatchers("/api/v1/users/me").hasRole(Role.USER.name())
                                //Admin
                                .requestMatchers("/api/v1/users/**").hasRole(Role.ADMIN.name())
                                //Order User
                                .requestMatchers(HttpMethod.POST, "/api/v1/orders").hasRole(Role.USER.name())
                                .requestMatchers("/api/v1/users/my/**").hasRole(Role.USER.name())
                                //Order Admin
                                .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasRole(Role.ADMIN.name())
                                .requestMatchers("/api/v1/orders/user/**").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasRole(Role.ADMIN.name())

                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //    //In memory User Details Manager
    //    @Bean
    //    UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder){
    //        UserDetails admin = User.withUsername("Admin").roles("ADMIN").password(passwordEncoder.encode("adminpassword"))
    //                .build();
    //        UserDetails user = User.withUsername("Ankit").roles("USER").password(passwordEncoder.encode("userpassword"))
    //                .build();
    //
    //        return new InMemoryUserDetailsManager(admin, user);
    //    }
}
