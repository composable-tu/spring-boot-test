package personal.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import personal.example.demo.entity.InterviewThread;

public interface InterviewThreadRepository extends JpaRepository<InterviewThread, String> {
  List<InterviewThread> findByUserAccount(String account);
}
