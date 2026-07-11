package com.radheankit.SpringBootLearning.security;

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
                                //User
                                .requestMatchers("/api/v1/users/me").hasRole("USER")
                                //Admin
                                .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                                //Order User
                                .requestMatchers(HttpMethod.POST, "/api/v1/orders").hasRole("USER")
                                .requestMatchers("/api/v1/users/my/**").hasRole("USER")
                                //Order Admin
                                .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasRole("ADMIN")
                                .requestMatchers("/api/v1/orders/user/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/orders/**").hasRole("ADMIN")

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
