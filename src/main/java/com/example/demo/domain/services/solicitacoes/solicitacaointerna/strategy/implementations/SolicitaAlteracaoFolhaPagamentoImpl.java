package com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy.implementations;

import com.example.demo.domain.dto.solicitacoes.NovaSolicitacaoInternaDto;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.estrutura.FolhaPagamento;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoInternaRepository;
import com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy.SolicitacaoInternaStrategy;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.domain.validations.FolhaPagamentoValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class SolicitaAlteracaoFolhaPagamentoImpl extends SolicitacaoInternaStrategy {

    private final FolhaPagamentoValidator folhaPagamentoValidator;

    protected SolicitaAlteracaoFolhaPagamentoImpl(SolicitacaoInternaRepository solicitacaoInternaRepository, EstruturaValidator estruturaValidator, FolhaPagamentoValidator folhaPagamentoValidator) {
        super(solicitacaoInternaRepository, estruturaValidator);
        this.folhaPagamentoValidator = folhaPagamentoValidator;
    }

    @Override
    @Transactional
    public void realiza(NovaSolicitacaoInternaDto dto) {
        Usuario usuario = usuarioSolicitacao();
        estruturaValidator.validaUsuarioPertenceEstrutura(usuario,"RH");
        Estrutura estrutura = estruturaValidator.validarEstruturaExiste(dto.estruturaId());

        SolicitacaoInterna solicitacaoInterna = new SolicitacaoInterna();
        if (dto.folhaPagamentoId() != null){
            FolhaPagamento folhaPagamento = folhaPagamentoValidator.validarFolhaPagamentoExiste(dto.folhaPagamentoId());
            solicitacaoInterna.setFolhaPagamento(folhaPagamento);
        }

        solicitacaoInterna.setEstrutura(estrutura);
        solicitacaoInterna.setValor(dto.valor());
        solicitacaoInterna.setDescricao(dto.descricao());
        solicitacaoInterna.setTipoSolicitacao(dto.tipoSolicitacao());
        solicitacaoInterna.setUsuario(usuario);

        solicitacaoInternaRepository.save(solicitacaoInterna);
    }

    @Override
    public TipoSolicitacao getTipo() {
        return TipoSolicitacao.ALTERAR_VALOR_FOLHA_PAGAMENTO;
    }
}

/* Para fazer essa solicitação: Só pode ser solicitada se o usuario for da estrutura RH. Pode pesquisar todas as estruturas.
Ao escolher esse tipo, deverá ter uma busca de folha de pagamento, com os parametros competencia e estrutura. Se achar o valor, vai inserir o uuid do valor
e esses outros atributos e o novo valor. Se não achar, só vai inserir esses atributos junto ao novo valor. Na lógica de aceitar essa solicitação, se não
houver esse valor anterior ele vai criar um novo, se houver, só vai alterar.
 */
