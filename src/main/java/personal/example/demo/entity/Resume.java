package personal.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Resume {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account", nullable = false)
  private User user;

  @Column(nullable = false)
  String name;

  @Column(nullable = false)
  String content;
}
