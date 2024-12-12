package kata.paiementservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PaiementResponseDTO {

    private String paiementId;
    private Long commandeId;
    private String status;

    public String getPaiementId() {
        return paiementId;
    }

    public void setPaiementId(String paiementId) {
        this.paiementId = paiementId;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
