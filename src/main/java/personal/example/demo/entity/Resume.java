package personal.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
