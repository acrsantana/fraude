create table receita(
    id serial primary key not null,
    bancoOrigem varchar(50) not null,
    agenciaOrigem varchar(4) not null,
    contaOrigem varchar(7) not null
    bancoDestino varchar(50) not null,
    agenciaDestino varchar(4) not null,
    contaDestino varchar(7) not null,
    valorTransacao DOUBLE PRECISION,
    dataHoraTransacao timestamp not null
);