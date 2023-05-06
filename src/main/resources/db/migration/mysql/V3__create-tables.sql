
create table roles
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table users
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
    constraint UK_users_email
        unique (email),
    constraint UK_users_username
        unique (username)
);

create table player_stats
(
    player_id        bigint not null
        primary key,
    average_profit   double not null,
    balance          double not null,
    profit           double not null,
    tournament_count int    not null,
    tournament_wins  int    not null,
    constraint FK_player_stats_users_user_id
        foreign key (player_id) references users (id)
            on delete cascade
);

create table poker_rooms
(
    id               bigint auto_increment
        primary key,
    name             varchar(255) null,
    poker_room_scope varchar(255) null,
    url              varchar(255) null,
    player_id        bigint       null,
    constraint FK_poker_rooms_users_user_id
        foreign key (player_id) references users (id)
            on delete cascade
);

create table sessions
(
    id               bigint auto_increment
        primary key,
    name             varchar(255) null,
    total_cost       double       not null,
    tournament_count int          null,
    player_id        bigint       null,
    constraint FK_sessions_users_user_id
        foreign key (player_id) references users (id)
            on delete cascade
);

create table tournaments
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
    constraint FK_tournaments_users_user_id
        foreign key (player_id) references users (id)
            on delete cascade,
    constraint FK_tournaments_poker_rooms_poker_room_id
        foreign key (poker_room_id) references poker_rooms (id)
            on delete cascade

);

create table player_favourite_tournaments
(
    player_id     bigint not null,
    tournament_id bigint not null,
    constraint FK_player_favourite_tournaments_tournaments_tournament_id
        foreign key (tournament_id) references tournaments (id)
            on delete cascade,
    constraint FK_player_favourite_tournaments_users_user_id
        foreign key (player_id) references users (id)
            on delete cascade
);

create table sessions_tournaments
(
    session_id    bigint not null,
    tournament_id bigint not null,
    constraint FK_sessions_tournaments_tournaments_tournament_id
        foreign key (tournament_id) references tournaments (id)
            on delete cascade,
    constraint FK_sessions_tournaments_sessions_session_id
        foreign key (session_id) references sessions (id)
            on delete cascade

);

create table user_role
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint FK_user_role_users_user_id
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint FK_user_role_roles_role_id
        foreign key (role_id) references roles (id)
            on delete cascade
);

