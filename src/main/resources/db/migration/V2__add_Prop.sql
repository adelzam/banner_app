insert into local_info(id, country, "language") values (1, 'Russia', 'Russian');
insert into local_info(id, country, "language") values (2, 'Switzerland', 'German');
insert into local_info(id, country, "language") values (3, 'Switzerland', 'French');
insert into local_info(id, country, "language") values (4, 'USA', 'English');

insert into usr(id, active, first_name, last_name, password, username) values (1, true, 'Oleg', 'Veshiy', '123', 'oleg23');
insert into usr(id, active, first_name, last_name, password, username) values (2, true, 'Ekaterina', 'Velykaya', '123', 'ekat1');
insert into user_role (user_id, roles) values (1, 'USER');
insert into user_role (user_id, roles) values (2, 'USER');

create extension if not exists pgcrypto;
update usr set password = crypt(password, gen_salt('bf', 8));

insert into banner(id, height, img_src, target_url, width, lang_id) VALUES (1, 100, '/local/usr/main/img1.jpg', 'http://google.com', 400, 1);
insert into banner(id, height, img_src, target_url, width, lang_id) VALUES (2, 200, '/local/usr/main/img2.jpg', 'http://google.com', 300, 3);
insert into banner(id, height, img_src, target_url, width, lang_id) VALUES (3, 300, '/local/usr/main/img3.jpg', 'http://google.com', 200, 3);
insert into banner(id, height, img_src, target_url, width, lang_id) VALUES (4, 400, '/local/usr/main/img4.jpg', 'http://google.com', 100, 4);

insert into audit(id, comment, "date", type, banner_id, user_id) values (1,'new', current_timestamp,0,1,1);
insert into audit(id, comment, "date", type, banner_id, user_id) values (2,'new', current_timestamp,0,2,2);
insert into audit(id, comment, "date", type, banner_id, user_id) values (3,'new', current_timestamp,0,3,1);
insert into audit(id, comment, "date", type, banner_id, user_id) values (4,'new', current_timestamp,0,4,2);
