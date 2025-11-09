package ma.emsi.gestionarticle.mapper;

import ma.emsi.gestionarticle.dto.ArticleDTO;
import ma.emsi.gestionarticle.entities.Article;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ArticleMapperImpl implements ArticleMapper {
    @Override
    public Article fromArticleDTOToArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        return article;
    }
    @Override
    public ArticleDTO fromArticleToArticleDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);
        return articleDTO;
    }
}
