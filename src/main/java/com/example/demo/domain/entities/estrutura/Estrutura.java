package com.example.demo.domain.entities.estrutura;

import com.example.demo.domain.entities.Municipio;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "estrutura")
@Table(name = "estrutura")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Estrutura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classificacao_id", nullable = false)
    private Classificacao classificacao;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String logradouro;

    @Column
    private String complemento;

    @Column
    private Integer numeroRua;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private Integer cep;

    @ManyToOne
    @JoinColumn(name = "municipio_id", nullable = false)
    private Municipio municipio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estrutura_pai_id")
    @JsonBackReference // evita loop infinito no JSON, não carrega o setorpai dos filhos na lista
    private Estrutura estruturaPai;

    // Relacionamento para setores filhos (um setor pode ter vários filhos)
    @OneToMany(mappedBy = "estruturaPai", cascade = CascadeType.ALL)
    @JsonManagedReference // evita loop infinito no JSON
    private List<Estrutura> subSetores = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @LastModifiedDate
    private LocalDateTime atualizadoEm;

}
