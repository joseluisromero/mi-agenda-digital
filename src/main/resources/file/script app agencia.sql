create table categoria
(
id_categoria serial  primary key,
nombre varchar (255) not null
);

create table cliente
(
id_cliente serial  primary key ,
identificacion varchar (20) not null unique,
username varchar(64) not null,
password varchar (255) not null,
nombres varchar(64) not null,
apellidos varchar(64) not null,
email varchar(64),
estado integer default 1
);

create table agenda(
id_agenda serial  primary key,
titulo varchar(64) not null unique,
descripcion varchar(255),
fecha_creacion timestamp NOT NULL DEFAULT now(),
fecha_modificacion timestamp,
fecha_vencimiento date,
notifica boolean not null default false,
dias_notifica integer default 0,
estado int default 1,
sincronizado boolean default false,
id_cliente integer not null,
id_categoria integer not null,
constraint cliente_agenda FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
constraint categoria_agenda FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);
ALTER TABLE agenda ADD CONSTRAINT resctriccion_unique  UNIQUE(titulo,id_cliente);


create table alerta_agenda(
id_alerta serial  primary key,
nombre varchar(64) not null,
fecha_notifica timestamp NOT NULL DEFAULT now(),
id_agenda integer not null,
constraint agenda_alerta foreign key (id_agenda)  references agenda (id_agenda)
);

create table backup_agenda(
id_backup serial  primary key,
fecha_creacion timestamp NOT NULL DEFAULT now(),
id_agenda integer not null,
constraint agenda_backup_agenda foreign key (id_agenda)  references agenda (id_agenda)
);

create table roles(
id_roles serial  primary key,
nombre varchar(64) not null unique--,
--id_cliente integer not null,
--constraint cliente_roles foreign key (id_cliente)  references cliente (id_cliente)
);

create table cliente_roles(
id_cliente Integer not null,
id_roles Integer not null,
primary key(id_cliente,id_roles),
constraint cliente_cliente_roles FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
constraint roles_cliente_roles FOREIGN KEY (id_roles) REFERENCES roles(id_roles)
);