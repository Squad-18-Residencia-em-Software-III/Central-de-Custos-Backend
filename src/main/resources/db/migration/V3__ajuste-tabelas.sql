alter table solicitacoes_cadastro_usuario
    drop column senha;

-- CEP
alter table estruturas
    alter column cep type varchar(8);

alter table usuarios
    alter column cep type varchar(8);

alter table solicitacoes_cadastro_usuario
    alter column cep type varchar(8);

-- CPF
alter table usuarios
    alter column cpf type varchar(11);

alter table solicitacoes_cadastro_usuario
    alter column cpf type varchar(11);

-- Telefone
alter table estruturas
    alter column telefone type varchar(20);

alter table usuarios
    alter column telefone type varchar(20);

alter table solicitacoes_cadastro_usuario
    alter column telefone type varchar(20);