package com.example.buysellrentproperty.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Spring MVC.
 * Класс конфигурации для Spring MVC.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Configures a ModelMapper bean.
     * Конфигурирует бин ModelMapper.
     *
     * @return The configured ModelMapper instance.
     *         Настроенный экземпляр ModelMapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Configures resource handlers for serving static resources.
     * Конфигурирует обработчики ресурсов для обслуживания статических ресурсов.
     *
     * @param registry The registry for managing resource handlers.
     *                 Реестр для управления обработчиками ресурсов.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}