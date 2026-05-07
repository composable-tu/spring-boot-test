package personal.example.demo.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LangChainResponse {
  private String thread_id;
  private List<LangChainMessage> messages;
  private Boolean has_finished;
}
