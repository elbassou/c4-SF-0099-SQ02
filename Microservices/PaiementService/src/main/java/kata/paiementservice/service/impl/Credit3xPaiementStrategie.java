package kata.paiementservice.service.impl;

import kata.paiementservice.dto.PaiementRequestDTO;
import kata.paiementservice.dto.ResultatPaiement;
import kata.paiementservice.model.Echeancier;
import kata.paiementservice.model.Paiement;
import kata.paiementservice.model.TypePaiement;
import kata.paiementservice.service.inter.PaiementStrategie;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Credit3xPaiementStrategie implements PaiementStrategie {

    @Override
    public ResultatPaiement processPaiement(Paiement paiement ) {

        // Simuler le traitement de paiement

        return new ResultatPaiement(true, "Paiement effectué avec succès (Crédit 3x)",paiement.getMontantTotal());
    }

    @Override
    public TypePaiement getType() {
        return TypePaiement.CREDIT_3X;
    }

    @Override
    public List<Echeancier> generateEcheances(PaiementRequestDTO paiementRequest) {
        BigDecimal montantTotal = paiementRequest.getMontantTotal();
        BigDecimal montantParEcheance = montantTotal.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP); // Division par 3 avec arrondi à 2 décimales

        List<Echeancier> echeances = new ArrayList<>();
        LocalDate dateDebut = LocalDate.now();

        for (int i = 0; i < 3; i++) {
            Echeancier echeance = new Echeancier();
            echeance.setDatePaiement(dateDebut.plusMonths(i)); // Paiement mensuel

            // Ajuster le montant de la dernière échéance
            if (i == 2) {
                BigDecimal montantReste = montantTotal.subtract(montantParEcheance.multiply(BigDecimal.valueOf(2)));
                echeance.setMontant(montantReste);
            } else {
                echeance.setMontant(montantParEcheance);
            }

            echeances.add(echeance);
        }

        return echeances;
    }
}
