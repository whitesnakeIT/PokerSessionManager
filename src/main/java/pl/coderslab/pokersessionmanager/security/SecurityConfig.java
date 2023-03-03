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
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/registration",
                                "/login",
                                "/WEB-INF/views/authorization/**",
                                "/registration").permitAll()
                        .requestMatchers("/app/tournament/global/all",
                                "/app/poker_room/global/all").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/app/tournament/global/**",
                                "/WEB-INF/views/admin/**",
                                "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/app/player/**",
                                "/app/tournament/local/**",
                                "/app/tournament/suggestion/**",
                                "/app/session/add", "/app/session/all").hasRole("USER")
                        .requestMatchers("/tournament/all", "/poker_room/all", "/WEB-INF/views/unlogged/**").anonymous()
                        .anyRequest().hasAnyRole("USER", "ADMIN"))
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
