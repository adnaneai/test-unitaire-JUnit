package ma.emsi.gestionarticle.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.emsi.gestionarticle.dto.ArticleDTO;
import ma.emsi.gestionarticle.exceptions.ArticleNotFoundException;
import ma.emsi.gestionarticle.service.ArticleService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@ActiveProfiles("test")
@WebMvcTest(ArticleRestControllerImpl.class)
@Import(ArticleRestControllerImpl.class)
class ArticleRestControllerTest {
    @MockitoBean
    private ArticleService articleService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Page<ArticleDTO> articlePage;

    @BeforeEach
    void setUp() {
        this.articlePage = new PageImpl<>(
                List.of(
                        ArticleDTO.builder()
                                .id(1L)
                                .description("Pain")
                                .price(1.2F)
                                .quantity(10).build(),
                        ArticleDTO.builder()
                                .id(2L)
                                .description("Lait")
                                .price(4.0F)
                                .quantity(6).build(),
                        ArticleDTO.builder()
                                .id(3L)
                                .description("Frommage")
                                .price(22.0F)
                                .quantity(8).build()
                )
        );
    }

    @Test
    void shouldGetAllArticles() throws Exception {

        Mockito.when(articleService.getAllArticles(Mockito.anyInt(), Mockito.anyInt())).thenReturn(articlePage);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(articlePage)));
    }
    @Test
    void shouldGetArticleById() throws Exception {
        Long articleId = 1L;
        Mockito.when(articleService.getArticleById(articleId)).thenReturn(articlePage.getContent().getFirst());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/" + articleId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(articlePage.getContent().getFirst())));
    }
    @Test
    void shouldNotGetArticleByInvalidId() throws Exception {
        Long articleId = 9L;
        Mockito.when(articleService.getArticleById(articleId)).thenThrow(ArticleNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/{id}",articleId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
    @Test
    void shouldSaveArticle() throws Exception {
        ArticleDTO articleDTO = articlePage.getContent().get(0);
        String expected = """
                   {
                        "id":1, "description":"Pain", "price":1.2, "quantity":10
                   }
                """;
        Mockito.when(articleService.save(Mockito.any())).thenReturn(articlePage.getContent().get(0));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/articles")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(articleDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
    @Test
    void testUpdateArticle() throws Exception {
        Long articleId = 1L;
        ArticleDTO articleDTO = articlePage.getContent().get(0);
        Mockito.when(articleService.updateArticleById(Mockito.eq(articleId), Mockito.any()))
                .thenReturn(articlePage.getContent().get(0));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/articles/{id}", articleId)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(articleDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(articleDTO)));
    }
    @Test
    void shouldDeleteArticle() throws Exception {
        Long articleId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/articles/{id}", articleId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
//@Test
//void shouldDeleteCustomer() throws Exception {
//    Long customerId=1L;
//    mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/{id}",customerId))
//            .andExpect(MockMvcResultMatchers.status().isNoContent());
//}
