package nl.rabobank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Register the custom converters in the Spring configuration
        registry.addConverter(new StringToAuthorizationConverter());
        registry.addConverter(new StringToAccountTypeConverter());
    }
}