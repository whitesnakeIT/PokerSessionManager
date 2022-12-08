INSERT INTO poker_session_manager.poker_rooms ( id, name, scope, url) VALUES (1, 'PokerStars','global',  'https://www.pokerstars.com/');
INSERT INTO poker_session_manager.poker_rooms ( id, name, scope, url) VALUES (2, 'GGPoker', 'global', 'https://www.ggpoker.com/');
INSERT INTO poker_session_manager.poker_rooms ( id, name, scope, url) VALUES (3, 'PartyPoker','global',  'https://www.partypoker.com/');


INSERT INTO poker_session_manager.roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO poker_session_manager.roles (id, name) VALUES (2, 'ROLE_MODERATOR');
INSERT INTO poker_session_manager.roles (id, name) VALUES (3, 'ROLE_ADMIN');



INSERT INTO poker_session_manager.users (id, birthday_date, created, email, first_name, last_name, password, enabled, username) VALUES (1, '2020-01-01', '2020-01-01 20:33:30', 'admin@gmail.com', 'Tomasz', 'Kapuśniak', '$2a$10$tZ4CMcR07gULGi2OJtzhMer/jZIwT2/FW7loNfHCVfZHaSWSVmy6e', 1,'user1' );
INSERT INTO poker_session_manager.users (id, birthday_date, created, email, first_name, last_name, password, enabled, username) VALUES (2,  '2020-07-01', '2022-11-18 20:35:14', 'user1@gmail.com', 'Paweł', 'Gracz', '$2a$10$tZ4CMcR07gULGi2OJtzhMer/jZIwT2/FW7loNfHCVfZHaSWSVmy6e', 1,  'user2');
INSERT INTO poker_session_manager.users (id, birthday_date, created, email, first_name, last_name, password, enabled, username) VALUES (3,  '2020-11-01', '2022-06-18 20:35:20', 'gamblerx@gmail.com', 'Arek', 'Winner', '$2a$10$tZ4CMcR07gULGi2OJtzhMer/jZIwT2/FW7loNfHCVfZHaSWSVmy6e', 1, 'user3');


INSERT INTO poker_session_manager.user_stats (user_id, average_profit, balance, profit, tournament_count, tournament_wins) VALUES (2, 10, 5000, 1000, 100, 1);
INSERT INTO poker_session_manager.user_stats (user_id, average_profit, balance, profit, tournament_count, tournament_wins) VALUES (3, 200, 10000, 2000, 10, 2);

INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 1, 100, 6, '6-handed Bounty 100$', true, 'turbo',  'progressive-bounty', 1,null);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 2, 55, 9, '9-handed Special 55$', true, 'slow',  'normal', 2,null);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 3, 33.33, 8, '8-handed Freezout 33.33$', false, 'slow',  'normal', 3,null);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 4, 10, 2, 'Heads-up Zoom 10$', true, 'zoom',  'zoom', 1,null);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 5, 21, 8, '8-handed Bounty 21$', true, 'regular',  'progressive-bounty', 2,null);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('global', 6, 10.5, 8, '8-handed Bounty 10.5$', true, 'regular',  'progressive-bounty', 2,null);

INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('local', 7, 100, 4, '4-handed Freezout 100$', false, 'slow',  'normal', 3,2);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('local', 8, 200, 5, 'Heads-up Zoom 200$', true, 'zoom',  'zoom', 1,2);

INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('suggestion', 9, 300, 6, '6-handed Bounty 300$', true, 'regular',  'progressive-bounty', 2,2);
INSERT INTO poker_session_manager.tournaments (tournament_genus, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, user_id) VALUES ('suggestion', 10, 400, 7, '7-handed Bounty 400$', true, 'regular',  'progressive-bounty', 2,2);


INSERT INTO poker_session_manager.user_favourite_tournaments (user_id, tournament_id) VALUES (2, 1);
INSERT INTO poker_session_manager.user_favourite_tournaments (user_id, tournament_id) VALUES (2, 2);
INSERT INTO poker_session_manager.user_favourite_tournaments (user_id, tournament_id) VALUES (2, 3);

INSERT INTO poker_session_manager.user_role (user_id, role_id) VALUES (1, 3);
INSERT INTO poker_session_manager.user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO poker_session_manager.user_role (user_id, role_id) VALUES (3, 1);




INSERT INTO poker_session_manager.sessions (id, name, total_cost, tournament_count, user_id) VALUES (1, 'My first session', 155.0,2,2);
INSERT INTO poker_session_manager.sessions (id, name, total_cost, tournament_count, user_id) VALUES (2, 'My second session', 33.33,1,2);

INSERT INTO poker_session_manager.sessions_tournaments (session_id, tournament_id) VALUES (1, 1);
INSERT INTO poker_session_manager.sessions_tournaments (session_id, tournament_id) VALUES (1, 2);
INSERT INTO poker_session_manager.sessions_tournaments (session_id, tournament_id) VALUES (2, 3);

