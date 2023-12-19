package plus.plusproject.repository.user;

import plus.plusproject.entity.user.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository< EmailAuth, String > {
}
