package pl.coderslab.pokersessionmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginSuccessHandler getLoginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/tournament/all", "/poker_room/all").permitAll()
                .antMatchers("/logout", "/tournament/**", "/poker_room/**").authenticated()
                .antMatchers("/app/**").hasRole("USER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").usernameParameter("email")
                .successHandler(getLoginSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/logout").logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        return http.build();
    }
}
