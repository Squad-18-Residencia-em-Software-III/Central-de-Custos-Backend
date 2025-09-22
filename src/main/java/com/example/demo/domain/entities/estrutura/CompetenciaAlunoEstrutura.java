package com.example.demo.domain.entities.estrutura;

import com.example.demo.domain.entities.competencia.Competencia;
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

@Entity(name = "competenciaAlunoEstrutura")
@Table(name = "competencia_aluno_estrutura")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CompetenciaAlunoEstrutura {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer numeroAlunos;

    @ManyToOne
    @JoinColumn(name = "estrutura_id", nullable = false)
    private Estrutura estrutura;

    @ManyToOne
    @JoinColumn(name = "competencia_id", nullable = false)
    private Competencia competencia;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @LastModifiedDate
    private LocalDateTime atualizadoEm;
}
