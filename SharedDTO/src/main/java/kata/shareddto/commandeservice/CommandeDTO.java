package kata.shareddto.commandeservice;

import kata.shareddto.paimentservice.TypePaiement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandeDTO {

    private Long id;
    private Long clientId;
    private BigDecimal montantTotal;
    private TypePaiement typePaiement;
}
