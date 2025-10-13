package com.example.demo.infra.security.config;

import com.example.demo.domain.entities.Municipio;
import com.example.demo.domain.entities.estrutura.Estrutura;
import com.example.demo.domain.entities.usuario.Perfil;
import com.example.demo.domain.entities.usuario.Usuario;
import com.example.demo.domain.repositorios.EstruturaRepository;
import com.example.demo.domain.repositorios.MunicipioRepository;
import com.example.demo.domain.repositorios.PerfilRepository;
import com.example.demo.domain.repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configuration
public class DefaultAdminConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private EstruturaRepository estruturaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${default.admin.password}")
    private String senha;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByCpf("22411451067").isEmpty()) {
            Perfil perfilAdmin = perfilRepository.findByNome("ADMIN")
                    .orElseThrow(() -> new EntityNotFoundException("Perfil ADMIN não encontrada!"));

            Estrutura estrutura = estruturaRepository.findByNome("SEED")
                    .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado!"));

            Municipio municipio = municipioRepository.findByNome("Aracaju")
                    .orElseThrow(() -> new EntityNotFoundException("Municipio não encontrado!"));

            Usuario usuario = new Usuario();
            usuario.setCpf("22411451067");
            usuario.setNome("Admin");
            usuario.setEmail("admin@admin.com");
            usuario.setSenha(passwordEncoder.encode(senha));
            usuario.setPerfil(perfilAdmin);
            usuario.setEstrutura(estrutura);
            usuario.setTelefone("(99)9999-9999");
            usuario.setLogradouro("Rua Teste");
            usuario.setBairro("Teste");
            usuario.setCep("99999999");
            usuario.setCidade("Teste");
            usuario.setEstado("Estado de Teste");
            usuario.setDataNascimento(LocalDate.of(2000, 7, 21));

            usuarioRepository.save(usuario);
            usuario.setPrimeiroAcesso(false);
            usuarioRepository.save(usuario);
            System.out.println("Usuário admin criado com sucesso.");
        } else {
            System.out.println("Usuário admin já existe.");
        }
    }
}
