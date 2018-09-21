delete from audit;
delete from banner;
delete from local_info;

insert into local_info(id, country, "language") values
    (1001, 'Russia', 'Russian'),
    (1002, 'Switzerland', 'German'),
    (1003, 'Switzerland', 'French'),
    (1004, 'USA', 'English');

alter sequence hibernate_sequence restart with 1010;