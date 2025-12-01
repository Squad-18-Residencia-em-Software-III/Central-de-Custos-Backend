package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoCadastroUsuario;
import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.StatusSolicitacao;
import com.example.demo.domain.enums.TipoSolicitacao;
import org.springframework.data.jpa.domain.Specification;

public class SolicitacoesSpecs {

    public static Specification<SolicitacaoInterna> usuarioEqual(Usuario usuario){
        return (root, query, cb) ->
                cb.equal(root.get("usuario"), usuario);
    }

    public static Specification<SolicitacaoInterna> doStatus(StatusSolicitacao statusSolicitacao) {
        return (root, query, cb) -> cb.equal(root.get("status"), statusSolicitacao);
    }

    public static Specification<SolicitacaoInterna> doTipo(TipoSolicitacao tipoSolicitacao) {
        return (root, query, cb) -> cb.equal(root.get("tipoSolicitacao"), tipoSolicitacao);
    }

    public static Specification<SolicitacaoCadastroUsuario> doStatusCadastro(StatusSolicitacao statusSolicitacao) {
        return (root, query, cb) -> cb.equal(root.get("status"), statusSolicitacao);
    }

    public static Specification<SolicitacaoCadastroUsuario> comNomeContendo(String nome) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

}
