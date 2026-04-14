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

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    private ArticleMapper articleMapper;
    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private ArticleServiceImpl underTest;
    @Test
    void shouldSaveNwwArticle() throws ArticleNotFoundException {
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
}


//@Test
//void shouldGetAllCustomers() {
//    List<Customer> customers = List.of(
//            Customer.builder().firstName("Mohamed").lastName("Youssfi").email("med@gmail.com").build(),
//            Customer.builder().firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build()
//    );
//    List<CustomerDTO> expected = List.of(
//            CustomerDTO.builder().firstName("Mohamed").lastName("Youssfi").email("med@gmail.com").build(),
//            CustomerDTO.builder().firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build()
//    );
//    Mockito.when(customerRepository.findAll()).thenReturn(customers);
//    Mockito.when(customerMapper.fromListCustomers(customers)).thenReturn(expected);
//    List<CustomerDTO> result = underTest.getAllCustomers();
//    AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
//}