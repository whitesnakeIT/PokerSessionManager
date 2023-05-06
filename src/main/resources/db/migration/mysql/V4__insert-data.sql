
INSERT INTO poker_rooms ( id, name, poker_room_scope, url) VALUES (1, 'PokerStars','global',  'https://www.pokerstars.com/');
INSERT INTO poker_rooms ( id, name, poker_room_scope, url) VALUES (2, 'GGPoker', 'global', 'https://www.ggpoker.com/');
INSERT INTO poker_rooms ( id, name, poker_room_scope, url) VALUES (3, 'PartyPoker','global',  'https://www.partypoker.com/');


INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_MODERATOR');
INSERT INTO roles (id, name) VALUES (3, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (4, 'ROLE_ANONYMOUS');



INSERT INTO users (user_type, id, birthday_date, created, email, first_name, last_name, password, enabled, username) VALUES ('admin', 1, '2000-01-01', '2020-01-01 20:33:30', 'admin@gmail.com', 'Tomasz', 'Kapuśniak', '$2a$10$tZ4CMcR07gULGi2OJtzhMer/jZIwT2/FW7loNfHCVfZHaSWSVmy6e', 1,'user1' );
INSERT INTO users (user_type, id, birthday_date, created, email, first_name, last_name, password, enabled, username) VALUES ('player', 2,  '2000-07-01', '2022-11-18 20:35:14', 'user1@gmail.com', 'Paweł', 'Gracz', '$2a$10$tZ4CMcR07gULGi2OJtzhMer/jZIwT2/FW7loNfHCVfZHaSWSVmy6e', 1,  'user2');
INSERT INTO users (user_type, id, birthday_date, created, email, first_name, last_name, password, enabled, username) VALUES ('player', 3,  '2000-11-01', '2022-06-18 20:35:20', 'gamblerx@gmail.com', 'Arek', 'Winner', '$2a$10$tZ4CMcR07gULGi2OJtzhMer/jZIwT2/FW7loNfHCVfZHaSWSVmy6e', 1, 'user3');


INSERT INTO player_stats (player_id, average_profit, balance, profit, tournament_count, tournament_wins) VALUES (2, 10, 5000, 1000, 100, 1);
INSERT INTO player_stats (player_id, average_profit, balance, profit, tournament_count, tournament_wins) VALUES (3, 200, 10000, 2000, 10, 2);

INSERT INTO tournaments (id, buy_in, handed, name, re_buy, speed, tournament_scope, type, poker_room_id, player_id) VALUES ( 1, 100, 6, '6-handed Bounty 100$', true, 'turbo' ,'global',  'progressive-bounty',1,null);
INSERT INTO tournaments (id, buy_in, handed, name, re_buy, speed, tournament_scope, type, poker_room_id, player_id) VALUES ( 2, 55, 9, '9-handed Special 55$', true, 'slow', 'global', 'normal', 2,null);
INSERT INTO tournaments (id, buy_in, handed, name, re_buy, speed, tournament_scope, type, poker_room_id, player_id) VALUES ( 3, 33.33, 8, '8-handed Freezout 33.33$', false, 'slow','global',  'normal', 3,null);
INSERT INTO tournaments (id, buy_in, handed, name, re_buy, speed, tournament_scope, type, poker_room_id, player_id) VALUES ( 4, 10, 2, 'Heads-up Zoom 10$', true, 'zoom',  'global','zoom', 1,null);
INSERT INTO tournaments (id, buy_in, handed, name, re_buy, speed, tournament_scope, type, poker_room_id, player_id) VALUES ( 5, 21, 8, '8-handed Bounty 21$', true, 'regular', 'global', 'progressive-bounty', 2,null);
INSERT INTO tournaments (id, buy_in, handed, name, re_buy, speed, tournament_scope, type, poker_room_id, player_id) VALUES ( 6, 10.5, 8, '8-handed Bounty 10.5$', true, 'regular','global',  'progressive-bounty', 2,null);

INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('local', 7, 100, 4, '4-handed Freezout 100$', false, 'slow',  'normal', 3,2);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('local', 8, 200, 5, 'Heads-up Zoom 200$', true, 'zoom',  'zoom', 1,2);

INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('suggestion', 9, 300, 6, '6-handed Bounty 300$', true, 'regular',  'progressive-bounty', 2,2);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('suggestion', 10, 400, 7, '7-handed Bounty 400$', true, 'regular',  'progressive-bounty', 2,2);


INSERT INTO player_favourite_tournaments (player_id, tournament_id) VALUES (2, 1);
INSERT INTO player_favourite_tournaments (player_id, tournament_id) VALUES (2, 2);
INSERT INTO player_favourite_tournaments (player_id, tournament_id) VALUES (2, 3);

INSERT INTO user_role (user_id, role_id) VALUES (1, 3);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (3, 1);




INSERT INTO sessions (id, name, total_cost, tournament_count, player_id) VALUES (1, 'My first session', 155.0,2,2);
INSERT INTO sessions (id, name, total_cost, tournament_count, player_id) VALUES (2, 'My second session', 33.33,1,2);

INSERT INTO sessions_tournaments (session_id, tournament_id) VALUES (1, 1);
INSERT INTO sessions_tournaments (session_id, tournament_id) VALUES (1, 2);
INSERT INTO sessions_tournaments (session_id, tournament_id) VALUES (2, 3);

INSERT INTO users (user_type, birthday_date, created, email, enabled, first_name, last_name, password, username) VALUES ('admin', '2000-01-01', '2023-03-02 20:15:25.000000', 'aa@aa.pl', 1, 'admin', 'istrator', '$2a$10$tZ4CMcR07gULGi2OJtzhMer/jZIwT2/FW7loNfHCVfZHaSWSVmy6e', 'adminek');
INSERT INTO users (user_type, birthday_date, created, email, enabled, first_name, last_name, password, username) VALUES ('player', '2000-01-01', '2023-03-02 20:15:25.000000', '12345@12345.pl', 1, 'play', 'er', '$2a$10$tZ4CMcR07gULGi2OJtzhMer/jZIwT2/FW7loNfHCVfZHaSWSVmy6e', 'playerek');

INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('local', 11, 50, 8, '50 $ KO Saturday Special', false, 'turbo', 'progressive knockout', 1, 2);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('global', 12, 1, 3, 'global', false, 'turbo', 'zoom', 1, null);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('global', 13, 1, 3, 'global ggg', false, 'turbo', 'zoom', 1, null);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('global', 14, 1, 3, 'global global', false, 'turbo', 'zoom', 1, null);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('suggestion', 15, 1, 3, 'suggestion my', false, 'turbo', 'zoom', 1, 5);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('suggestion', 16, 1, 3, 'suggestion not my', false, 'turbo', 'zoom', 1, 2);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('suggestion', 17, 1, 3, 'suggestion global', false, 'turbo', 'zoom', 1, 2);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('local', 18, 1, 3, 'local my', false, 'turbo', 'zoom', 1, 5);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('local', 19, 1, 3, 'local not my', false, 'turbo', 'zoom', 1, 2);
INSERT INTO tournaments (tournament_scope, id, buy_in, handed, name, re_buy, speed, type, poker_room_id, player_id) VALUES ('local', 20, 1, 3, 'local global', false, 'turbo', 'zoom', 1,  2);

INSERT INTO user_role (user_id, role_id) VALUES (4, 3);
INSERT INTO user_role (user_id, role_id) VALUES (5, 1);