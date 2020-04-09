insert into SECURITY_ROLE values(2, 'ADMIN_ROLE');
insert into SECURITY_ROLE values(1, 'USER_ROLE');

INSERT INTO EMPLOYEE values(1,NULL,NULL,'user1.assignment@test.com', 'user1', 'assignment', 123.0,'test user 1', 1, NULL);
INSERT INTO EMPLOYEE values(2,NULL,NULL,'user2.assignment@test.com', 'user2', 'assignment', 123.0,'test user 2', 1, NULL);
INSERT INTO EMPLOYEE values(3,NULL,NULL,'user3.assignment@test.com', 'user3', 'assignment', 123.0,'test user 3', 1, NULL);

INSERT INTO SECURITY_USER VALUES (1, 'PBKDF2WithHmacSHA256:2048:XH1IRCybcpMMdIyPb6S4gupqzW0cFy942EP0hnRklCc=:APlLoh304bD1zRKOYAVgnD9mWvXvCPW9RjzqHtMpeYI=', 'user1', 1);
INSERT INTO SECURITY_USER VALUES (2, 'PBKDF2WithHmacSHA256:2048:xHT5hKIZlvl29wfD7IkEecWPsPMtWEVbuL3J/Kv3enw=:GtwAap6eOV8aXrgr/YolsFNX4FHJG+byn/Ijpe5m7P0=', 'admin', NULL);

INSERT INTO SECURITY_USER_SECURITY_ROLE values(2,2);
INSERT INTO SECURITY_USER_SECURITY_ROLE values(2,2);