# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  constraint pk_category primary key (id)
);

create table password (
  id                            bigint auto_increment not null,
  password_hash                 varchar(255),
  up_date                       datetime(6) not null,
  constraint pk_password primary key (id)
);

create table product (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  amount                        integer,
  price                         double,
  user_id                       bigint,
  constraint pk_product primary key (id)
);

create table product_category (
  product_id                    bigint not null,
  category_id                   bigint not null,
  constraint pk_product_category primary key (product_id,category_id)
);

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  email                         varchar(255),
  password_id                   bigint,
  constraint uq_user_password_id unique (password_id),
  constraint pk_user primary key (id)
);

alter table product add constraint fk_product_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_product_user_id on product (user_id);

alter table product_category add constraint fk_product_category_product foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_product_category_product on product_category (product_id);

alter table product_category add constraint fk_product_category_category foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_product_category_category on product_category (category_id);

alter table user add constraint fk_user_password_id foreign key (password_id) references password (id) on delete restrict on update restrict;


# --- !Downs

alter table product drop foreign key fk_product_user_id;
drop index ix_product_user_id on product;

alter table product_category drop foreign key fk_product_category_product;
drop index ix_product_category_product on product_category;

alter table product_category drop foreign key fk_product_category_category;
drop index ix_product_category_category on product_category;

alter table user drop foreign key fk_user_password_id;

drop table if exists category;

drop table if exists password;

drop table if exists product;

drop table if exists product_category;

drop table if exists user;

