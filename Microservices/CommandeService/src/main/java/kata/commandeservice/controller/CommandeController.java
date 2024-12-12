package kata.commandeservice.controller;

import kata.commandeservice.service.CommandeService;
import kata.shareddto.clientservice.ClientDTO;
import kata.shareddto.commandeservice.CommandeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/commandes/v1")
public class CommandeController {

    CommandeService commandeService;
    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;

    }

    @PostMapping("create")
    public ResponseEntity<String> createCommande(@RequestBody CommandeDTO commandeDTO) {


        ClientDTO client = new ClientDTO();
        client.setId(commandeDTO.getClientId());
        BigDecimal montantTotal=  commandeDTO.getMontantTotal();

        this.commandeService.createCommande(client,montantTotal);

        return ResponseEntity.ok().body("Commande created");
    }
}
