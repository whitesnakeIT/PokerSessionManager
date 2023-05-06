SET FOREIGN_KEY_CHECKS = 0;
drop table if exists users cascade;
drop table if exists tournaments cascade;
drop table if exists roles cascade;
drop table if exists user_role cascade;
drop table if exists player_stats cascade;
drop table if exists player_favourite_tournaments cascade;
drop table if exists poker_rooms cascade;
drop table if exists sessions cascade;
drop table if exists sessions_tournaments cascade;
SET FOREIGN_KEY_CHECKS = 1;