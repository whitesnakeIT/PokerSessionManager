package pl.coderslab.pokersessionmanager.security;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login/loginForm");
//        registry.addViewController("/logout").setViewName("login/logout");
        registry.addViewController("/403").setViewName("error/403");
        registry.addViewController("/app/player/dashboard").setViewName("player/dashboard");
        registry.addViewController("/app/admin/dashboard").setViewName("admin/adminPanel");
        registry.addViewController("/app/user/edit-password").setViewName("user/data/editPassword");  //405 error

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        ApplicationConversionService.configure(registry);
    }
}
