package ma.emsi.gestionarticle.repository;

import ma.emsi.gestionarticle.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    Page<Article> findAll(Pageable pageable);

    Object findArticleById(Long id);
}
