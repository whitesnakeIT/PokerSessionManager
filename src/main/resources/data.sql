INSERT INTO poker_session_manager.poker_rooms (id, name, url, scope) VALUES (1, 'PokerStars', 'https://www.pokerstars.com/','global');
INSERT INTO poker_session_manager.poker_rooms (id, name, url, scope) VALUES (2, 'GGPoker', 'https://www.ggpoker.com/','global');
INSERT INTO poker_session_manager.poker_rooms (id, name, url, scope) VALUES (3, 'PartyPoker', 'https://www.partypoker.com/','global');


INSERT INTO poker_session_manager.roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO poker_session_manager.roles (id, name) VALUES (2, 'ROLE_MODERATOR');
INSERT INTO poker_session_manager.roles (id, name) VALUES (3, 'ROLE_ADMIN');


INSERT INTO poker_session_manager.user_stats (id, average_profit, profit, tournament_count, tournament_wins) VALUES (1, 10, 1000, 100, 1);
INSERT INTO poker_session_manager.user_stats (id, average_profit, profit, tournament_count, tournament_wins) VALUES (2, 200, 2000, 10, 2);


INSERT INTO poker_session_manager.users (id, balance, birthday_date, created, email, first_name, last_name, password, super_admin, enabled, username, user_stats_id) VALUES (1, 0, '2020-01-01', '2020-01-01 20:33:30', 'admin@gmail.com', 'Tomasz', 'Kapuśniak', 'root', true, 1, 'user1', null);
INSERT INTO poker_session_manager.users (id, balance, birthday_date, created, email, first_name, last_name, password, super_admin, enabled, username, user_stats_id) VALUES (2, 1000, '2020-07-01', '2022-11-18 20:35:14', 'user1@gmail.com', 'Paweł', 'Gracz', 'user', false, 1, 'user2', null);
INSERT INTO poker_session_manager.users (id, balance, birthday_date, created, email, first_name, last_name, password, super_admin, enabled, username, user_stats_id) VALUES (3, 2000, '2020-11-01', '2022-06-18 20:35:20', 'gamblerx@gmail.com', 'Arek', 'Winner', 'user2', false, 1, 'user3', null);
INSERT INTO poker_session_manager.users (id, balance, birthday_date, created, email, first_name, last_name, password, super_admin, enabled, username, user_stats_id) VALUES (4, 0, '2000-11-18', '2022-11-23 12:31:43.371085', 'ddddqqqq@qqqq.qq', 'qqqqqqq', 'qqqqqqqqqqqq', 'dddd', false, 1, 'user13', null);
INSERT INTO poker_session_manager.users (id, balance, birthday_date, created, email, first_name, last_name, password, super_admin, enabled, username, user_stats_id) VALUES (5, 0, '2000-01-01', '2022-11-24 20:09:19.505870', 'a@a.pl', 'admin1', 'admin2', '$2a$10$4EiruMc/nS6rIP6ZTV2Rwus.u.oqPmIOGqz5wGT7nmCgx8OiQ7eFi', false, 1, 'admin14', null);
INSERT INTO poker_session_manager.users (id, balance, birthday_date, created, email, first_name, last_name, password, super_admin, enabled, username, user_stats_id) VALUES (6, 0, '2000-01-01', '2022-11-25 00:20:05.742558', 'user@user.pl1', 'user11xx2xxxx', 'user2xx2', '$2a$10$5Lt8.Q1dqXumnTIPsf3/5u575KC/pE7jxr02ReFoM/D8LXMUb6MNa', false, 1, 'admin16', null);
INSERT INTO poker_session_manager.users (id, balance, birthday_date, created, email, first_name, last_name, password, super_admin, enabled, username, user_stats_id) VALUES (7, 0, '2000-01-01', '2022-11-25 19:24:42.474979', 'user@user.pl', 'user1vvvvq1w', 'ssuser2vvvss', '$2a$10$tZ4CMcR07gULGi2OJtzhMer/jZIwT2/FW7loNfHCVfZHaSWSVmy6e', false, 1, 'admin', 1);
INSERT INTO poker_session_manager.users (id, balance, birthday_date, created, email, first_name, last_name, password, super_admin, enabled, username, user_stats_id) VALUES (8, 0, '2000-01-01', '2022-11-25 22:00:14.549140', 'qq@qq.qq', 'qq', 'qqqq', '$2a$10$VBhCQQ0TczBIdY/N1wxnMu2T2o5deFodT5c5jKzzqa64WdsdjNwzK', false, 1, 'qqqqq', 2);


INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 1, 100, 6, '6-handed Bounty 100$', true, 'turbo',  'progressive-bounty', 1,null);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 2, 55, 9, '9-handed Special 55$', true, 'slow',  'normal', 2,null);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 3, 33.33, 8, '8-handed Freezout 33.33$', false, 'slow',  'normal', 3,null);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 4, 10, 2, 'Heads-up Zoom 10$', true, 'zoom',  'zoom', 1,null);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 5, 21, 8, '8-handed Bounty 21$', true, 'regular',  'progressive-bounty', 2,null);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 6, 10.5, 8, '8-handed Bounty 10.5$', true, 'regular',  'progressive-bounty', 2,null);

INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('local', 7, 100, 4, '4-handed Freezout 100$', false, 'slow',  'normal', 3,7);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('local', 8, 200, 5, 'Heads-up Zoom 200$', true, 'zoom',  'zoom', 1,7);

INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('suggestion', 9, 300, 6, '6-handed Bounty 300$', true, 'regular',  'progressive-bounty', 2,7);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('suggestion', 10, 400, 7, '7-handed Bounty 400$', true, 'regular',  'progressive-bounty', 2,7);

INSERT INTO poker_session_manager.user_favourite_tournaments (user_id, tournament_id) VALUES (3, 2);
INSERT INTO poker_session_manager.user_favourite_tournaments (user_id, tournament_id) VALUES (3, 4);
INSERT INTO poker_session_manager.user_favourite_tournaments (user_id, tournament_id) VALUES (3, 6);
INSERT INTO poker_session_manager.user_favourite_tournaments (user_id, tournament_id) VALUES (3, 6);
INSERT INTO poker_session_manager.user_favourite_tournaments (user_id, tournament_id) VALUES (7, 1);
INSERT INTO poker_session_manager.user_favourite_tournaments (user_id, tournament_id) VALUES (7, 2);
INSERT INTO poker_session_manager.user_favourite_tournaments (user_id, tournament_id) VALUES (7, 3);

INSERT INTO poker_session_manager.user_role (user_id, role_id) VALUES (1, 3);
INSERT INTO poker_session_manager.user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO poker_session_manager.user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO poker_session_manager.user_role (user_id, role_id) VALUES (4, 1);
INSERT INTO poker_session_manager.user_role (user_id, role_id) VALUES (6, 1);
INSERT INTO poker_session_manager.user_role (user_id, role_id) VALUES (7, 1);
INSERT INTO poker_session_manager.user_role (user_id, role_id) VALUES (8, 1);




INSERT INTO poker_session_manager.sessions (id, name, total_cost, tournament_count, user_id) VALUES (1, 'My first session', 155.0,2,7);
INSERT INTO poker_session_manager.sessions (id, name, total_cost, tournament_count, user_id) VALUES (2, 'My second session', 33.33,1,7);

INSERT INTO poker_session_manager.sessions_tournaments (session_id, tournament_id) VALUES (1, 1);
INSERT INTO poker_session_manager.sessions_tournaments (session_id, tournament_id) VALUES (1, 2);
INSERT INTO poker_session_manager.sessions_tournaments (session_id, tournament_id) VALUES (2, 3);

