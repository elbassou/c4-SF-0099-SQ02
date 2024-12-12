package kata.shareddto.paimentservice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaiementResponseDTO {

    private String paiementId;
    private Long commandeId;
    private String status;

}
