delete from audit;
delete from banner;
delete from local_info;

insert into local_info(id, country, "language") values
    (1001, 'Russia', 'Russian'),
    (1002, 'Switzerland', 'German'),
    (1003, 'Switzerland', 'French'),
    (1004, 'USA', 'English');

insert into banner(id, height, img_src, target_url, width, lang_id) VALUES
(1001, 100, '/local/usr/main/img1.jpg', 'http://google.com', 400, 1001),
(1002, 200, '/local/usr/main/img2.jpg', 'http://google.com', 300, 1003),
(1003, 300, '/local/usr/main/img3.jpg', 'http://google.com', 200, 1003),
(1004, 400, '/local/usr/main/img4.jpg', 'http://google.com', 100, 1004);

insert into audit(id, comment, "date", type, banner_id, user_id) values
(1001,'new', current_timestamp,0,1001,1001),
(1002,'new', current_timestamp,0,1002,1001),
(1003,'new', current_timestamp,0,1003,1001),
(1004,'new', current_timestamp,0,1004,1001);

alter sequence hibernate_sequence restart with 1010;