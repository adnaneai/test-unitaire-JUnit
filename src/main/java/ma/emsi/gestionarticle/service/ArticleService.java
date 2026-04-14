package ma.emsi.gestionarticle.service;

import ma.emsi.gestionarticle.dto.ArticleDTO;
import ma.emsi.gestionarticle.exceptions.ArticleNotFoundException;
import org.springframework.data.domain.Page;

public interface ArticleService {
    ArticleDTO save(ArticleDTO articleDTO) throws ArticleNotFoundException;
    Page<ArticleDTO> getAllArticles(int page, int size);
    ArticleDTO getArticleById(Long id) throws ArticleNotFoundException;
    ArticleDTO updateArticleById(Long id,ArticleDTO articleDTO) throws ArticleNotFoundException;
    void deleteArticleById(Long id) throws ArticleNotFoundException;
}
