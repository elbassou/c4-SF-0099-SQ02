package kata.commandeservice.unit.controller;

import kata.commandeservice.controller.CommandeController;
import kata.commandeservice.service.CommandeService;
import kata.shareddto.clientservice.ClientDTO;
import kata.shareddto.commandeservice.CommandeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommandeControllerTest {

    private CommandeService commandeService;
    private CommandeController commandeController;

    @BeforeEach
    void setUp() {

        commandeService = mock(CommandeService.class);
        commandeController = new CommandeController(commandeService);
    }

    @Test
    void testCreateCommandeReturnOk() {

        CommandeDTO commandeDTO = new CommandeDTO();
        commandeDTO.setClientId(123L);
        commandeDTO.setMontantTotal(new BigDecimal("1500.00"));

        ClientDTO expectedClient = new ClientDTO();
        expectedClient.setId(123L);

        CommandeDTO expectedCommandeDTO = new CommandeDTO();
        expectedCommandeDTO.setClientId(123L);
        expectedCommandeDTO.setMontantTotal(new BigDecimal("1500.00"));


        when(commandeService.createCommande(any(ClientDTO.class), any(BigDecimal.class)))
                .thenReturn(expectedCommandeDTO);

        ResponseEntity<String> response = commandeController.createCommande(commandeDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Commande created", response.getBody());

        verify(commandeService, times(1)).createCommande(eq(expectedClient), eq(new BigDecimal("1500.00")));
    }

}
