# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table usuario (
  id                            bigint not null,
  nickname                      varchar(255),
  edad                          varchar(255),
  password_id                   bigint,
  constraint uq_usuario_password_id unique (password_id),
  constraint pk_usuario primary key (id)
);
create sequence usuario_seq;

create table usuario_password (
  id                            bigint not null,
  password_hash                 varchar(255),
  fecha_creacion                timestamp not null,
  constraint pk_usuario_password primary key (id)
);
create sequence usuario_password_seq;

create table usuario_telefonos (
  id                            bigint not null,
  telefono                      varchar(255),
  usuario_id                    bigint,
  constraint pk_usuario_telefonos primary key (id)
);
create sequence usuario_telefonos_seq;

alter table usuario add constraint fk_usuario_password_id foreign key (password_id) references usuario_password (id) on delete restrict on update restrict;

alter table usuario_telefonos add constraint fk_usuario_telefonos_usuario_id foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;
create index ix_usuario_telefonos_usuario_id on usuario_telefonos (usuario_id);


# --- !Downs

alter table usuario drop constraint if exists fk_usuario_password_id;

alter table usuario_telefonos drop constraint if exists fk_usuario_telefonos_usuario_id;
drop index if exists ix_usuario_telefonos_usuario_id;

drop table if exists usuario;
drop sequence if exists usuario_seq;

drop table if exists usuario_password;
drop sequence if exists usuario_password_seq;

drop table if exists usuario_telefonos;
drop sequence if exists usuario_telefonos_seq;

