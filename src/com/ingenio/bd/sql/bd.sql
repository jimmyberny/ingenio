create database ingenio;

use ingenio;

-- Es cada una de las nueve divisiones administrativas, asignadas a un  supervisor
create table zona (
	id varchar(40) not null,
	nombre varchar(20) not null,
	descripcion varchar(160) not null,
	constraint pk_zona primary key(id)
) engine innodb;

-- Son subdivisiones de las zonas, al parecer forman el prefijo de cada productor
create table sector (
	id varchar(40) not null,
	id_zona varchar(40) not null,
	clave varchar(20) not null,
	constraint pk_sector primary key(id),
	constraint fk_s_zona foreign key(id_zona) references zona(id)
) engine innodb;

-- Son los tipos de contrato que se tiene estipulado para con el productor
create table contrato (
	id varchar(40) not null,
	nombre varchar(120) not null,
	constraint pk_contrato primary key(id)
) engine innodb;

-- Son las organizaciones a las que puede pertenecer un productor.
create table organizacion (
	id varchar(40) not null,
	nombre varchar(120) not null,
	constraint pk_organizacion primary key(id)
) engine innodb;

-- Los productores de caña, les falta asociarlos a un sector y a una zona
create table productor (
	id varchar(40) not null,
	clave varchar(20) not null,
	nombre varchar(100) not null,
	paterno varchar(120) not null,
	materno varchar(120) not null,
	id_contrato varchar(40) not null,
	id_organizacion varchar(40) not null,
	id_zona varchar(40) not null,
	id_sector varchar(40) not null,
	constraint pk_productor primary key(id),
	constraint fk_p_c_contrato foreign key(id_contrato) references contrato(id),
	constraint fk_p_o_organizacion foreign key(id_organizacion) references organizacion(id),
	constraint fk_p_z_zona foreign key(id_zona) references zona(id),
	constraint fk_p_s_sector foreign key(id_sector) references sector(id)
) engine innodb;
-- hay que crearle un indice
create unique index idx_producto on productor(clave);

-- Son las labores que pueden realizar los productores sobre su cultivo (o en otras cosas)
create table actividad (
	id varchar(40) not null,
	nombre varchar(100) not null,
	descripcion varchar(255) not null,
	constraint pk_actividad primary key(id)
) engine innodb;

-- El agrupamiento principal de la información, una zafra es un año basicamente
create table zafra (
	id varchar(40) not null,
	nombre varchar(100) not null,
	descripcion varchar(255) not null,
	constraint pk_zafra primary key(id)
) engine innodb;

-- Es un ciclo de la zafra que agrupa tareas: Siembras, Plantas, Socas y resocas
create table ciclo (
	id varchar(40) not null,
	id_zafra varchar(40) not null,
	nombre varchar(120) not null,
	descripcion varchar(255) not null,
	constraint pk_ciclo_zafra primary key(id, id_zafra),
	constraint fk_cz_ciclo foreign key(id) references zafra(id)
) engine innodb;

-- Es la relación de actividades a realizar en un ciclo.
create table actividad_ciclo (
	id varchar(40) not null,
	id_ciclo varchar(40) not null,
	id_actividad varchar(40) not null,
	constraint pk_actividad_ciclo primary key(id),
	constraint fk_ac_ciclo foreign key(id_ciclo) references ciclo(id),
	constraint fk_ac_actividad foreign key(id_actividad) references actividad(id)
) engine innodb;

-- Los usuarios que tienen acceso a la aplicación
create table usuario (
	id varchar(40) not null,
	nombre varchar(120) not null,
	paterno varchar(120) not null,
	materno varchar(120) null,
	usuario varchar(40) not null,
	clave varchar(40) not null,
	constraint pk_usuario primary key(id)
) engine innodb;

-- Son las personas encargadas de una zona
-- por defecto tienen un usuario
create table supervisor (
	id_usuario varchar(40) not null,
	id_zona varchar(40) not null,
	constraint pk_supervisor primary key(id_usuario),
	constraint fk_sup_zona foreign key(id_zona) references zona(id),
	constraint fk_s_u_usuario foreign key(id_usuario) references usuario(id)
) engine innodb;

-- Es el reporte de actividades para determinada ciclo de actividades
-- Como su nombre lo indica sera semanal
create table reporte_semanal (
	id varchar(40) not null,
	id_actividad_ciclo varchar(40) not null,
	id_zona varchar(40) not null,
	id_supervisor varchar(40) not null,
	fecha_reporte datetime not null,
	programa double not null,
	avance double not null,
	fecha datetime not null,
	semana integer not null,
	constraint pk_reporte_semanal primary key(id),
	constraint fk_rs_ac_actividad foreign key(id_actividad_ciclo) references actividad_ciclo(id),
	constraint fk_rs_z_zona foreign key(id_zona) references zona(id),
	constraint fk_rs_s_supervisor foreign key(id_supervisor) references supervisor(id_usuario)
) engine innodb;

create table cana (
	id varchar(40) not null,
	nombre varchar(40) not null,
	constraint pk_cana primary key(id)
) engine innodb;

create table cultivo (
	id varchar(40) not null,
	id_productor varchar(40) not null,
	id_cana varchar(40) not null,
	id_ciclo varchar(40) not null,
	superficie double not null,
	rendimiento double not null,
	constraint pk_cultivo primary key(id),
	constraint fk_c_p_producto foreign key(id_productor) references productor(id),
	constraint fk_c_c_cana foreign key(id_cana) references cana(id),
	constraint fk_c_c_ciclo foreign key(id_ciclo) references ciclo(id)
) engine innodb;

