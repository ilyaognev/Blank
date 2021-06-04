insert into USERS (name, surname, login, password) VALUES ('Name', 'SurName', 'user', '$2a$12$DhUY3Ht.enC7h4XlvC8.SepXS6PiHR1k7OLNY0EqGTWkpd5ubEDk6');
insert into USERS (name, surname, login, password) VALUES ('Name2', 'SurName2', 'admin', '$2a$12$DhUY3Ht.enC7h4XlvC8.SepXS6PiHR1k7OLNY0EqGTWkpd5ubEDk6');
insert into USERS (name, surname, login, password) VALUES ('Name3', 'SurName3', 'admin2', '$2a$12$DhUY3Ht.enC7h4XlvC8.SepXS6PiHR1k7OLNY0EqGTWkpd5ubEDk6');

insert into t_role (id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO users_roles (user_id, roles_id) VALUES (1, 1),
                                                   (2, 2),
                                                   (3, 1),
                                                   (3, 2);
