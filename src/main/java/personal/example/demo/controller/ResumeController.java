package personal.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import personal.example.demo.common.BaseReponse;
import personal.example.demo.utils.JwtUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
  private final JwtUtil jwtUtil;

  @PostMapping("/upload")
  public ResponseEntity<?> uploadResume(
      @RequestHeader("Authorization") String authorization,
      @RequestParam("file") MultipartFile file,
      @RequestParam("name") String name) {
    try {
      String account = jwtUtil.extractAccountFromHeader(authorization);
      // TODO: 使用 Tika 解析 File 并保存到数据库
      return ResponseEntity.ok(BaseReponse.success("上传成功"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(BaseReponse.badRequest("上传失败: " + e.getMessage()));
    }
  }
}
