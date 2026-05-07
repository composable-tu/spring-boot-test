package personal.example.demo.controller;

import com.drew.lang.annotations.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.example.demo.common.BaseResponse;
import personal.example.demo.entity.InterviewThread;
import personal.example.demo.service.InterviewService;
import personal.example.demo.utils.JwtUtil;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {
  private final JwtUtil jwtUtil;
  private final InterviewService interviewService;

  @PostMapping("/allThreads")
  public ResponseEntity<BaseResponse<?>> getAllThreads(
      @RequestHeader("Authorization") String authorization) {
    try {
      String account = jwtUtil.extractAccountFromHeader(authorization);
      return ResponseEntity.ok(BaseResponse.success(interviewService.findByUserAccount(account)));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(BaseResponse.badRequest("获取对话线程失败: " + e.getMessage()));
    }
  }

  @PostMapping("/history/{thread_id}")
  public ResponseEntity<BaseResponse<?>> getChatHistoryByThreadId(
      @RequestHeader("Authorization") String authorization,
      @NotNull @PathVariable String thread_id) {
    try {
      String account = jwtUtil.extractAccountFromHeader(authorization);
      InterviewThread thread = interviewService.findByThreadId(thread_id);
      if (!thread.getUser().getAccount().equals(account)) {
        return ResponseEntity.badRequest().body(BaseResponse.badRequest("无权限访问该对话"));
      } else return ResponseEntity.ok(BaseResponse.success(thread));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(BaseResponse.badRequest("获取对话历史记录失败: " + e.getMessage()));
    }
  }
}
