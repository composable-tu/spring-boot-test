package personal.example.demo.controller;

import com.drew.lang.annotations.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.example.demo.client.LangChainClient;
import personal.example.demo.common.BaseResponse;
import personal.example.demo.common.ChatFirstRequest;
import personal.example.demo.common.LangChainChatRequest;
import personal.example.demo.common.LangChainResponse;
import personal.example.demo.entity.InterviewThread;
import personal.example.demo.service.InterviewService;
import personal.example.demo.service.ResumeService;
import personal.example.demo.utils.JwtUtil;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {
  private final JwtUtil jwtUtil;
  private final InterviewService interviewService;
  private final ResumeService resumeService;
  private final LangChainClient langChainClient;

  @PostMapping("/allThreads")
  public ResponseEntity<BaseResponse<?>> getAllThreads(
      @RequestHeader("Authorization") String authorization) {
    try {
      String account = jwtUtil.extractAccountFromHeader(authorization);
      List<InterviewThread> threads = interviewService.findByUserAccount(account);
      return ResponseEntity.ok(BaseResponse.success(threads));
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
      if (!isValidThreadId(authorization, thread_id))
        return ResponseEntity.badRequest().body(BaseResponse.badRequest("无权限访问该对话"));
      else {
        LangChainResponse response = langChainClient.callHistoryApi(thread_id);
        return ResponseEntity.ok(BaseResponse.success(response));
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(BaseResponse.badRequest("获取对话历史记录失败: " + e.getMessage()));
    }
  }

  @PostMapping("/chatfirst")
  public ResponseEntity<BaseResponse<?>> postChatFirst(
      @RequestHeader("Authorization") String authorization,
      @RequestBody ChatFirstRequest chatFirstRequest) {
    try {
      String account = jwtUtil.extractAccountFromHeader(authorization);
      InterviewThread interviewThread = interviewService.save(account);
      String resumeContent = resumeService.findById(chatFirstRequest.resume_id).getContent();
      String interviewType =
          switch (chatFirstRequest.getInterviewType()) {
            case ChatFirstRequest.InterviewType.TECH -> "软件开发岗求职者";
            case ChatFirstRequest.InterviewType.POSTGRADUATE -> "考研复试者";
            case ChatFirstRequest.InterviewType.CIVIL_SERVICE -> "公务员应聘者";
          };
      String message = "我是一位%s，以下是我的简历内容：\n\n".formatted(interviewType);
      LangChainResponse response =
          langChainClient.callChatApi(interviewThread.getId(), message + resumeContent);
      return ResponseEntity.ok(BaseResponse.success(response));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(BaseResponse.badRequest("对话: " + e.getMessage()));
    }
  }

  @PostMapping("/chat/{thread_id}")
  public ResponseEntity<BaseResponse<?>> postChatByThreadId(
      @RequestHeader("Authorization") String authorization,
      @RequestBody LangChainChatRequest langChainChatRequest,
      @NotNull @PathVariable String thread_id) {
    try {
      if (!isValidThreadId(authorization, thread_id))
        return ResponseEntity.badRequest().body(BaseResponse.badRequest("无权限访问该对话"));
      else {
        LangChainResponse response =
            langChainClient.callChatApi(thread_id, langChainChatRequest.getMessage());
        return ResponseEntity.ok(BaseResponse.success(response));
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(BaseResponse.badRequest("AI 服务出现错误: " + e.getMessage()));
    }
  }

  private Boolean isValidThreadId(String authorization, String thread_id) {
    String account = jwtUtil.extractAccountFromHeader(authorization);
    InterviewThread thread = interviewService.findByThreadId(thread_id);
    return thread.getUser().getAccount().equals(account);
  }
}
