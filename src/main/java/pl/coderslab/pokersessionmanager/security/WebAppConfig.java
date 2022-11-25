package pl.coderslab.pokersessionmanager.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login/loginForm");
        registry.addViewController("/logout").setViewName("login/logout");
        registry.addViewController("/403").setViewName("error/403");
        registry.addViewController("/app/dashboard").setViewName("user/dashboard"); //  nie dziala :) jak nie ma akcji

    }
}
