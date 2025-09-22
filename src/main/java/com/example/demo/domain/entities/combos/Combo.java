package com.example.demo.domain.entities.combos;

import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
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

@Entity(name = "combo")
@Table(name = "combos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "estrutura_id", nullable = false)
    private Estrutura estrutura;

    @ManyToMany
    @JoinTable(
            name = "tabela_combo_itens_combo",
            joinColumns = @JoinColumn(name = "combo_id"),
            inverseJoinColumns = @JoinColumn(name = "item_combo_id")
    )
    private List<ItemCombo> itens = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "competencia_id", nullable = false)
    private Competencia competencia;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @LastModifiedDate
    private LocalDateTime atualizadoEm;


}
