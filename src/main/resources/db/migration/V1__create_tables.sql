create extension if not exists "pgcrypto";

-- ============================
-- BÁSICAS
-- ============================

create table uf (
    id bigserial primary key,
    nome varchar(100) not null,
    sigla char(2) not null,
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table municipios (
    id bigserial primary key,
    nome varchar(100) not null,
    uf_id bigint not null references uf(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table classificacoes (
    id bigserial primary key,
    nome varchar(100) not null,
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table perfis (
    id bigserial primary key,
    nome varchar(100) not null
);

create table competencias (
    id bigserial primary key,
    referencia date,
    status varchar(50),
    data_abertura date not null,
    data_fechamento date not null
);

-- ============================
-- ESTRUTURA
-- ============================

create table estruturas (
    id uuid primary key default gen_random_uuid(),
    nome varchar(255) not null,
    classificacao_id bigint not null references classificacoes(id),
    telefone varchar(50) not null,
    logradouro varchar(255) not null,
    complemento varchar(255),
    numero_rua integer,
    bairro varchar(255) not null,
    cep integer not null,
    municipio_id bigint not null references municipios(id),
    estrutura_pai_id uuid references estruturas(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

-- ============================
-- USUÁRIOS
-- ============================

create table usuarios (
    id uuid primary key default gen_random_uuid(),
    nome varchar(255) not null,
    email varchar(255) not null,
    telefone varchar(50) not null,
    cpf varchar(20) not null unique,
    senha varchar(255) not null,
    genero varchar(50),
    perfil_id bigint not null references perfis(id),
    estrutura_id uuid not null references estruturas(id),
    logradouro varchar(255) not null,
    numero_rua integer,
    complemento varchar(255),
    bairro varchar(255) not null,
    municipio_id bigint not null references municipios(id),
    cep integer not null,
    primeiro_acesso boolean not null,
    criado_em timestamp not null,
    atualizado_em timestamp
);

-- ============================
-- COMBOS
-- ============================

create table itens_combo (
    id uuid primary key default gen_random_uuid(),
    nome varchar(255) not null,
    estrutura_id uuid references estruturas(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table combos (
    id uuid primary key default gen_random_uuid(),
    nome varchar(255) not null,
    estrutura_id uuid not null references estruturas(id),
    competencia_id bigint not null references competencias(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table tabela_combo_itens_combo (
    combo_id uuid not null references combos(id),
    item_combo_id uuid not null references itens_combo(id),
    primary key (combo_id, item_combo_id)
);

create table valores_itens_combo (
    id uuid primary key default gen_random_uuid(),
    valor numeric(19,2) not null,
    combo_id uuid not null references combos(id),
    item_combo_id uuid not null references itens_combo(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

-- ============================
-- OUTRAS TABELAS
-- ============================

create table folhas_pagamento (
    id uuid primary key default gen_random_uuid(),
    estrutura_id uuid not null references estruturas(id),
    competencia_id bigint not null references competencias(id),
    valor numeric(19,2) not null,
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table solicitacoes_cadastro_usuario (
    id uuid primary key default gen_random_uuid(),
    nome varchar(255) not null,
    email varchar(255) not null,
    telefone varchar(50) not null,
    cpf varchar(20) not null unique,
    senha varchar(255) not null,
    genero varchar(50),
    estrutura_id uuid not null references estruturas(id),
    logradouro varchar(255) not null,
    numero_rua integer,
    complemento varchar(255),
    bairro varchar(255) not null,
    municipio_id bigint not null references municipios(id),
    cep integer not null,
    criado_em timestamp not null
);

create table solicitacoes_internas (
    id uuid primary key default gen_random_uuid(),
    descricao varchar(500) not null,
    valor numeric(19,2),
    tipo_solicitacao varchar(50) not null,
    usuario_id uuid not null references usuarios(id),
    estrutura_id uuid not null references estruturas(id),
    folha_pagamento_id uuid references folhas_pagamento(id),
    combo_id uuid references combos(id),
    item_combo_id uuid references itens_combo(id),
    criado_em timestamp not null
);

create table competencias_aluno_estrutura (
    id uuid primary key default gen_random_uuid(),
    numero_alunos integer not null,
    estrutura_id uuid not null references estruturas(id),
    competencia_id bigint not null references competencias(id),
    criado_em timestamp not null,
    atualizado_em timestamp
);

create table tokens (
    id uuid primary key default gen_random_uuid(),
    usuario_id uuid not null references usuarios(id),
    token varchar(500) not null unique,
    tipo varchar(50) not null,
    expira_em timestamp not null
);

create table logs_sistema (
    id bigserial primary key,
    entidade varchar(255),
    entidade_id uuid,
    acao varchar(50),
    valores_anteriores jsonb,
    valores_novos jsonb,
    usuario_id uuid not null references usuarios(id),
    atualizado_em timestamp not null
);
