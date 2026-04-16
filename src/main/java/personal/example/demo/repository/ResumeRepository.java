package personal.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import personal.example.demo.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
  List<Resume> findByUserAccount(String account);
}
