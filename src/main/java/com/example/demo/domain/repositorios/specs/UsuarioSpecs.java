package com.example.demo.domain.repositorios.specs;

import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecs {

    public static Specification<Usuario> comNomeContendo(String nome) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }
    public static Specification<Usuario> daEstrutura(Estrutura estrutura) {
        return (root, query, cb) -> cb.equal(root.get("estrutura"), estrutura);
    }

    public static Specification<Usuario> doPrimeiroAcesso(Boolean primeiroAcesso) {
        return (root, query, cb) -> cb.equal(root.get("primeiroAcesso"), primeiroAcesso);
    }

}
