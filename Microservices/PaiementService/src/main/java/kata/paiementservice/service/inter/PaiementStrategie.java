package kata.paiementservice.service.inter;

import kata.paiementservice.dto.PaiementRequestDTO;
import kata.paiementservice.dto.ResultatPaiement;
import kata.paiementservice.model.Echeancier;
import kata.paiementservice.model.Paiement;
import kata.paiementservice.model.TypePaiement;
import java.util.List;

public interface PaiementStrategie {

    ResultatPaiement processPaiement(Paiement paiement);
    TypePaiement getType();
    List<Echeancier> generateEcheances(PaiementRequestDTO paiementRequest);

}
