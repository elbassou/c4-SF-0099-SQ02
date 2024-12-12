package kata.paiementservice.service.impl;

import kata.paiementservice.dto.ResultatPaiement;
import kata.paiementservice.model.Echeancier;
import kata.paiementservice.model.Paiement;
import kata.paiementservice.model.TypePaiement;
import kata.paiementservice.service.inter.PaiementStrategie;
import kata.paiementservice.dto.PaiementRequestDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class VirementBanquePaiementStrategie implements PaiementStrategie {


    @Override
    public ResultatPaiement processPaiement(Paiement paiement ) {
        // Simuler le traitement de paiement
        BigDecimal frais = BigDecimal.ONE;
        BigDecimal montantPaye = paiement.getMontantTotal().add(frais);

        return new ResultatPaiement(true, "Paiement effectué avec succès (Virement)",montantPaye);

    }

    @Override
    public TypePaiement getType() {
        return TypePaiement.VIREMENT;
    }

    @Override
    public List<Echeancier> generateEcheances(PaiementRequestDTO paiementRequest) {
        BigDecimal frais = BigDecimal.ONE; // Frais fixe de 1€
        BigDecimal montantTotalAvecFrais = paiementRequest.getMontantTotal().add(frais);


        Echeancier echeance = new Echeancier();
        echeance.setDatePaiement(LocalDate.now());
        echeance.setMontant(montantTotalAvecFrais);
        // Retourne une liste contenant une seule échéance
        return List.of(echeance);
    }


}
