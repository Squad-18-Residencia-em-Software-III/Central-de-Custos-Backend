alter table competencia
    drop column referencia;

alter table competencia
    drop column status;

INSERT INTO competencia (data_abertura, data_fechamento)
SELECT
    date_trunc('month', d)::date as data_abertura,
    (date_trunc('month', d) + interval '1 month - 1 day')::date as data_fechamento
FROM generate_series('2025-01-01'::date, '2025-12-01'::date, interval '1 month') d;
