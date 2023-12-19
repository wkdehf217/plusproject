package plus.plusproject.user.repository;

import plus.plusproject.user.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository< EmailAuth, String > {
}
