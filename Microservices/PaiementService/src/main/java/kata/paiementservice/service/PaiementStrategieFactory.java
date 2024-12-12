package kata.paiementservice.service;

import kata.paiementservice.model.TypePaiement;
import kata.paiementservice.service.inter.PaiementStrategie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaiementStrategieFactory {

    private final Map<TypePaiement, PaiementStrategie> strategies;

    public PaiementStrategieFactory(List<PaiementStrategie> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(
                        PaiementStrategie::getType,
                        Function.identity(),
                        (existing, replacement) -> replacement
                ));
    }

    public PaiementStrategie getStrategy(TypePaiement type) {
        return strategies.get(type);
    }


}
