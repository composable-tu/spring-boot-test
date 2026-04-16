package personal.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
  @Id
  @Column(nullable = false)
  private String account;

  @Column(nullable = false)
  private String password;
}
