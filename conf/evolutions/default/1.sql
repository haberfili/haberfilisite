# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table security_role (
  id                        bigint not null,
  role_name                 varchar(255),
  constraint pk_security_role primary key (id))
;

create table user_permission (
  id                        bigint not null,
  value                     varchar(255),
  constraint pk_user_permission primary key (id))
;

create sequence security_role_seq;

create sequence user_permission_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists security_role;

drop table if exists user_permission;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists security_role_seq;

drop sequence if exists user_permission_seq;

