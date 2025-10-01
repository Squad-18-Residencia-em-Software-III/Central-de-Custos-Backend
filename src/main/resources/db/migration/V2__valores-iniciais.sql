-- ========================
-- perfil
-- ========================
INSERT INTO perfil (nome)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM perfil WHERE nome = 'ADMIN');

INSERT INTO perfil (nome)
SELECT 'RESPONSAVEL_SETOR'
WHERE NOT EXISTS (SELECT 1 FROM perfil WHERE nome = 'RESPONSAVEL_SETOR');

INSERT INTO perfil (nome)
SELECT 'RH'
WHERE NOT EXISTS (SELECT 1 FROM perfil WHERE nome = 'RH');

-- ========================
-- UF
-- ========================
INSERT INTO uf (nome, sigla, criado_em)
SELECT 'Sergipe', 'SE', NOW()
WHERE NOT EXISTS (SELECT 1 FROM uf WHERE sigla = 'SE');

-- ========================
-- MUNICÍPIOS
-- ========================
INSERT INTO municipio (nome, uf_id, criado_em)
SELECT 'Aracaju', u.id, NOW()
FROM uf u
WHERE u.sigla = 'SE'
AND NOT EXISTS (
    SELECT 1 FROM municipio m WHERE m.nome = 'Aracaju' AND m.uf_id = u.id
);

-- ========================
-- CLASSIFICAÇÕES
-- ========================
INSERT INTO classificacao (nome, criado_em)
SELECT 'Secretaria', NOW()
WHERE NOT EXISTS (SELECT 1 FROM classificacao WHERE nome = 'Secretaria');

-- ========================
-- ESTRUTURA (Setor Raiz)
-- ========================
INSERT INTO estrutura (nome, classificacao_id, telefone, logradouro, numero_rua, bairro, cep, municipio_id, criado_em)
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
FROM classificacao c
JOIN municipio m ON m.nome = 'Aracaju'
WHERE c.nome = 'Secretaria'
AND NOT EXISTS (
    SELECT 1 FROM estrutura e WHERE e.nome = 'SEED'
);
