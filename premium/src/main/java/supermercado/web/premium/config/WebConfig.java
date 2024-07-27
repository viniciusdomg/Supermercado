package supermercado.web.premium.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapeie a URL "/images/**" para o diretório onde as imagens são armazenadas
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + System.getProperty("user.home") + "/supermercado_images/");
    }
}
