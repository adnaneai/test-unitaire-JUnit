package ma.emsi.gestionarticle.mapper;

import ma.emsi.gestionarticle.dto.ArticleDTO;
import ma.emsi.gestionarticle.entities.Article;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.Random;

class ArticleMapperTest {
    private  ArticleMapper articleMapper =new ArticleMapperImpl();
    Random random = new Random();
    @Test
    void shouldMapAtricleToArticleDTO(){
        Article givenArticle = Article.builder()
                .id(1L)
                .description("Pain")
                .price((float) (1+Math.random()*10))
                .quantity(random.nextInt()).build();
        ArticleDTO expected = ArticleDTO.builder()
                .id(1L)
                .description("Pain")
                .price((float) (1+Math.random()*10))
                .quantity(random.nextInt()).build();
        ArticleDTO result = articleMapper.fromArticleToArticleDTO(givenArticle);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().ignoringFields("price","quantity").isEqualTo(result);
    }
    @Test
    void shouldMapArticleDTOToArticle(){
        ArticleDTO givenArticleDTO = ArticleDTO.builder()
                .id(1L)
                .description("Pain")
                .price((float) (1+Math.random()*10))
                .quantity(random.nextInt()).build();
        Article expected = Article.builder()
                .id(1L)
                .description("Pain")
                .price((float) (1+Math.random()*10))
                .quantity(random.nextInt()).build();
        Article result = articleMapper.fromArticleDTOToArticle(givenArticleDTO);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().ignoringFields("price","quantity").isEqualTo(result);
    }
    @Test
    void shouldNotMapArticleToArticleDTO(){
        AssertionsForClassTypes .assertThatThrownBy(
                () -> articleMapper.fromArticleDTOToArticle(null)
        ).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void shouldNotMapArticleDTOToArticle(){
        AssertionsForClassTypes.assertThatThrownBy(
                () -> articleMapper.fromArticleDTOToArticle(null)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}