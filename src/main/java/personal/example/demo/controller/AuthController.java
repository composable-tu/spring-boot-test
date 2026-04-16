package personal.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.example.demo.common.AuthRequest;
import personal.example.demo.common.BaseReponse;
import personal.example.demo.common.LoginResponse;
import personal.example.demo.entity.User;
import personal.example.demo.service.UserService;
import personal.example.demo.utils.JwtUtil;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
  private final UserService userService;
  private final JwtUtil jwtUtil;

  @PostMapping("/signup")
  public ResponseEntity<BaseReponse<?>> signup(@RequestBody AuthRequest request) {
    try {
      User user = new User(request.getAccount(), request.getPassword());
      userService.save(user);
      return ResponseEntity.ok(BaseReponse.success("注册成功，请登录"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(BaseReponse.badRequest("注册失败: " + e.getMessage()));
    }
  }

  @PostMapping("/login")
  public ResponseEntity<BaseReponse<?>> login(@RequestBody AuthRequest request) {
    try {
      User user = userService.login(request.getAccount(), request.getPassword());
      String token = jwtUtil.generateToken(user.getAccount());
      return ResponseEntity.ok(BaseReponse.success("登录成功", new LoginResponse(token)));
    } catch (Exception e) {
      return ResponseEntity.status(401).body(BaseReponse.unauthorized("登录失败: " + e.getMessage()));
    }
  }
}
