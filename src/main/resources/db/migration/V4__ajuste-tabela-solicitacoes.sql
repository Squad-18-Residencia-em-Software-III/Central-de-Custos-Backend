alter table solicitacoes_cadastro_usuario
    add column status varchar(50) not null;

alter table solicitacoes_internas
    add column status varchar(50) not null;