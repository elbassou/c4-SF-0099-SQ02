package kata.paiementservice.configuration;

import kata.paiementservice.model.TypePaiement;
import kata.paiementservice.service.impl.Credit3xPaiementStrategie;
import kata.paiementservice.service.impl.Credit4xPaiementStrategie;
import kata.paiementservice.service.impl.VirementBanquePaiementStrategie;
import kata.paiementservice.service.inter.PaiementStrategie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class PaiementStrategieConfig {


    @Bean
    public PaiementStrategie credit3xStrategie() {
        return new Credit3xPaiementStrategie();
    }

    @Bean
    public PaiementStrategie credit4xStrategie() {
        return new Credit4xPaiementStrategie();
    }

    @Bean
    public PaiementStrategie virementBanquePaiementStrategie() {
        return new VirementBanquePaiementStrategie();
    }

    @Bean
    public Map<TypePaiement, PaiementStrategie> paiementStrategiesMap(
            PaiementStrategie credit3xStrategie,
            PaiementStrategie credit4xStrategie,
             PaiementStrategie virementBanquePaiementStrategie
    ) {
        Map<TypePaiement, PaiementStrategie> strategies = new HashMap<>();
        strategies.put(TypePaiement.CREDIT_3X, credit3xStrategie);
        strategies.put(TypePaiement.CREDIT_4X, credit4xStrategie);
        strategies.put(TypePaiement.VIREMENT, virementBanquePaiementStrategie);
        return strategies;
    }

}
