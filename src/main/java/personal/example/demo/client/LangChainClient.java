package personal.example.demo.client;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import personal.example.demo.common.LangChainResponse;

@Component
public class LangChainClient {
  private final RestClient restClient;

  public LangChainClient(@Value("${langchain.base-url}") String baseUrl) {
    this.restClient =
        RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();
  }

  public LangChainResponse callChatApi(String threadId, String message) {
    try {
      return restClient
          .post()
          .uri("/chat/{threadId}", threadId)
          .contentType(MediaType.APPLICATION_JSON)
          .body(Map.of("message", message))
          .retrieve()
          .body(LangChainResponse.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public LangChainResponse callHistoryApi(String threadId) {
    try {
      return restClient
          .get()
          .uri("/history/{threadId}", threadId)
          .retrieve()
          .body(LangChainResponse.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
