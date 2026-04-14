package personal.example.demo.service;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import personal.example.demo.entity.User;
import personal.example.demo.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public void save(User user) {
    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    userRepository.save(user);
  }

  public User login(String username, String password) {
    User user =
        userRepository.findByAccount(username).orElseThrow(() -> new RuntimeException("用户不存在"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("密码错误");
    }

    return user;
  }
}
