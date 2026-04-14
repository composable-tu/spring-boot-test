package personal.example.demo.common;

import lombok.Data;

@Data
public class AuthRequest {
  private String account;
  private String password;
}
