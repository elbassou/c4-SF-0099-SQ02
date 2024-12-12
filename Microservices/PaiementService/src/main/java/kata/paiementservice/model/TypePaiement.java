package kata.paiementservice.model;

public enum TypePaiement {
    CREDIT_3X("Crédit 3 fois sans frais"),
    CREDIT_4X("Crédit 4 fois avec 5% de frais"),
    VIREMENT("Virement bancaire avec 1€ de frais");

    private final String description;

    TypePaiement(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
