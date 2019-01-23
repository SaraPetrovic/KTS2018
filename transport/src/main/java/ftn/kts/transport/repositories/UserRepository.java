package ftn.kts.transport.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ftn.kts.transport.enums.DocumentVerification;
import ftn.kts.transport.model.User;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByDocumentVerified(DocumentVerification documentVerified);
}
