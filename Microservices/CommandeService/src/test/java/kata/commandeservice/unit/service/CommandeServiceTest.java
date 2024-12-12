package kata.commandeservice.unit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kata.commandeservice.exception.CommandeNotFoundException;
import kata.commandeservice.model.Commande;
import kata.commandeservice.repository.CommandeRepository;
import kata.commandeservice.service.CommandeService;
import kata.shareddto.clientservice.ClientDTO;
import kata.shareddto.commandeservice.CommandeDTO;
import kata.shareddto.paimentservice.OptionPaiementDTO;
import kata.shareddto.paimentservice.PaiementRequestDTO;
import kata.shareddto.paimentservice.PaiementResponseDTO;
import kata.shareddto.paimentservice.TypePaiement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)

class CommandeServiceTest {

    @InjectMocks
    private CommandeService commandeService;

    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private KafkaTemplate<String, PaiementRequestDTO> kafkaTemplate;

    @BeforeEach
    void setUp() {
        commandeService = new CommandeService(commandeRepository, kafkaTemplate);
    }

    @Test
    void testProcessCommande_SaveCommandeAndSendKafkaMessage() {
        // Arrange
        CommandeDTO commandeDTO = new CommandeDTO();
        commandeDTO.setClientId(1L);
        commandeDTO.setMontantTotal(new BigDecimal("1200"));

        Commande savedCommande = new Commande();
        savedCommande.setId(1L);
        savedCommande.setClientId(1L);
        savedCommande.setMontantTotal(new BigDecimal("1200"));
        savedCommande.setTypePaiement(TypePaiement.CREDIT_3X);

        when(commandeRepository.save(any(Commande.class))).thenReturn(savedCommande);

        CommandeDTO result = commandeService.processCommande(commandeDTO);

        assertNotNull(result);
        assertEquals(commandeDTO.getClientId(), result.getClientId());
        assertEquals(commandeDTO.getMontantTotal(), result.getMontantTotal());

        verify(commandeRepository, times(1)).save(any(Commande.class));
        verify(kafkaTemplate, times(1)).send(eq("commande-topics"), eq("commande"), any(PaiementRequestDTO.class));
    }

    @Test
    void testCalculDesOptionsPaiement_ReturnCorrectOptions() {

        BigDecimal montantTotal = new BigDecimal("1200");

        List<OptionPaiementDTO> options = commandeService.calculDesOptionsPaiement(montantTotal);

        assertEquals(3, options.size());
        assertEquals(TypePaiement.CREDIT_3X, options.get(0).getTypePaiement());
    }

    @Test
    void testProcessStatusPaiement_DeleteCommandeOnECHEC() {

        PaiementResponseDTO paiementResponseDTO = new PaiementResponseDTO();
        paiementResponseDTO.setCommandeId(1L);
        paiementResponseDTO.setStatus("ECHEC");

        doNothing().when(commandeRepository).deleteById(1L);

        Consumer<PaiementResponseDTO> processStatusPaiement = commandeService.processStatusPaiement();

        processStatusPaiement.accept(paiementResponseDTO);

        verify(commandeRepository, times(1)).deleteById(1L);
    }
}
