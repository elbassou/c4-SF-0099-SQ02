package kata.paiementservice.service.impl;

import jakarta.transaction.Transactional;
import kata.paiementservice.dto.PaiementRequestDTO;
import kata.paiementservice.dto.ResultatPaiement;
import kata.paiementservice.model.Echeancier;
import kata.paiementservice.model.Paiement;
import kata.paiementservice.model.TypePaiement;
import kata.paiementservice.repository.PaiementRepository;
import kata.paiementservice.service.inter.PaiementStrategie;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Credit4xPaiementStrategie implements PaiementStrategie {

    @Override
    public ResultatPaiement processPaiement(Paiement paiement ) {

        // Simuler le traitement de paiement
        BigDecimal frais = paiement.getMontantTotal().multiply(BigDecimal.valueOf(0.05));
        BigDecimal montantPaye= paiement.getMontantTotal().add(frais);
        paiement.setMontantTotal(montantPaye);


        return new ResultatPaiement(true, "Paiement effectué avec succès (Crédit 4x)",montantPaye);
    }


    @Override
    public TypePaiement getType() {
        return TypePaiement.CREDIT_4X;
    }

    @Override
    public List<Echeancier> generateEcheances(PaiementRequestDTO paiementRequest) {
        BigDecimal montant = paiementRequest.getMontantTotal();
        List<Echeancier> echeances = new ArrayList<>();
        BigDecimal montantParEcheance = (montant.divide(BigDecimal.valueOf(4)) .add((montant.multiply(BigDecimal.valueOf(0.05))).divide(BigDecimal.valueOf(4)) )); // 5% frais
        LocalDate date = LocalDate.now();
        for (int i = 1; i <= 4; i++) {
            echeances.add(new Echeancier(montantParEcheance,paiementRequest.getCommandeId() ,date.plusMonths(i)));
        }
        return echeances;

    }
}
