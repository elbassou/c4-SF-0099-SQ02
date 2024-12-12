package kata.paiementservice.unit.service;

import kata.paiementservice.model.TypePaiement;
import kata.paiementservice.service.PaiementService;
import kata.paiementservice.service.PaiementStrategieFactory;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kata.paiementservice.dto.PaiementRequestDTO;
import kata.paiementservice.dto.PaiementResponseDTO;
import kata.paiementservice.dto.ResultatPaiement;
import kata.paiementservice.model.Echeancier;
import kata.paiementservice.model.Paiement;
import kata.paiementservice.repository.EcheanceRepository;
import kata.paiementservice.repository.PaiementRepository;
import kata.paiementservice.service.inter.PaiementStrategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PaiementServiceTest {

    @InjectMocks
    private PaiementService paiementService;

    @Mock
    private PaiementRepository paiementRepository;

    @Mock
    private PaiementStrategieFactory strategieFactory;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private EcheanceRepository echeanceRepository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(paiementService, "topicPaiement", "paiement-topics");
    }

    @Mock
    private PaiementStrategie mockStrategie;



    @Test
    void testPaiementProcess_Success() throws JsonProcessingException {
        // Arrange
        PaiementRequestDTO requestDTO = new PaiementRequestDTO();
        requestDTO.setCommandeId(1L);
        requestDTO.setMontantTotal(new BigDecimal("100.00"));
        requestDTO.setTypePaiement(TypePaiement.VIREMENT);

        List<Echeancier> echeanciers = List.of(new Echeancier());
        ResultatPaiement resultatPaiement = new ResultatPaiement(true, "Success", new BigDecimal("100.00"));

        when(strategieFactory.getStrategy(TypePaiement.VIREMENT)).thenReturn(mockStrategie);
        when(mockStrategie.generateEcheances(requestDTO)).thenReturn(echeanciers);
        when(mockStrategie.processPaiement(any(Paiement.class))).thenReturn(resultatPaiement);
        when(echeanceRepository.save(any(Echeancier.class))).thenReturn(new Echeancier());
        when(paiementRepository.save(any(Paiement.class))).thenReturn(new Paiement());


        paiementService.paiementProcess().accept(requestDTO);

        verify(echeanceRepository, times(1)).save(any(Echeancier.class));
        verify(paiementRepository, times(1)).save(any(Paiement.class));
        verify(kafkaTemplate, never()).send(anyString(), anyString(), anyString());
    }


    @Test
    void testPaiementProcess_Failure() throws JsonProcessingException {

        String kafkaTopic = "paiement-topics";
        PaiementRequestDTO requestDTO = new PaiementRequestDTO();
        requestDTO.setCommandeId(1L);
        requestDTO.setMontantTotal(new BigDecimal("100.00"));
        requestDTO.setTypePaiement(TypePaiement.VIREMENT);

        ResultatPaiement resultatPaiement = new ResultatPaiement(false, "Failure", new BigDecimal("0.00"));

        when(strategieFactory.getStrategy(TypePaiement.VIREMENT)).thenReturn(mockStrategie);
        when(mockStrategie.generateEcheances(requestDTO)).thenReturn(List.of());
        when(mockStrategie.processPaiement(any(Paiement.class))).thenReturn(resultatPaiement);

        PaiementResponseDTO responseDTO = new PaiementResponseDTO();
        responseDTO.setCommandeId(1L);
        responseDTO.setStatus("ECHEC");

        when(objectMapper.writeValueAsString(any(PaiementResponseDTO.class))).thenReturn("{\"commandeId\":1,\"status\":\"ECHEC\"}");

        paiementService.paiementProcess().accept(requestDTO);

        verify(paiementRepository, never()).save(any(Paiement.class));
        verify(kafkaTemplate, times(1)).send(eq(kafkaTopic), eq("paiement"), anyString());
    }


}
