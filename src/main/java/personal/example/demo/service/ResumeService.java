package personal.example.demo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.example.demo.entity.Resume;
import personal.example.demo.repository.ResumeRepository;

@Service
@RequiredArgsConstructor
public class ResumeService {
  private final ResumeRepository resumeRepository;

  public List<Resume> findAll() {
    return resumeRepository.findAll();
  }

  public List<Resume> findByUserAccount(String account) {
    return resumeRepository.findByUserAccount(account);
  }

  public void save(Resume resume) {
    resumeRepository.save(resume);
  }

  public Resume findById(Long id) {
    return resumeRepository.findById(id).orElse(null);
  }
}
