use poker_session_manager;

SET FOREIGN_KEY_CHECKS = 0;
drop table if exists poker_session_manager.users cascade;
drop table if exists poker_session_manager.tournaments cascade;
drop table if exists poker_session_manager.roles cascade;
drop table if exists poker_session_manager.user_role cascade;
drop table if exists poker_session_manager.player_stats cascade;
drop table if exists poker_session_manager.player_favourite_tournaments cascade;
drop table if exists poker_session_manager.poker_rooms cascade;
drop table if exists poker_session_manager.sessions cascade;
drop table if exists poker_session_manager.sessions_tournaments cascade;
SET FOREIGN_KEY_CHECKS = 1;

create table poker_session_manager.roles
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table poker_session_manager.users
(
    user_type     varchar(31)  not null,
    id            bigint auto_increment
        primary key,
    birthday_date date         not null,
    created       datetime(6)  null,
    email         varchar(255) not null,
    enabled       int          not null,
    first_name    varchar(255) null,
    last_name     varchar(255) null,
    password      varchar(255) not null,
    username      varchar(15)  not null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UK_r43af9ap4edm43mmtq01oddj6
        unique (username)
);

create table poker_session_manager.player_stats
(
    player_id        bigint not null
        primary key,
    average_profit   double not null,
    balance          double not null,
    profit           double not null,
    tournament_count int    not null,
    tournament_wins  int    not null,
    constraint FKk3toom4yb5inrke0fyxxshbvy
        foreign key (player_id) references poker_session_manager.users (id)
            on delete cascade
);

create table poker_session_manager.poker_rooms
(
    id               bigint auto_increment
        primary key,
    name             varchar(255) null,
    poker_room_scope varchar(255) null,
    url              varchar(255) null,
    player_id        bigint       null,
    constraint FK6huu2q810jfhmvmxyhy8r2t63
        foreign key (player_id) references poker_session_manager.users (id)
            on delete cascade
);

create table poker_session_manager.sessions
(
    id               bigint auto_increment
        primary key,
    name             varchar(255) null,
    total_cost       double       not null,
    tournament_count int          null,
    player_id        bigint       null,
    constraint FKt7q6ursqju6c296a2j5dnttrr
        foreign key (player_id) references poker_session_manager.users (id)
            on delete cascade
);

create table poker_session_manager.tournaments
(
    tournament_scope varchar(31)  not null,
    id               bigint auto_increment
        primary key,
    buy_in           double       not null,
    handed           int          not null,
    name             varchar(255) not null,
    re_buy           bit          not null,
    speed            varchar(255) not null,
    type             varchar(255) not null,
    player_id        bigint       null,
    poker_room_id    bigint       null,
    constraint FKf8sf5nrihy1k239unqovhk6rp
        foreign key (player_id) references poker_session_manager.users (id)
            on delete cascade,
    constraint FKkdai9sjqg3xrcvhkvlf4p1daw
        foreign key (poker_room_id) references poker_session_manager.poker_rooms (id)
            on delete cascade

);

create table poker_session_manager.player_favourite_tournaments
(
    player_id     bigint not null,
    tournament_id bigint not null,
    constraint FKj7jnyfuvlexwobyqn95je9d85
        foreign key (tournament_id) references poker_session_manager.tournaments (id)
            on delete cascade,
    constraint FKlilp2jtoaphwi0kj47x8qwb95
        foreign key (player_id) references poker_session_manager.users (id)
            on delete cascade
);

create table poker_session_manager.sessions_tournaments
(
    session_id    bigint not null,
    tournament_id bigint not null,
    constraint FK7a2o5bmdujv0i0dichxdq1yio
        foreign key (tournament_id) references poker_session_manager.tournaments (id)
            on delete cascade,
    constraint FKlj3ad4mtri80ygnhokkpjyddb
        foreign key (session_id) references poker_session_manager.sessions (id)
            on delete cascade

);

create table poker_session_manager.user_role
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint FKj345gk1bovqvfame88rcx7yyx
        foreign key (user_id) references poker_session_manager.users (id)
            on delete cascade,
    constraint FKt7e7djp752sqn6w22i6ocqy6q
        foreign key (role_id) references poker_session_manager.roles (id)
            on delete cascade
);

