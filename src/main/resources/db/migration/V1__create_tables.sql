create extension if not exists "pgcrypto";

-- ============================
-- BÁSICAS
-- ============================

create table uf (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    nome varchar(100) not null,
    sigla char(2) not null,
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table municipio (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    nome varchar(100) not null,
    uf_id bigint not null references uf(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table classificacao (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    nome varchar(100) not null,
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table perfil (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    nome varchar(100) not null
);

create table competencia (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    referencia date,
    status varchar(50),
    data_abertura date not null,
    data_fechamento date not null
);

-- ============================
-- ESTRUTURA
-- ============================

create table estrutura (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    nome varchar(255) not null,
    classificacao_id bigint not null references classificacao(id),
    telefone varchar(50) not null,
    logradouro varchar(255) not null,
    complemento varchar(255),
    numero_rua integer,
    bairro varchar(255) not null,
    cep integer not null,
    municipio_id bigint not null references municipio(id),
    estrutura_pai_id bigint references estrutura(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

-- ============================
-- USUÁRIOS
-- ============================

create table usuario (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    nome varchar(255) not null,
    email varchar(255) not null,
    telefone varchar(50) not null,
    cpf varchar(20) not null unique,
    senha varchar(255) not null,
    genero varchar(50),
    perfil_id bigint not null references perfil(id),
    estrutura_id bigint not null references estrutura(id),
    logradouro varchar(255) not null,
    numero_rua integer,
    complemento varchar(255),
    bairro varchar(255) not null,
    municipio_id bigint not null references municipio(id),
    cep integer not null,
    primeiro_acesso boolean not null,
    criado_em timestamp not null,
    atualizado_em timestamp
);

-- ============================
-- COMBOS
-- ============================

create table item_combo (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    nome varchar(255) not null,
    estrutura_id bigint references estrutura(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table combo (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    nome varchar(255) not null,
    estrutura_id bigint not null references estrutura(id),
    competencia_id bigint not null references competencia(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table combo_item_combo (
    combo_id bigint not null references combo(id),
    item_combo_id bigint not null references item_combo(id),
    primary key (combo_id, item_combo_id)
);

create table valor_item_combo (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    valor numeric(19,2) not null,
    combo_id bigint not null references combo(id),
    item_combo_id bigint not null references item_combo(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

-- ============================
-- OUTRAS TABELAS
-- ============================

create table folha_pagamento (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    estrutura_id bigint not null references estrutura(id),
    competencia_id bigint not null references competencia(id),
    valor numeric(19,2) not null,
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table solicitacao_cadastro_usuario (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    nome varchar(255) not null,
    email varchar(255) not null,
    telefone varchar(50) not null,
    cpf varchar(20) not null unique,
    senha varchar(255) not null,
    genero varchar(50),
    estrutura_id bigint not null references estrutura(id),
    logradouro varchar(255) not null,
    numero_rua integer,
    complemento varchar(255),
    bairro varchar(255) not null,
    municipio_id bigint not null references municipio(id),
    cep integer not null,
    criado_em timestamp not null
);

create table solicitacao_interna (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    descricao varchar(500) not null,
    valor numeric(19,2),
    tipo_solicitacao varchar(50) not null,
    usuario_id bigint not null references usuario(id),
    estrutura_id bigint not null references estrutura(id),
    folha_pagamento_id bigint references folha_pagamento(id),
    combo_id bigint references combo(id),
    item_combo_id bigint references item_combo(id),
    criado_em timestamp not null
);

create table competencia_aluno_estrutura (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    numero_alunos integer not null,
    estrutura_id bigint not null references estrutura(id),
    competencia_id bigint not null references competencia(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table token (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    usuario_id bigint not null references usuario(id),
    token varchar(500) not null unique,
    tipo varchar(50) not null,
    expira_em timestamp not null
);

create table log_sistema (
    id bigserial primary key,
    uuid uuid not null unique default gen_random_uuid(),
    entidade varchar(255),
    entidade_id bigint,
    acao varchar(50),
    valores_anteriores jsonb,
    valores_novos jsonb,
    usuario_id bigint not null references usuario(id),
    atualizado_em timestamp not null
);
