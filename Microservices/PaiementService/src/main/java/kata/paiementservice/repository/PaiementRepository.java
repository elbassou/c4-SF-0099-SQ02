package kata.paiementservice.repository;

import kata.paiementservice.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
