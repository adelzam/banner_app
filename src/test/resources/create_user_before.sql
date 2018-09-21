delete from audit;
delete from user_role;
delete from usr;

insert into usr(id, active, first_name, last_name, password, username)
values (1001, true, 'Test', 'User', '$2a$08$NvsRFu.O0EST0q61Uhwo0.zwn14bDT4n9f5ODUjFKZ3hMbsaSM2Um', 'test');
insert into user_role (user_id, roles)
    values (1001, 'USER');