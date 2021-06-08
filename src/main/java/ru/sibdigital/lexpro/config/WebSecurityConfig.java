package ru.sibdigital.lexpro.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.sibdigital.lexpro.service.UserDetailsServiceImpl;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@Configuration
@EnableWebSecurity()
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private static String OPERATION_READ = "READ_";
    private static String OPERATION_WRITE = "WRITE_"; //create + update
    private static String OPERATION_DELETE = "DELETE_";

    public static String RESOURCE_RKK = "RKK";

    private static String constructPrivilege (String operation, String resource){
        return operation + "_" + resource;
    }

    public static String read(String resource){
        return constructPrivilege(OPERATION_READ, resource);
    }

    //@Bean
    //public CorsConfigurationSource corsConfigurationSource() {
        //final CorsConfiguration configuration = new CorsConfiguration();
        //configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        //configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
       // configuration.setAllowCredentials(true);
        //configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        //configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
       // UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     //   source.registerCorsConfiguration("/**", configuration);
   //    return source;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
               // .cors()
                //.and()
                .authorizeRequests()
                //.antMatchers("/**").permitAll()
                .antMatchers("/libs/**", "/css/**", "/images/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/doc_rkks").hasAuthority(OPERATION_READ + RESOURCE_RKK)
                .antMatchers("/favicon.ico","/logo.png").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                //.defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .and()
                .cors()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID").and()
                .sessionManagement()
                .invalidSessionUrl("/login");

    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*")
                .allowedHeaders("authorization","Access-Control-Allow-Origin");
    }











}

