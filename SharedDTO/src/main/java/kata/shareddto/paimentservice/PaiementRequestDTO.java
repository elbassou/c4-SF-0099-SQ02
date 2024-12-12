package kata.shareddto.paimentservice;

import kata.shareddto.paimentservice.TypePaiement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementRequestDTO {

    private Long commandeId;
    private BigDecimal montantTotal;
    private TypePaiement typePaiement;
 //   private String status;

}
