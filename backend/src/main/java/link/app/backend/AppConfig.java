package link.app.backend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import link.app.backend.entity.Link;
import link.app.backend.repository.LinkRepository;

@Configuration
@EnableWebMvc
public class AppConfig {

    @Bean
    CommandLineRunner initDatabase(LinkRepository linkRepository) {
        return args -> {
            Link l1 = Link.builder()
                .title("Google")
                .description("Google search engine")
                .url("https://www.google.com")
                .isActive(true)
                .build();
            linkRepository.save(l1);
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        }; 
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
