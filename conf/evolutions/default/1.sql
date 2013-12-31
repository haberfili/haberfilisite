# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table news (
  header                    varchar(255),
  detail                    varchar(255),
  url                       varchar(255))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists news;

SET REFERENTIAL_INTEGRITY TRUE;

