create table cargas
(
    id                         serial primary key not null,
    file_name                  varchar,
    size                       int,
    total_transacoes_arquivo   int,
    total_transacoes_sucesso   int,
    total_transacoes_falha     int,
    data_carga                 timestamp,
    data_referencia_transacoes date,
    erro                       varchar,
    status                     boolean
);