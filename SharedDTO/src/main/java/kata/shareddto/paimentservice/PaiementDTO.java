package kata.shareddto.paimentservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementDTO {

    private Long id;
    private BigDecimal montantTotal;
    private TypePaiement paymentType;
    private Long commandeId;  // Commande
}
