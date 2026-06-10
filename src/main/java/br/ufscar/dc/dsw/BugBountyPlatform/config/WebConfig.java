package br.ufscar.dc.dsw.BugBountyPlatform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapeia a URL /uploads/** para o diretório físico na raiz do projeto
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
