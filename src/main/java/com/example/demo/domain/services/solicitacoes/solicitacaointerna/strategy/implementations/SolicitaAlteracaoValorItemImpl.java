package com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy.implementations;

import com.example.demo.domain.dto.solicitacoes.NovaSolicitacaoInternaDto;
import com.example.demo.domain.entities.combos.Combo;
import com.example.demo.domain.entities.combos.ItemCombo;
import com.example.demo.domain.entities.combos.ValorItemCombo;
import com.example.demo.domain.entities.competencia.Competencia;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoInternaRepository;
import com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy.SolicitacaoInternaStrategy;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.ItemValidator;
import jakarta.transaction.Transactional;

public class SolicitaAlteracaoValorItemImpl extends SolicitacaoInternaStrategy {

    private final ComboValidator comboValidator;
    private final ItemValidator itemValidator;

    protected SolicitaAlteracaoValorItemImpl(SolicitacaoInternaRepository solicitacaoInternaRepository, EstruturaValidator estruturaValidator, ComboValidator comboValidator, ItemValidator itemValidator) {
        super(solicitacaoInternaRepository, estruturaValidator);
        this.comboValidator = comboValidator;
        this.itemValidator = itemValidator;
    }

    @Override
    @Transactional
    public void realiza(NovaSolicitacaoInternaDto dto) {
        Usuario usuario = usuarioSolicitacao();
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(dto.estruturaId());
        estruturaValidator.validaUsuarioPertenceEstrutura(usuario, estrutura);
        Combo combo = comboValidator.validarComboExiste(dto.comboId());
        ItemCombo item = itemValidator.validaItemExiste(dto.itemComboId());
        Competencia competencia = comboValidator.validarCompetenciaExiste(dto.competenciaId());

        SolicitacaoInterna solicitacaoInterna = new SolicitacaoInterna();

        if (dto.valorItemComboId() != null){
            ValorItemCombo valorItemCombo = itemValidator.validaValorExiste(dto.valorItemComboId());
            solicitacaoInterna.setValorItemCombo(valorItemCombo);
        }
        solicitacaoInterna.setEstrutura(estrutura);
        solicitacaoInterna.setCombo(combo);
        solicitacaoInterna.setItemCombo(item);
        solicitacaoInterna.setCompetencia(competencia);
        solicitacaoInterna.setValor(dto.valor());
        solicitacaoInterna.setDescricao(dto.descricao());
        solicitacaoInterna.setTipoSolicitacao(dto.tipoSolicitacao());
        solicitacaoInterna.setUsuario(usuario);

        solicitacaoInternaRepository.save(solicitacaoInterna);
    }

    @Override
    public TipoSolicitacao getTipo() {
        return TipoSolicitacao.ALTERAR_VALOR_ITEM_COMBO;
    }
}
/* Para fazer essa solicitação: Não será possivel escolher a estrutura (o usuario só pode solicitar se for da sua propria), selecionar o Tipo ALTERAR VALOR ITEM COMBO
Ao escolher esse tipo, deverá ter uma busca de valor item combo, com os parametros competencia, combo, e item. Se achar o valor, vai inserir o uuid do valor
e esses outros atributos e o novo valor. Se não achar, só vai inserir esses atributos junto ao novo valor. Na lógica de aceitar essa solicitação, se não
houver esse valor anterior ele vai criar um novo, se houver, só vai alterar.
 */

