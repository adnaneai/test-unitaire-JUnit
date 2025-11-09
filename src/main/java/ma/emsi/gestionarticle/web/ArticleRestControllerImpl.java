package ma.emsi.gestionarticle.web;

import ma.emsi.gestionarticle.dto.ArticleDTO;
import ma.emsi.gestionarticle.exceptions.ArticleNotFoundException;
import ma.emsi.gestionarticle.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin("*")
public class ArticleRestControllerImpl implements ArticleRestController {
    private ArticleService articleService;

    public ArticleRestControllerImpl(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    @PostMapping
    public ResponseEntity<ArticleDTO> saveArticle(@RequestBody ArticleDTO articleDTO) {
        ArticleDTO savedArticle = articleService.save(articleDTO);
        return ResponseEntity.ok(savedArticle);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ArticleDTO>> getAllArticles(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) {
        Page<ArticleDTO> articleDTOPage = articleService.getAllArticles(page, size);
        return ResponseEntity.ok(articleDTOPage);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) throws ArticleNotFoundException {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        return ResponseEntity.ok(articleDTO);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticleById(@PathVariable Long id,
                                                        @RequestBody ArticleDTO articleDTO) throws ArticleNotFoundException {
        ArticleDTO updatedArticle = articleService.updateArticleById(id, articleDTO);
        return ResponseEntity.ok(updatedArticle);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteArticleById(@PathVariable Long id) throws ArticleNotFoundException {
        articleService.deleteArticleById(id);
    }
}
