package com.example.demo.domain.validations;

import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class ComboValidator {

    public void validarAcessoBucarCombos(Usuario usuario, Estrutura estrutura) {
        boolean isAdmin = usuario.getPerfil().getNome().equals("ADMIN");
        boolean mesmaEstrutura = usuario.getEstrutura().equals(estrutura);
        boolean subSetor = usuario.getEstrutura().getSubSetores().contains(estrutura);

        if (!(isAdmin || mesmaEstrutura || subSetor)) {
            throw new AccessDeniedException("Busca não permitida");
        }
    }

}
