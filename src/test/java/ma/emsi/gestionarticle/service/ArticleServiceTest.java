package ma.emsi.gestionarticle.service;

import ma.emsi.gestionarticle.dto.ArticleDTO;
import ma.emsi.gestionarticle.entities.Article;
import ma.emsi.gestionarticle.exceptions.ArticleNotFoundException;
import ma.emsi.gestionarticle.mapper.ArticleMapper;
import ma.emsi.gestionarticle.repository.ArticleRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    private ArticleMapper articleMapper;
    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private ArticleServiceImpl underTest;
    @Test
    void shouldSaveNewArticle() throws ArticleNotFoundException {
        ArticleDTO articleDTO = ArticleDTO.builder()
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Article article = Article.builder()
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Article savedArticle = Article.builder()
                .id(1L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        ArticleDTO expected = ArticleDTO.builder()
                .id(1L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Mockito.when(articleMapper.fromArticleDTOToArticle(articleDTO)).thenReturn(article);
        Mockito.when(articleRepository.save(article)).thenReturn(savedArticle);
        Mockito.when(articleMapper.fromArticleToArticleDTO(savedArticle)).thenReturn(expected);
        ArticleDTO result = underTest.save(articleDTO);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }
    @Test
    void shouldSaveArticle() throws ArticleNotFoundException {
        ArticleDTO articleDTO = ArticleDTO.builder()
                .id(1L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Article article = Article.builder()
                .id(1L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Article savedArticle = Article.builder()
                .id(1L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        ArticleDTO expected = ArticleDTO.builder()
                .id(1L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();

        Mockito.when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        Mockito.when(articleMapper.fromArticleDTOToArticle(articleDTO)).thenReturn(article);
        Mockito.when(articleRepository.save(article)).thenReturn(savedArticle);
        Mockito.when(articleMapper.fromArticleToArticleDTO(savedArticle)).thenReturn(expected);

        ArticleDTO result = underTest.save(articleDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());

    }
    @Test
    void shouldNotSavedArticleWhenIdExist(){
        ArticleDTO articleDTO = ArticleDTO.builder()
                .id(5L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Article article = Article.builder()
                .id(5L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Mockito.when(articleRepository.findById(5L)).thenReturn(Optional.of(article));
        AssertionsForClassTypes.assertThatThrownBy(() -> underTest.save(articleDTO))
                .isInstanceOf(ArticleNotFoundException.class);
    }
    @Test
    void shouldGetAllArticles(){
        List<Article> articles = List.of(
                Article.builder()
                        .description("Pain")
                        .price(1.2F)
                        .quantity(10).build(),
                Article.builder()
                        .description("Lait")
                        .price(4.0F)
                        .quantity(6).build()
        );
        List<ArticleDTO> expected = List.of(
                ArticleDTO.builder()
                        .description("Pain")
                        .price(1.2F)
                        .quantity(10).build(),
                ArticleDTO.builder()
                        .description("Lait")
                        .price(4F)
                        .quantity(6).build()
        );

        Page<Article> page = new PageImpl<>(articles);
        Mockito.when(articleRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
        Mockito.when(articleMapper.fromArticleToArticleDTO(articles.get(0))).thenReturn(expected.get(0));
        Mockito.when(articleMapper.fromArticleToArticleDTO(articles.get(1))).thenReturn(expected.get(1));
        Page<ArticleDTO> result = underTest.getAllArticles(0,5);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result.getContent());
    }
    @Test
    void shouldFindArticleById() throws ArticleNotFoundException {
        Long articleId = 1L;
        Article article = Article.builder()
                .id(1L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        ArticleDTO expected = ArticleDTO.builder()
                .id(1L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Mockito.when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        Mockito.when(articleMapper.fromArticleToArticleDTO(article)).thenReturn(expected);
        ArticleDTO result = underTest.getArticleById(articleId);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }
    @Test
    void shouldNotFindArticleById(){
        Long articleId = 9L;
        Mockito.when(articleRepository.findById(articleId)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(() -> underTest.getArticleById(articleId))
                .isInstanceOf(ArticleNotFoundException.class)
                .hasMessage("Article not found");
    }
    @Test
    void testUpdateArticle() throws ArticleNotFoundException {
        Long articleId = 6L;
        ArticleDTO articleDTO = ArticleDTO.builder()
                .id(6L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Article article = Article.builder()
                .id(6L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Article updatedArticle = Article.builder()
                .id(6L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        ArticleDTO expected = ArticleDTO.builder()
                .id(6L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Mockito.when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        Mockito.when(articleRepository.save(article)).thenReturn(updatedArticle);
        Mockito.when(articleMapper.fromArticleToArticleDTO(updatedArticle)).thenReturn(expected);
        ArticleDTO result = underTest.updateArticleById(articleId, articleDTO);
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }
    @Test
    void shouldUpdateOnlyDescription() throws ArticleNotFoundException {
        Long id = 1L;
        Article existingArticle = Article.builder()
                .id(id)
                .description("Pain")
                .price(1.0F)
                .quantity(5)
                .build();
        ArticleDTO updateDTO = ArticleDTO.builder()
                .description(null)
                .price(0.0F)
                .quantity(0)
                .build();
        Article updatedArticle = Article.builder()
                .id(id)
                .description("Pain")
                .price(1.0F)
                .quantity(5)
                .build();
        ArticleDTO expectedDTO = ArticleDTO.builder()
                .id(id)
                .description("Pain")
                .price(1.0F)
                .quantity(5)
                .build();
        Mockito.when(articleRepository.findById(id)).thenReturn(Optional.of(existingArticle));
        Mockito.when(articleRepository.save(existingArticle)).thenReturn(updatedArticle);
        Mockito.when(articleMapper.fromArticleToArticleDTO(updatedArticle)).thenReturn(expectedDTO);
        ArticleDTO result = underTest.updateArticleById(id, updateDTO);
        assertEquals("Pain", result.getDescription());
        assertEquals(1.0F, result.getPrice());
        assertEquals(5, result.getQuantity());
        assertEquals("Pain", result.getDescription());
    }
    @Test
    void shouldDeleteArticle() throws ArticleNotFoundException {
        Long articleId = 1L;
        Article article = Article.builder()
                .id(6L)
                .description("Pain")
                .price(1.2F)
                .quantity(10).build();
        Mockito.when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        underTest.deleteArticleById(articleId);
        Mockito.verify(articleRepository).delete(article);
    }
    @Test
    void shouldNotDeleteArticle(){
        Long articleId = 9L;
        Mockito.when(articleRepository.findById(articleId)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(() -> underTest.deleteArticleById(articleId))
                .isInstanceOf(ArticleNotFoundException.class);
    }
}