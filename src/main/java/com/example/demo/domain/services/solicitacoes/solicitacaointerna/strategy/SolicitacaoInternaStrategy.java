package com.example.demo.domain.services.solicitacoes.solicitacaointerna.strategy;

import com.example.demo.domain.dto.solicitacoes.NovaSolicitacaoInternaDto;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.enums.TipoSolicitacao;
import com.example.demo.domain.repositorios.SolicitacaoInternaRepository;
import com.example.demo.domain.validations.ComboValidator;
import com.example.demo.domain.validations.EstruturaValidator;
import com.example.demo.infra.security.authentication.AuthenticatedUserProvider;


public abstract class SolicitacaoInternaStrategy {

    protected final SolicitacaoInternaRepository solicitacaoInternaRepository;
    protected final EstruturaValidator estruturaValidator;
    protected final ComboValidator comboValidator;

    protected SolicitacaoInternaStrategy(SolicitacaoInternaRepository solicitacaoInternaRepository, EstruturaValidator estruturaValidator, ComboValidator comboValidator) {
        this.solicitacaoInternaRepository = solicitacaoInternaRepository;
        this.estruturaValidator = estruturaValidator;
        this.comboValidator = comboValidator;
    }

    public abstract void realiza(NovaSolicitacaoInternaDto dto);

    public abstract TipoSolicitacao getTipo();

    public Usuario usuarioSolicitacao(){
        return AuthenticatedUserProvider.getAuthenticatedUser();
    }

}
