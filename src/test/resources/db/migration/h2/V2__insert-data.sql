
insert into users (id, birthday_date, created, email, enabled, first_name, last_name, password, username, user_type)
values (1,
        '2020-01-01',
        '2023-03-08 22:00:00',
        'testEmail@test.com',
        1,
        'testFirstName',
        'testLastName',
        '$2a$10$9Gbk6JSaledz2aty6Wjmo.eYKDOqb92JTyHP5nUiFlCsJQPX//pBe',
        'testUsername',
        'player');


insert into poker_rooms (id, name, player_id, poker_room_scope, url)
values (1, 'testPokerRoom', 1, 'local', 'https://www.testPokerRoom.com');
insert into poker_rooms (id, name, player_id, poker_room_scope, url)
values (2, 'testPokerRoomGlobal1', null, 'global', 'https://www.testPokerRoomGlobal1.com');
insert into poker_rooms (id, name, player_id, poker_room_scope, url)
values (3, 'testPokerRoomGlobal2', null, 'global', 'https://www.testPokerRoomGlobal2.com');

insert into tournaments(tournament_scope,id, buy_in, handed, name, re_buy, speed, type, player_id, poker_room_id)
values ('local',1,100,4,'100$ 4-handed KO turbo',true,'turbo','proggressive bounty',1,1);
insert into tournaments(tournament_scope,id, buy_in, handed, name, re_buy, speed, type, player_id, poker_room_id)
values ('global',2,200,6,'200$ 6-handed KO',true,'normal','proggressive bounty',null,2);
insert into tournaments(tournament_scope,id, buy_in, handed, name, re_buy, speed, type, player_id, poker_room_id)
values ('global',3,300,8,'300$ 8-handed freezout',false,'slow','normal',null,3);
insert into tournaments(tournament_scope,id, buy_in, handed, name, re_buy, speed, type, player_id, poker_room_id)
values ('suggestion',4,400,3,'400$ 3-handed freezout',false,'turbo','normal',1,null);

insert into roles (id, name)
values (1, 'ROLE_ADMIN');

insert into sessions (id, name, total_cost, tournament_count, player_id)
values (1, 'Test session', 100, 1,1);

insert into sessions_tournaments (session_id, tournament_id)
values (1,1);

insert into sessions_tournaments (session_id, tournament_id)
values (1,2);

insert into user_role (user_id, role_id)
values (1,1);

insert into player_favourite_tournaments (player_id, tournament_id)
values (1,1);

insert into player_favourite_tournaments (player_id, tournament_id)
values (1,2);

insert into player_stats (player_id, average_profit, balance, profit, tournament_count, tournament_wins)
values (1,10,2000,1000,100,1);
