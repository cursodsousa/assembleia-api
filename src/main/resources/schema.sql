CREATE TABLE pauta(
    id int8 not null AUTO_INCREMENT primary key,
    descricao varchar(50) not null,
    abertura timestamp,
    fechamento timestamp check (fechamento > abertura)
);

CREATE TABLE associado(
  id int8 not null AUTO_INCREMENT primary key,
  cpf char(11) not null unique
);

CREATE TABLE voto (
    id int8 not null AUTO_INCREMENT primary key,
    id_associado bigint not null references associado(id),
    id_pauta bigint not null references pauta(id),
    voto char(3) not null check (voto in ('SIM', 'NAO')),
    unique(id_associado, id_pauta)
);