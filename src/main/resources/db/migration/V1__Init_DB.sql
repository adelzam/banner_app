create sequence hibernate_sequence start 5 increment 1;
create table audit (id int4 not null, comment varchar(255), date varchar(255), type int4, banner_id int4, user_id int4, primary key (id));
create table banner (id int4 not null, height int4 not null check (height>=1), img_src varchar(1024), target_url varchar(1024), width int4 not null check (width>=1), lang_id int4, primary key (id));
create table local_info (id int4 not null, country varchar(255), language varchar(255), primary key (id));
create table user_role (user_id int4 not null, roles varchar(255));
create table usr (id int4 not null, active boolean not null, first_name varchar(255), last_name varchar(255), password varchar(255), username varchar(255), primary key (id));
alter table if exists audit add constraint FKq4yh3egt7qbcfj6f0ae8f3kji foreign key (banner_id) references banner;
alter table if exists audit add constraint FKe7iv7l2ra2g5tbindlfiwc80s foreign key (user_id) references usr;
alter table if exists banner add constraint FKe7oahqmg36j081ti26eh5i5gw foreign key (lang_id) references local_info;
alter table if exists user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;