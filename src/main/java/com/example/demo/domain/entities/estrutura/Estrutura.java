package com.example.demo.domain.entities.estrutura;

import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.enums.ClassificacaoEstrutura;
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
import java.util.*;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassificacaoEstrutura classificacaoEstrutura;

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
    private String cep;

    @ManyToOne(fetch = FetchType.EAGER) // verificar se isso prejudica
    @JoinColumn(name = "municipio_id", nullable = false)
    private Municipio municipio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estrutura_pai_id")
    private Estrutura estruturaPai;

    @OneToMany(mappedBy = "estruturaPai", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Estrutura> subSetores = new ArrayList<>();

    @ManyToMany(mappedBy = "estruturas", fetch = FetchType.LAZY)
    private List<Combo> combos;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @LastModifiedDate
    private LocalDateTime atualizadoEm;

}
