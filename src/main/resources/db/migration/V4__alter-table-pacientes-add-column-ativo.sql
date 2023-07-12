ALTER TABLE pacientes ADD COLUMN ativo TINYINT;

UPDATE pacientes SET ativo = 1;
alter table pacientes modify ativo tinyint not null;
