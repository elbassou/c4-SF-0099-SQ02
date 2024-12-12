package kata.commandeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kata.commandeservice.model.Commande;
import kata.commandeservice.repository.CommandeRepository;
import kata.shareddto.clientservice.ClientDTO;
import kata.shareddto.commandeservice.CommandeDTO;
import kata.shareddto.paimentservice.OptionPaiementDTO;
import kata.shareddto.paimentservice.PaiementRequestDTO;
import kata.shareddto.paimentservice.PaiementResponseDTO;
import kata.shareddto.paimentservice.TypePaiement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Service
public class CommandeService {

    private   KafkaTemplate<String,PaiementRequestDTO> kafkaTemplate;
    private  CommandeRepository commandeRepository;
    private ObjectMapper objectMapper;
    private static final String STATUS_ECHEC ="ECHEC";

    @Value("${montant.seuil}")
    public String montantSeuil;

    public CommandeService(CommandeRepository commandeRepository, @Autowired(required = true) KafkaTemplate<String, PaiementRequestDTO> kafkaTemplate){
        this.commandeRepository = commandeRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public CommandeDTO createCommande(ClientDTO client, BigDecimal montantTotal) {
        CommandeDTO commandeDTO = new CommandeDTO();
        commandeDTO.setClientId(client.getId());
        commandeDTO.setMontantTotal(montantTotal);
        processCommande(commandeDTO);
        return commandeDTO;
    }

    @Transactional
    public CommandeDTO processCommande(CommandeDTO commandeDTO) {

        Commande commande = new Commande();
        commande.setClientId(commandeDTO.getClientId());
        commande.setMontantTotal(commandeDTO.getMontantTotal());

        List<OptionPaiementDTO> optionsPaiement    = calculDesOptionsPaiement(commandeDTO.getMontantTotal());

        // ici on suppose que le client à choisi la premiere option
        TypePaiement  typePaiement = optionsPaiement.get(0).getTypePaiement();

        commande.setTypePaiement(typePaiement);

        commande = commandeRepository.save(commande);

        PaiementRequestDTO paymentRequest = new PaiementRequestDTO(commande.getId(), commande.getMontantTotal(), commande.getTypePaiement());
        kafkaTemplate.send("commande-topics","commande",paymentRequest);

        return commandeDTO;
    }


    public List<OptionPaiementDTO> calculDesOptionsPaiement( BigDecimal montantTotal) {

        List<OptionPaiementDTO> options = new ArrayList<>();

        if (montantTotal.compareTo(new BigDecimal(this.montantSeuil.trim())) > 0) {
            options.add(new OptionPaiementDTO(TypePaiement.CREDIT_3X, "Crédit 3 fois sans frais", BigDecimal.ZERO, 3));
            options.add(new OptionPaiementDTO(TypePaiement.CREDIT_4X, "Crédit 4 fois avec 5% de frais", new BigDecimal("0.05"), 4));
        }
        options.add(new OptionPaiementDTO(TypePaiement.VIREMENT, "Virement bancaire avec 1€ de frais", new BigDecimal("1"), 1));

        return options;
    }

   // pour faire le rollback lorsqu il y un echec dans le paiement
    @Bean
    public Consumer<PaiementResponseDTO> processStatusPaiement() {

        return input -> {

            if(input.getStatus().equals(STATUS_ECHEC)) {
                commandeRepository.deleteById(input.getCommandeId());
            }
        };
    }

}
