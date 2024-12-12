package kata.paiementservice.dto;

import java.math.BigDecimal;

public class ResultatPaiement {

    private boolean success;
    private String message;
    public ResultatPaiement(boolean success, String message, BigDecimal montantPaye) {
        this.success = success;
        this.message = message;
        this.montantPaye = montantPaye;
    }

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }

    private BigDecimal montantPaye;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}


