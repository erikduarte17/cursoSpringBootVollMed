CREATE TABLE usuarios(

    id bigint NOT NULL auto_increment,
    login VARCHAR(100) NOT NULL,
    senha varchar(255) not null,

    primary key(id)

);