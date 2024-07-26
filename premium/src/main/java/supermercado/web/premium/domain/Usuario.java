package supermercado.web.premium.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(schema = "supermercado", name = "user")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "username", nullable = false)
    @NotNull(message = "Usuario precisa cadastrar username")
    private String username;

    @Column(name = "password", nullable = false)
    @NotNull(message = "Usuario precisa cadastrar senha")
    private String password;

    @Column(name = "email", nullable = false)
    @NotNull(message = "Usuario precisa cadastrar email")
    private String email;

    @Column(name = "isAdmin", nullable = false)
    @NotNull
    private Boolean isAdmin;
}
