package pl.coderslab.pokersessionmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
    public AntPathRequestMatcher getAntRequestMatcher() {
        return new AntPathRequestMatcher("/logout", "GET");
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .requestMatchers("/tournament/all", "/poker_room/all").permitAll()
                .requestMatchers("/logout", "/tournament/**", "/poker_room/**", "/app/user/**").authenticated()
                .requestMatchers("/app/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/registration").anonymous()
                .and()
                .formLogin()
                .loginPage("/login").usernameParameter("email")
                .successHandler(getLoginSuccessHandler())
                .and()
                .logout()
                .logoutRequestMatcher(getAntRequestMatcher())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        return http.build();
    }
}
