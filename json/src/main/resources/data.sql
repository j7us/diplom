insert into manager (account_non_expired, account_non_locked, credentials_non_expired, enabled, password, username)
VALUES (true, true, true, true, '{bcrypt}$2a$10$mRbur.wbfxmFkq3X/vMO/eZfrOswp1MGI2fzRx63FNdwkXvBn.0ha', 'manager1');


insert into enterprise (id, city, name, production_capacity, zone)
VALUES ('362fb5e1-a4f3-4e7b-b757-f1e6e592144c', 'CIIITY', 'NAME', 123, 'UTC+04:00');

insert into manager_enterprise (enterprise_id, manager_id)
VALUES ('362fb5e1-a4f3-4e7b-b757-f1e6e592144c', 1);