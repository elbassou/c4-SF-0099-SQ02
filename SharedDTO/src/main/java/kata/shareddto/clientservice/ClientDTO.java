package kata.shareddto.clientservice;

import kata.shareddto.commandeservice.CommandeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private List<CommandeDTO> commandes;
}
