package ma.emsi.gestionarticle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleDTO {
    private String description;
    private float price;
    private int quantity;
}
