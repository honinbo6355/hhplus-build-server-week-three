insert into test.concert (id, name, created_at, updated_at) values (1, '아이유 콘서트', now(), now());
insert into test.concert (id, name, created_at, updated_at) values (2, '방탄소년단 콘서트', now(), now());

insert into test.concert_detail (id, concert_id, starts_at, max_seat_num, reserved_seat_num, created_at, updated_at) values (1, 1, '2024-02-15 13:00:00', 50, 0, now(), now());
insert into test.concert_detail (id, concert_id, starts_at, max_seat_num, reserved_seat_num, created_at, updated_at) values (2, 1, '2024-02-20 15:00:00', 50, 0, now(), now());
insert into test.concert_detail (id, concert_id, starts_at, max_seat_num, reserved_seat_num, created_at, updated_at) values (3, 2, '2024-03-15 13:00:00', 50, 0, now(), now());
insert into test.concert_detail (id, concert_id, starts_at, max_seat_num, reserved_seat_num, created_at, updated_at) values (4, 2, '2024-03-20 15:00:00', 50, 0, now(), now());

insert into test.seat (id, price, created_at, updated_at) values (1, 10000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (2, 10000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (3, 10000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (4, 10000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (5, 10000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (6, 10000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (7, 10000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (8, 10000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (9, 10000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (10, 12000, now(), now());

insert into test.seat (id, price, created_at, updated_at) values (11, 12000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (12, 12000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (13, 12000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (14, 12000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (15, 12000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (16, 12000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (17, 12000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (18, 12000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (19, 12000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (20, 12000, now(), now());

insert into test.seat (id, price, created_at, updated_at) values (21, 14000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (22, 14000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (23, 14000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (24, 14000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (25, 14000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (26, 14000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (27, 14000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (28, 14000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (29, 14000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (30, 14000, now(), now());

insert into test.seat (id, price, created_at, updated_at) values (31, 16000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (32, 16000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (33, 16000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (34, 16000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (35, 16000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (36, 16000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (37, 16000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (38, 16000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (39, 16000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (40, 16000, now(), now());

insert into test.seat (id, price, created_at, updated_at) values (41, 18000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (42, 18000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (43, 18000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (44, 18000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (45, 18000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (46, 18000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (47, 18000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (48, 18000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (49, 18000, now(), now());
insert into test.seat (id, price, created_at, updated_at) values (50, 18000, now(), now());

insert into test.users (id, name, amount, created_at, updated_at) values (1, '유저1', 0, now(), now());
insert into test.users (id, name, amount, created_at, updated_at) values (2, '유저2', 0, now(), now());
insert into test.users (id, name, amount, created_at, updated_at) values (3, '유저3', 0, now(), now());

insert into test.reservation (concert_detail_id, seat_id, user_id, status, created_at, updated_at) values (1, 4, 1, 'COMPLETED', now(), now());
insert into test.reservation (concert_detail_id, seat_id, user_id, status, created_at, updated_at) values (1, 12, 1, 'COMPLETED', now(), now());
insert into test.reservation (concert_detail_id, seat_id, user_id, status, created_at, updated_at) values (1, 15, 2, 'IN_PROGRESS', now(), now());
insert into test.reservation (concert_detail_id, seat_id, user_id, status, created_at, updated_at) values (1, 20, 2, 'CANCELLED', now(), now());
