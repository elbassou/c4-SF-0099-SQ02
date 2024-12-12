package kata.paiementservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Echeancier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate datePaiement;
    private BigDecimal montant ;
    private Long commandeId;

    public Echeancier() {
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Echeancier(BigDecimal montantParEcheance, Long commandeId, LocalDate localDate) {
        this.montant = montantParEcheance;
        this.commandeId = commandeId;
        this.datePaiement = localDate;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Echeancier that = (Echeancier) o;
        return Objects.equals(id, that.id) && Objects.equals(datePaiement, that.datePaiement) && Objects.equals(montant, that.montant) && Objects.equals(commandeId, that.commandeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datePaiement, montant, commandeId);
    }
}


