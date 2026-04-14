package personal.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.example.demo.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
