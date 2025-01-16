package kata.commandeservice.model;


import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import kata.shareddto.paimentservice.TypePaiement;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Commande {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    @Column(precision = 19, scale = 2)
    private BigDecimal montantTotal;

    @Enumerated(EnumType.STRING)
    private TypePaiement TypePaiement;

    @ElementCollection
    private List<Long> optionsPaiementId;

    public kata.shareddto.paimentservice.TypePaiement getTypePaiement() {
        return TypePaiement;
    }

    public void setTypePaiement(kata.shareddto.paimentservice.TypePaiement typePaiement) {
        TypePaiement = typePaiement;
    }


    public List<Long> getOptionsPaiementId() {
        return optionsPaiementId;
    }

    public void setOptionsPaiementId(List<Long> optionsPaiementId) {
        this.optionsPaiementId = optionsPaiementId;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return Objects.equals(id, commande.id) && Objects.equals(clientId, commande.clientId) && Objects.equals(montantTotal, commande.montantTotal) && TypePaiement == commande.TypePaiement && Objects.equals(optionsPaiementId, commande.optionsPaiementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, montantTotal, TypePaiement, optionsPaiementId);
    }

}
