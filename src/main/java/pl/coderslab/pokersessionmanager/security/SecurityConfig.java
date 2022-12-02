package pl.coderslab.pokersessionmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

//@EnableGlobalMethodSecurity(securedEnabled = true) // możemy teraz dodawac adnotacje @Secured("ROlE_***") na cały kontroler
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/tournament/all").permitAll()
                .antMatchers("/logout").authenticated()
                .antMatchers("/app/**").hasRole("USER")
                .antMatchers("/tournament/**").hasRole("ADMIN") // edycja turnieów tylko admin
                .antMatchers("/security/**").hasAnyRole("ADMIN")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
//                .antMatchers("/**").permitAll()
                .and().formLogin()
                .loginPage("/login").usernameParameter("email")
                .defaultSuccessUrl("/app/dashboard") // /app/dashboard
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        return http.build();
    }
}
