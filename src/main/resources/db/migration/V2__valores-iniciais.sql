-- ========================
-- PERFIS
-- ========================
INSERT INTO perfis (nome)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM perfis WHERE nome = 'ADMIN');

INSERT INTO perfis (nome)
SELECT 'RESPONSAVEL_SETOR'
WHERE NOT EXISTS (SELECT 1 FROM perfis WHERE nome = 'RESPONSAVEL_SETOR');

INSERT INTO perfis (nome)
SELECT 'RH'
WHERE NOT EXISTS (SELECT 1 FROM perfis WHERE nome = 'RH');

-- ========================
-- UF
-- ========================
INSERT INTO uf (nome, sigla, criado_em)
SELECT 'Sergipe', 'SE', NOW()
WHERE NOT EXISTS (SELECT 1 FROM uf WHERE sigla = 'SE');

-- ========================
-- MUNICÍPIOS
-- ========================
INSERT INTO municipios (nome, uf_id, criado_em)
SELECT 'Aracaju', u.id, NOW()
FROM uf u
WHERE u.sigla = 'SE'
AND NOT EXISTS (
    SELECT 1 FROM municipios m WHERE m.nome = 'Aracaju' AND m.uf_id = u.id
);

-- ========================
-- CLASSIFICAÇÕES
-- ========================
INSERT INTO classificacoes (nome, criado_em)
SELECT 'Secretaria', NOW()
WHERE NOT EXISTS (SELECT 1 FROM classificacoes WHERE nome = 'Secretaria');

-- ========================
-- ESTRUTURA (Setor Raiz)
-- ========================
INSERT INTO estruturas (nome, classificacao_id, telefone, logradouro, numero_rua, bairro, cep, municipio_id, criado_em)
SELECT
    'SEED',
    c.id,
    '(79)3194-3367',
    'Rua Gutemberg Chagas',
    169,
    'Inácio Barbosa',
    49040780,
    m.id,
    NOW()
FROM classificacoes c
JOIN municipios m ON m.nome = 'Aracaju'
WHERE c.nome = 'Secretaria'
AND NOT EXISTS (
    SELECT 1 FROM estruturas e WHERE e.nome = 'SEED'
);
