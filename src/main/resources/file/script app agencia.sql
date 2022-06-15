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

create table agencia(
id_agencia serial  primary key,
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
constraint cliente_agencia FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
constraint categoria_agencia FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);


create table alerta_agencia(
id_alerta serial  primary key,
nombre varchar(64) not null,
fecha_notifica timestamp NOT NULL DEFAULT now(),
id_agencia integer not null,
constraint agencia_alerta foreign key (id_agencia)  references agencia (id_agencia)
);

create table backup_agencia(
id_backup serial  primary key,
fecha_creacion timestamp NOT NULL DEFAULT now(),
id_agencia integer not null,
constraint agencia_backup_agencia foreign key (id_agencia)  references agencia (id_agencia)
);

create table roles(
id_roles serial  primary key,
nombre varchar(64) not null unique
);
create table cliente_roles(
id_cliente Integer not null,
id_roles Integer not null,
primary key(id_cliente,id_roles),
constraint cliente_cliente_roles FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
constraint roles_cliente_roles FOREIGN KEY (id_roles) REFERENCES roles(id_roles)
);