package kata.paiementservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionPaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Enumerated(EnumType.STRING)
    private TypePaiement typePaiement;

    private String description;
    @Column(precision = 19, scale = 2)
    private BigDecimal frais;

    private Integer nbEchenace;

    @ElementCollection
    private List<Long> commandesIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionPaiement that = (OptionPaiement) o;
        return Objects.equals(id, that.id) && typePaiement == that.typePaiement && Objects.equals(description, that.description) && Objects.equals(frais, that.frais) && Objects.equals(nbEchenace, that.nbEchenace) && Objects.equals(commandesIds, that.commandesIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typePaiement, description, frais, nbEchenace, commandesIds);
    }

}