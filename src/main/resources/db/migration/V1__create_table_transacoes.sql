create table transacoes
(
    id                  serial primary key not null,
    banco_origem        varchar(50)        not null,
    agencia_origem      varchar(4)         not null,
    conta_origem        varchar(7)         not null,
    banco_destino       varchar(50)        not null,
    agencia_destino     varchar(4)         not null,
    conta_destino       varchar(7)         not null,
    valor_transacao     DOUBLE PRECISION   not null,
    data_hora_transacao timestamp          not null
);