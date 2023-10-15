package com.planticasalquiler.demo.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.planticasalquiler.demo.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Configuration one

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

   @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/img/**","/js/**","/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/index",true).permitAll()
                .and().logout()
                .logoutUrl("/logout") // Ruta de cierre de sesión
                .logoutSuccessUrl("/login?logout") // Página a la que redirigir después del cierre de sesión
                .invalidateHttpSession(true) // Invalida la sesión HTTP
                .deleteCookies("JSESSIONID") // Elimina las cookies de sesión
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .build();
    } 
    
    // @Bean
    //     UserDetailsService userDetailsService(){
    //         InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    //         manager.createUser(User.withUsername("racso")
    //                 .password("$2a$10$Fmgjy6lngTDA7ELDG2rLJuWTRqc1voPYkigtxTtGLX6uH4tMGJP1m")
    //                 .roles()
    //                 .build());

    //         return manager;
    //     }
    

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
   }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

    // public static void main(String[]args){
    //     System.out.println(new BCryptPasswordEncoder().encode("1234"));
    // }
}