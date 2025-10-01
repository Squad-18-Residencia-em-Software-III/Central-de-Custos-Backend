alter table solicitacao_cadastro_usuario
    drop column senha;

-- CEP
alter table estrutura
    alter column cep type varchar(8);

alter table usuario
    alter column cep type varchar(8);

alter table solicitacao_cadastro_usuario
    alter column cep type varchar(8);

-- CPF
alter table usuario
    alter column cpf type varchar(11);

alter table solicitacao_cadastro_usuario
    alter column cpf type varchar(11);

-- Telefone
alter table estrutura
    alter column telefone type varchar(20);

alter table usuario
    alter column telefone type varchar(20);

alter table solicitacao_cadastro_usuario
    alter column telefone type varchar(20);