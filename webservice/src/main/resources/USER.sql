 drop table if exists users;
 drop table if exists roles;
 
 create table users (
    pseudo               varchar(255) not null,
    password 			 varchar(255) not null,
    enabled				 boolean,
    constraint pk_users primary key (pseudo))
  ;
  
 create table roles (
    id                  bigint not null auto_increment,
    user_pseudo 	    varchar(255) not null,
    role				varchar(255) not null,
    constraint pk_roles primary key (id))
  ;
  
  alter table roles add constraint fk_roles_users_1 foreign key (user_pseudo) references users (pseudo) on delete restrict on update restrict;
  
  
insert into users (pseudo, password, enabled) values ('Maissa','210195', TRUE);
insert into users (pseudo, password, enabled) values ('test','123456', FALSE);

insert into roles (id, user_pseudo, role) values (1,'Maissa', 'ADMIN');
insert into roles (id, user_pseudo, role) values (2,'test', 'USER');