
  drop table if exists computer;
  drop table if exists company;

  create table company (
    id                  BIGINT IDENTITY not null,
    name                 varchar(255),
    constraint pk_company primary key (id))
  ;

  create table computer (
    id                       BIGINT IDENTITY not null,
    name                     varchar(255),
    introduced               date NULL,
    discontinued             date NULL,
    company_id               bigint default NULL,
    constraint pk_computer primary key (id))
  ;

  alter table computer add constraint fk_computer_company_1 
  foreign key (company_id) references company (id) on delete restrict on update restrict;

insert into company (id,name) values (  1,'Apple Inc.');
insert into company (id,name) values (  2,'Thinking Machines');
insert into company (id,name) values (  3,'RCA');
insert into company (id,name) values (  4,'Netronics');
insert into company (id,name) values (  5,'Tandy Corporation');
insert into company (id,name) values (  6,'Commodore International');
insert into company (id,name) values (  7,'MOS Technology');
insert into company (id,name) values (  8,'Micro Instrumentation and Telemetry Systems');
insert into company (id,name) values (  9,'IMS Associates, Inc.');
insert into company (id,name) values ( 10,'Digital Equipment Corporation');

insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (  2,'CM-2a',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  3,'CM-200',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  4,'CM-5e',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  5,'CM-5','1991-01-01',null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (  6,'MacBook Pro','2006-01-10',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (  7,'Apple IIe',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values (  8,'Apple IIc',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values (  9,'Apple IIGS',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 10,'Apple IIc Plus',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 11,'Apple II Plus',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 12,'Apple III','1980-05-01','1984-04-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 13,'Apple Lisa',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 14,'CM-2',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values ( 15,'Connection Machine','1987-01-01',null,2);
insert into computer (id,name,introduced,discontinued,company_id) values ( 16,'Apple II','1977-04-01','1993-10-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 17,'Apple III Plus','1983-12-01','1984-04-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values ( 18,'COSMAC ELF',null,null,3);
insert into computer (id,name,introduced,discontinued,company_id) values ( 19,'COSMAC VIP','1977-01-01',null,3);
insert into computer (id,name,introduced,discontinued,company_id) values ( 20,'ELF II','1977-01-01',null,4);