package ma.emsi.gestionarticle.mapper;

import ma.emsi.gestionarticle.dto.ArticleDTO;
import ma.emsi.gestionarticle.entities.Article;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

class ArticleMapperTest {
    private  ArticleMapper articleMapper =new ArticleMapperImpl();
    @Test
    void shouldMapAtricleToArticleDTO(){
        Article givenArticle = Article.builder()
                .id(1L)
                .description("Pain")
                .price((float) (1+Math.random()*10))
                .quantity((int) (1+Math.random()*1000)).build();
        ArticleDTO expected = ArticleDTO.builder()
                .id(1L)
                .description("Pain")
                .price((float) (1+Math.random()*10))
                .quantity((int) (1+Math.random()*1000)).build();
        ArticleDTO result = articleMapper.fromArticleToArticleDTO(givenArticle);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().ignoringFields("price","quantity").isEqualTo(result);
    }
    @Test
    void shouldMapArticleDTOToArticle(){
        ArticleDTO givenArticleDTO = ArticleDTO.builder()
                .id(1L)
                .description("Pain")
                .price((float) (1+Math.random()*10))
                .quantity((int) (1+Math.random()*1000)).build();
        Article expected = Article.builder()
                .id(1L)
                .description("Pain")
                .price((float) (1+Math.random()*10))
                .quantity((int) (1+Math.random()*1000)).build();
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