package ma.emsi.gestionarticle.mapper;

import ma.emsi.gestionarticle.dto.ArticleDTO;
import ma.emsi.gestionarticle.entities.Article;

public interface ArticleMapper {
    Article fromArticleDTOToArticle(ArticleDTO articleDTO);
    ArticleDTO fromArticleToArticleDTO(Article article);
}
