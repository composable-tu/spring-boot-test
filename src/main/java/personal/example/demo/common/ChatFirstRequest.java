package personal.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatFirstRequest {
  public enum InterviewType {
    TECH, // 技术岗
    POSTGRADUATE, // 考研
    CIVIL_SERVICE // 考公
  }

  public Long resume_id;
  public InterviewType interviewType;
}
