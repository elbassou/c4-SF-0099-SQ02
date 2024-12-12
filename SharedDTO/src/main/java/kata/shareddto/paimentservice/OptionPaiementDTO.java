package kata.shareddto.paimentservice;

import kata.shareddto.commandeservice.CommandeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionPaiementDTO {

    private Long id;


    private TypePaiement typePaiement; // CREDIT_3X, CREDIT_4X, BANK_TRANSFER

    private String description;

    private BigDecimal frais;

    private Integer nbEchenace;

    private boolean isActive;

    // @ManyToMany(mappedBy = "optionsPaiement")
    private List<CommandeDTO> commandes;

    public OptionPaiementDTO(TypePaiement typePaiement, String description, BigDecimal frais, int nbEchenace) {

    this.typePaiement = typePaiement;
    this.description = description;
    this.frais = frais;
    this.nbEchenace = nbEchenace;
    }
}
