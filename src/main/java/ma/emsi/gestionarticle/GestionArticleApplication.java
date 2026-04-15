package ma.emsi.gestionarticle;

import ma.emsi.gestionarticle.entities.Article;
import ma.emsi.gestionarticle.repository.ArticleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
//@Configuration
//@Profile("!test")
public class GestionArticleApplication {
    private ArticleRepository articleRepository;

//    public GestionArticleApplication(ArticleRepository articleRepository) {
//        this.articleRepository = articleRepository;
//    }

    public static void main(String[] args) {
        SpringApplication.run(GestionArticleApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner init(ArticleRepository articleRepository) {
        return args ->  {
            for (int i = 1; i <= 10; i++) {
                Article article1 = Article.builder()
                        .description("Pain")
                        .price((float) (1+Math.random()*10))
                        .quantity((int) (1+Math.random()*1000)).build();

                Article article2 = Article.builder()
                        .description("Lait")
                        .price((float) (1+Math.random()*10))
                        .quantity((int) (1+Math.random()*1000)).build();

                Article article3 = Article.builder()
                        .description("Frommage")
                        .price((float) (1+Math.random()*10))
                        .quantity((int) (1+Math.random()*1000)).build();

                articleRepository.save(article1);
                articleRepository.save(article2);
                articleRepository.save(article3);
            }
        };
    }

}
