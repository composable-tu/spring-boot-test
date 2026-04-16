package personal.example.demo.controller;

import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import personal.example.demo.common.BaseReponse;
import personal.example.demo.entity.Resume;
import personal.example.demo.entity.User;
import personal.example.demo.repository.UserRepository;
import personal.example.demo.service.ResumeService;
import personal.example.demo.utils.JwtUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final ResumeService resumeService;
  private final Tika tika = new Tika();

  @PostMapping("/upload")
  public ResponseEntity<?> uploadResume(
      @RequestHeader("Authorization") String authorization,
      @RequestParam("file") MultipartFile file,
      @RequestParam("name") String name) {
    try {
      String account = jwtUtil.extractAccountFromHeader(authorization);
      User user =
          userRepository.findByAccount(account).orElseThrow(() -> new RuntimeException("用户不存在"));
      InputStream inputStream = file.getInputStream();
      String content = tika.parseToString(inputStream);
      inputStream.close();

      Resume resume = new Resume(null, user, name, content);
      resumeService.save(resume);
      return ResponseEntity.ok(BaseReponse.success("上传成功"));
    } catch (TikaException e) {
      return ResponseEntity.badRequest()
          .body(BaseReponse.badRequest("Tika 解析失败: " + e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(BaseReponse.badRequest("上传失败: " + e.getMessage()));
    }
  }
}
