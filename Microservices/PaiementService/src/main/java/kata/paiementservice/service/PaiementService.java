package kata.paiementservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kata.paiementservice.dto.PaiementRequestDTO;
import kata.paiementservice.dto.PaiementResponseDTO;
import kata.paiementservice.dto.ResultatPaiement;
import kata.paiementservice.model.Echeancier;
import kata.paiementservice.model.Paiement;
import kata.paiementservice.repository.EcheanceRepository;
import kata.paiementservice.repository.PaiementRepository;
import kata.paiementservice.service.inter.ConstanteStatusPaiement;
import kata.paiementservice.service.inter.PaiementStrategie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class PaiementService {

    @Value("${name.topic.kafka.paiement}")
    private String topicPaiement;

    private final PaiementRepository paymentRepository;
    private final PaiementStrategieFactory strategyFactory;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplatePaiement;
    private final EcheanceRepository echeanceRepository;


    public PaiementService(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplatePaiement,
                           PaiementRepository paymentRepository, PaiementStrategieFactory strategyFactory,
                           EcheanceRepository echeanceRepository) {
        this.paymentRepository = paymentRepository;
        this.strategyFactory = strategyFactory;
        this.kafkaTemplatePaiement = kafkaTemplatePaiement;
        this.objectMapper = objectMapper;
        this.echeanceRepository = echeanceRepository;
    }
    @Transactional
    @Bean
    public Consumer<PaiementRequestDTO> paiementProcess() {

        return paiementRequestDTO -> {

            Paiement paiement = new Paiement();

            paiement.setMontantTotal(paiementRequestDTO.getMontantTotal());
            paiement.setTypePaiement(paiementRequestDTO.getTypePaiement());
            paiement.setCommandeId(paiementRequestDTO.getCommandeId());

            PaiementStrategie strategy = strategyFactory.getStrategy(paiementRequestDTO.getTypePaiement());

            // Générer les échéances
            List<Echeancier> echeancier = strategy.generateEcheances(paiementRequestDTO);

            // Persister les échéances
            echeancier.forEach(echeance -> {
                echeance.setCommandeId(paiementRequestDTO.getCommandeId());
                echeanceRepository.save(echeance);
            });

            ResultatPaiement resultatPaiement = strategy.processPaiement(paiement);
            paiement.setMontantTotal(resultatPaiement.getMontantPaye());

            if (resultatPaiement.isSuccess()) {
                Paiement savedPaiement = paymentRepository.save(paiement);

                PaiementResponseDTO paiementResponseDTO = new PaiementResponseDTO();
                paiementResponseDTO.setPaiementId(savedPaiement.getId().toString());
                paiementResponseDTO.setCommandeId(paiement.getCommandeId());
                paiementResponseDTO.setStatus(ConstanteStatusPaiement.STATUS_SUCCESS);
                try {
                    kafkaTemplatePaiement.send(topicPaiement,"paiement",objectMapper.writeValueAsString(paiementResponseDTO));
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                PaiementResponseDTO paiementResponseDTO = new PaiementResponseDTO();
                paiementResponseDTO.setCommandeId(paiement.getCommandeId());
                paiementResponseDTO.setStatus(ConstanteStatusPaiement.STATUS_ECHEC);
                try {
                    kafkaTemplatePaiement.send(topicPaiement,"paiement",objectMapper.writeValueAsString(paiementResponseDTO));
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
