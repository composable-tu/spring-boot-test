package personal.example.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import personal.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByAccount(String account);
}
