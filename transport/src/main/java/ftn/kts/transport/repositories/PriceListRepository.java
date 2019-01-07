package ftn.kts.transport.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.model.PriceList;

@Component
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

	Optional<PriceList> findByActive(boolean active);
}
