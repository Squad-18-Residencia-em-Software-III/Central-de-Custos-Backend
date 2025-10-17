package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.solicitacoes.SolicitacaoInterna;
import com.example.demo.domain.entities.usuario.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class SolicitacoesSpecs {

    public static Specification<SolicitacaoInterna> usuarioEqual(Usuario usuario){
        return (root, query, cb) ->
                cb.equal(root.get("usuario"), usuario);
    }

}
