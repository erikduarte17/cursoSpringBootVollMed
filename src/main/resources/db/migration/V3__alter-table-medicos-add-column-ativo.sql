ALTER TABLE medicos ADD COLUMN ativo TINYINT;

UPDATE medicos SET ativo = 1;
alter table medicos modify ativo tinyint not null;
