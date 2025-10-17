package com.example.demo.domain.entities.solicitacoes;

import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.combos.ValorItemCombo;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.estrutura.FolhaPagamento;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.enums.TipoSolicitacao;
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
@Table(name = "solicitacao_interna")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SolicitacaoInterna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid = UUID.randomUUID();

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

    @ManyToOne
    @JoinColumn(name = "valor_item_combo_id")
    private ValorItemCombo valorItemCombo;

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
