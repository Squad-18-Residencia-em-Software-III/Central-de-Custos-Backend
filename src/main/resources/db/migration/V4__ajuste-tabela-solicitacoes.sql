alter table solicitacao_cadastro_usuario
    add column status varchar(50) not null;

alter table solicitacao_interna
    add column status varchar(50) not null;