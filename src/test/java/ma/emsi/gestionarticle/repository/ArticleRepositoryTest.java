package ma.emsi.gestionarticle.repository;

import ma.emsi.gestionarticle.entities.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;
    @BeforeEach
    void init() {
        articleRepository.save(Article.builder()
                .description("Pain")
                .price((float) (1+Math.random()*10))
                .quantity((int) (1+Math.random()*1000)).build());
        articleRepository.save(Article.builder()
                .description("Lait")
                .price((float) (1+Math.random()*10))
                .quantity((int) (1+Math.random()*1000)).build());
        articleRepository.save(Article.builder()
                .description("Frommage")
                .price((float) (1+Math.random()*10))
                .quantity((int) (1+Math.random()*1000)).build());
    }
    @Test
    void shouldFindArticleById() {
        Article expected = articleRepository.findAll().get(0);
        Long existingId = expected.getId();
        Optional<Article> result = articleRepository.findById(existingId);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(existingId);  // ← CHANGE ICI : utilisez existingId au lieu de 1L
        assertThat(result.get().getDescription()).isEqualTo("Pain");
    }
    @Test
    void shouldNotFindArticleById() {
        Long givenId = 10L;
        Optional<Article> result = articleRepository.findById(givenId);
        assertThat(result).isEmpty();
    }
}