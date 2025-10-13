package com.example.demo.domain.entities.solicitacoes;

import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.enums.Genero;
import com.example.demo.domain.enums.StatusSolicitacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "solicitacaoCadastroUsuario")
@Table(name = "solicitacao_cadastro_usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SolicitacaoCadastroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estrutura_id", nullable = false)
    private Estrutura estrutura;

    @Column(nullable = false)
    private String logradouro;

    private Integer numeroRua;

    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String cep;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao status;

    @PrePersist
    public void prePersist() {
        this.status = StatusSolicitacao.PENDENTE;
    }
}
