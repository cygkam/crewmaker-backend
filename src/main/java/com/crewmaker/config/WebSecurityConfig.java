package com.crewmaker.config;

import com.crewmaker.config.security.jwt.JwtAuthenticationEntryPoint;
import com.crewmaker.config.security.jwt.JwtAuthenticationFilter;
import com.crewmaker.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
                .and()
            .csrf()
                .disable()
            .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers("/api/updateUser")
                .permitAll()
                .antMatchers("/api/event")
                .permitAll()
                .antMatchers("/api/newEventPlace")
                .permitAll()
                .antMatchers("/api/archiveEventPlace")
                .permitAll()
                .antMatchers("/api/acceptEventPlace")
                .permitAll()
                .antMatchers("/api/countEventPlaceEvents")
                .permitAll()
                .antMatchers("/api/eventParticipants")
                .permitAll()
                .antMatchers("/api/usersProfileImage/*")
                .permitAll()
                .antMatchers("/api/usersProfileImageSmall/*")
                .permitAll()
                .antMatchers("/api/leaveevent")
                .permitAll()
                .antMatchers("/api/uploadPhoto/")
                .permitAll()
                .antMatchers("/api/useropinions")
                .permitAll()
                .antMatchers("/api/addEventPlaceOpinion")
                .permitAll()
                .antMatchers("/api/eventPlaceImage/*")
                .permitAll()
                .antMatchers("/api/testQuery*")
                .permitAll()
                .antMatchers("/api/eventOpinion")
                .permitAll()
                .antMatchers("/api/getEventPlaceOpinions")
                .permitAll()
                .antMatchers("/api/useropinion")
                .permitAll()
                .antMatchers("/api/uploadPhotoEventPlace/*")
                .permitAll()
                .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
               .permitAll()
                .antMatchers("/api/getEventPlace*")
                .permitAll()
                .anyRequest()
                .fullyAuthenticated();
            //.httpBasic()
                //.disable();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //http
            //.addFilter(new JwtAuthenticationFilter(authenticationManager()))
            //.addFilter(new JWTAuthorizationFilter(authenticationManager()));

    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}

