package ma.emsi.gestionarticle.dto;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor  @AllArgsConstructor
public class ArticleDTO {
    private Long id;
    private String description;
    private float price;
    private int quantity;
}
