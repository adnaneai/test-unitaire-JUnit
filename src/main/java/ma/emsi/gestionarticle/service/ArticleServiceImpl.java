package ma.emsi.gestionarticle.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.gestionarticle.dto.ArticleDTO;
import ma.emsi.gestionarticle.entities.Article;
import ma.emsi.gestionarticle.exceptions.ArticleNotFoundException;
import ma.emsi.gestionarticle.mapper.ArticleMapper;
import ma.emsi.gestionarticle.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    private ArticleMapper articleMapper;
    private ArticleRepository articleRepository;
    private static final String ARTICLE_NOT_FOUND = "Article not found";
    @Override
    public ArticleDTO save(ArticleDTO articleDTO) throws ArticleNotFoundException {
        if (articleDTO.getId() != null) {
            Optional<Article> existingArticle = articleRepository.findById(articleDTO.getId());
            if (existingArticle.isPresent()) {
                throw new ArticleNotFoundException("Article with id " + articleDTO.getId() + " already exists");
            }
        }
        log.debug("Saving new article");
        Article article=articleMapper.fromArticleDTOToArticle(articleDTO);
        Article savedArticle=articleRepository.save(article);
        return articleMapper.fromArticleToArticleDTO(savedArticle);
    }

    @Override
    public Page<ArticleDTO> getAllArticles(int page, int size) {
        log.debug("Getting all articles");
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articlesPage = articleRepository.findAll(pageable);
        return articlesPage.map(article -> articleMapper.fromArticleToArticleDTO(article));
    }

    @Override
    public ArticleDTO getArticleById(Long id) throws ArticleNotFoundException {
        log.debug("Getting article by id {}", id);
        Article article=articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ARTICLE_NOT_FOUND));
        return articleMapper.fromArticleToArticleDTO(article);
    }

    @Override
    public ArticleDTO updateArticleById(Long id, ArticleDTO articleDTO) throws ArticleNotFoundException {
        log.debug("Updating article by id {}", id);
        Article article=articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ARTICLE_NOT_FOUND));
        if (articleDTO.getDescription() != null) {
            article.setDescription(articleDTO.getDescription());
        }
        if (articleDTO.getPrice() != 0.0){
            article.setPrice(articleDTO.getPrice());
        }
        if (articleDTO.getQuantity() != 0){
            article.setQuantity(articleDTO.getQuantity());
        }
        Article updatedArticle=articleRepository.save(article);
        return articleMapper.fromArticleToArticleDTO(updatedArticle);
    }

    @Override
    public void deleteArticleById(Long id) throws ArticleNotFoundException {
        log.debug("Deleting article by id {}", id);
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ARTICLE_NOT_FOUND));
        articleRepository.delete(article);
    }
}
