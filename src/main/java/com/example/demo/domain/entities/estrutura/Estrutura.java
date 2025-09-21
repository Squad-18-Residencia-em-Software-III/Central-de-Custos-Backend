package com.example.demo.domain.entities.estrutura;

import com.example.demo.domain.entities.Municipio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "estrutura")
@Table(name = "estruturas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Estrutura {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @LastModifiedDate
    private LocalDateTime atualizadoEm;

}
