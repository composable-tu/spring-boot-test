package personal.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import personal.example.demo.entity.InterviewThread;
import personal.example.demo.entity.User;
import personal.example.demo.repository.InterviewThreadRepository;

@Service
@RequiredArgsConstructor
public class InterviewService {
  private final InterviewThreadRepository interviewThreadRepository;
  private final UserService userService;

  public List<InterviewThread> findAll() {
    return interviewThreadRepository.findAll();
  }

  public List<InterviewThread> findByUserAccount(String account) {
    return interviewThreadRepository.findByUserAccount(account);
  }

  public void save(String account) {
    User user = userService.findByAccount(account);
    InterviewThread interviewThread = new InterviewThread();
    interviewThread.setId(generateThreadId(account));
    interviewThread.setUser(user);
    interviewThreadRepository.save(interviewThread);
  }

  public InterviewThread findByThreadId(String threadId) {
    return interviewThreadRepository.findById(threadId).orElse(null);
  }

  private @NonNull String generateThreadId(@NonNull String account) {
    byte[] accountBytes = account.getBytes(StandardCharsets.UTF_8);
    String uuid = UUID.nameUUIDFromBytes(accountBytes).toString().replace("-", "");
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    return uuid + "_" + timestamp;
  }
}
