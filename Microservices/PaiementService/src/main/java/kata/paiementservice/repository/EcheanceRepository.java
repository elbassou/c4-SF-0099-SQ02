package kata.paiementservice.repository;

import kata.paiementservice.model.Echeancier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcheanceRepository extends JpaRepository<Echeancier,Long> {
}
