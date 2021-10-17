package br.com.saleback.dto;

import br.com.saleback.model.Product;
import br.com.saleback.model.Solicitation;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSolicitationDTO {

    BigDecimal valueDiscount;
    Solicitation solicitation;
    List<Product> productList;

}
