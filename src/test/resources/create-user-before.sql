delete
from user_role;
delete
from users;

insert into users(id, activation_code, active, date_of_created, email, name, password, phone_number, avatar_id)
values (3, null, true, NOW(), 'elena@elena.com', 'Elena',
        '$2a$08$HMP4tRpGnXwDjIlEfPTz4.GOXNyW7pGzSYwTrjl6B9ASnHWP7Eguu', '+4912345678903', null),
       (2, null, true, NOW(), 'petja@petja.com', 'Petja',
        '$2a$08$HMP4tRpGnXwDjIlEfPTz5.GOXNyW7pGzSYwTrjl6B9ASnHWP7Eguu', '+4912345678900', null);

insert into user_role(user_id, roles)
values (3, 'ROLE_USER'),
       (3, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');


