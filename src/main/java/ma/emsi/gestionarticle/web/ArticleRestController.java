package ma.emsi.gestionarticle.web;

import ma.emsi.gestionarticle.dto.ArticleDTO;
import ma.emsi.gestionarticle.exceptions.ArticleNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ArticleRestController {
    public ResponseEntity<ArticleDTO> saveArticle(ArticleDTO articleDTO);
    public ResponseEntity<Page<ArticleDTO>> getAllArticles(int page, int size);
    public ResponseEntity<ArticleDTO> getArticleById(Long id) throws ArticleNotFoundException;
    public ResponseEntity<ArticleDTO> updateArticleById(Long id, ArticleDTO articleDTO) throws ArticleNotFoundException;
    public void deleteArticleById(Long id) throws ArticleNotFoundException;
}
