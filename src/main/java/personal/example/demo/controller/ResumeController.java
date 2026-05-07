package personal.example.demo.controller;

import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import personal.example.demo.common.BaseResponse;
import personal.example.demo.entity.Resume;
import personal.example.demo.entity.User;
import personal.example.demo.service.ResumeService;
import personal.example.demo.service.UserService;
import personal.example.demo.utils.JwtUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
  private final JwtUtil jwtUtil;
  private final UserService userService;
  private final ResumeService resumeService;
  private final Tika tika = new Tika();

  @PostMapping("/upload")
  public ResponseEntity<BaseResponse<?>> uploadResume(
      @RequestHeader("Authorization") String authorization,
      @RequestParam("file") MultipartFile file,
      @RequestParam("name") String name) {
    try {
      String account = jwtUtil.extractAccountFromHeader(authorization);
      User user = userService.findByAccount(account);
      InputStream inputStream = file.getInputStream();
      String content = tika.parseToString(inputStream);
      inputStream.close();

      Resume resume = new Resume(null, user, name, content);
      resumeService.save(resume);
      return ResponseEntity.ok(BaseResponse.success("上传成功"));
    } catch (TikaException e) {
      return ResponseEntity.badRequest()
          .body(BaseResponse.badRequest("Tika 解析失败: " + e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(BaseResponse.badRequest("上传失败: " + e.getMessage()));
    }
  }
}
