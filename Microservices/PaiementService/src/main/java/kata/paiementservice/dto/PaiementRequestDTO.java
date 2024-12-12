package kata.paiementservice.dto;

import kata.paiementservice.model.TypePaiement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
public class PaiementRequestDTO {

    private Long commandeId;
    private BigDecimal montantTotal;
    private TypePaiement typePaiement;

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public TypePaiement getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(TypePaiement typePaiement) {
        this.typePaiement = typePaiement;
    }


    @Override
    public String toString() {
        return "PaiementRequestDTO{" +
                "commandeId=" + commandeId +
                ", montantTotal=" + montantTotal +
                ", typePaiement=" + typePaiement +
                '}';
    }

}
