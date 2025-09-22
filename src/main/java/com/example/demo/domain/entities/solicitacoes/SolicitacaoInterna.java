package com.example.demo.domain.entities.solicitacoes;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.estrutura.FolhaPagamento;
import com.example.demo.domain.entities.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "solicitacaoInterna")
@Table(name = "solicitacoes_internas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SolicitacaoInterna {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String descricao;

    @Column
    private BigDecimal valor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSolicitacao tipoSolicitacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "estrutura_id", nullable = false)
    private Estrutura estrutura;

    @ManyToOne
    @JoinColumn(name = "folhaPagamento_id")
    private FolhaPagamento folhaPagamento;

    @ManyToOne
    @JoinColumn(name = "combo_id")
    private Combo combo;

    @ManyToOne
    @JoinColumn(name = "item_combo_id")
    private ItemCombo itemCombo;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime criadoEm;

}
