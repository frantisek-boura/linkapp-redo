package link.app.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import link.app.backend.entity.Link;
import link.app.backend.repository.LinkRepository;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(LinkRepository linkRepository) {
        return args -> {
            Link l1 = new Link("Google", "Google search engine", "https://www.google.com", null, true);
            linkRepository.save(l1);
        };
    }

}
