    package com.jkmodel.store.config;


    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.provisioning.InMemoryUserDetailsManager;
    import org.springframework.web.cors.CorsConfiguration;

    import java.util.Arrays;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {


        @Bean
        public UserDetailsService userDetailsService(){
            UserDetails user = User.builder()
                    .username("user")
                    .password(passwordEncoder().encode("password"))
                    .roles("USER")
                    .build();

            return new InMemoryUserDetailsManager(user);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Configuration
        public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.cors().configurationSource(httpServletRequest -> {
                            CorsConfiguration corsConfig = new CorsConfiguration();
                            corsConfig.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500"));
                            corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                            corsConfig.setAllowedHeaders(Arrays.asList("*"));
                            return corsConfig;
                }).and()
                        .authorizeRequests()
                        .antMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .formLogin()
                        .loginPage("/templates/signIn.html")
                        .permitAll()
                        .and()
                        .logout()
                        .permitAll()
                        .and()
                        .csrf().disable();
            }
        }
    }
