package personal.example.demo.client;

import java.time.Duration;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import personal.example.demo.common.LangChainResponse;

@Component
public class LangChainClient {
  private final RestClient restClient;

  public LangChainClient(@Value("${langchain.base-url}") String baseUrl) {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(Duration.ofSeconds(30));
    factory.setReadTimeout(Duration.ofSeconds(300)); // AI Agent 服务处理时间较长，因此这里设置超时时间为 5 分钟

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
