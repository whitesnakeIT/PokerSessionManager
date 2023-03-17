drop table if exists users cascade;
drop table if exists tournaments cascade;
drop table if exists roles cascade;
drop table if exists user_role cascade;
drop table if exists player_stats cascade;
drop table if exists player_favourite_tournaments cascade;
drop table if exists poker_rooms cascade;
drop table if exists sessions cascade;
drop table if exists sessions_tournaments cascade;


create table player_favourite_tournaments
(
    player_id     bigint not null,
    tournament_id bigint not null
);
create table player_stats
(
    player_id        bigint    not null,
    average_profit   float(53) not null,
    balance          float(53) not null,
    profit           float(53) not null,
    tournament_count integer   not null,
    tournament_wins  integer   not null,
    primary key (player_id)
);
create table poker_rooms
(
    id               bigint not null,
    name             varchar(255),
    poker_room_scope varchar(255),
    url              varchar(255),
    player_id        bigint,
    primary key (id)
);
create table roles
(
    id   bigint,
    name varchar(255),
    primary key (id)
);
create table sessions
(
    id               bigint not null,
    name             varchar(255),
    total_cost       float(53) not null,
    tournament_count integer,
    player_id        bigint,
    primary key (id)
);
create table sessions_tournaments
(
    session_id    bigint not null,
    tournament_id bigint not null
);
create table tournaments
(
    tournament_scope varchar(31)  not null,
    id               bigint not null,
    buy_in           float(53)    not null,
    handed           integer      not null check (handed >= 2 AND handed <= 10),
    name             varchar(255) not null,
    re_buy           boolean      not null,
    speed            varchar(255) not null,
    type             varchar(255) not null,
    player_id        bigint,
    poker_room_id    bigint,
    primary key (id)
);
create table user_role
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);
create table users
(
    user_type     varchar(31)  not null,
    id            bigint not null,
    birthday_date date         not null,
    created       timestamp(6),
    email         varchar(255) not null,
    enabled       integer      not null,
    first_name    varchar(255),
    last_name     varchar(255),
    password      varchar(255) not null,
    username      varchar(15)  not null,
    primary key (id)
);

alter table users
    add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table users
    add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
alter table player_favourite_tournaments
    add constraint FKj7jnyfuvlexwobyqn95je9d85 foreign key (tournament_id) references tournaments (id) on delete cascade;
alter table player_favourite_tournaments
    add constraint FKlilp2jtoaphwi0kj47x8qwb95 foreign key (player_id) references users (id) on delete cascade;
alter table player_stats
    add constraint FKk3toom4yb5inrke0fyxxshbvy foreign key (player_id) references users (id) on delete cascade;
alter table poker_rooms
    add constraint FK6huu2q810jfhmvmxyhy8r2t63 foreign key (player_id) references users (id) on delete cascade;
alter table sessions
    add constraint FKt7q6ursqju6c296a2j5dnttrr foreign key (player_id) references users (id) on delete cascade;
alter table sessions_tournaments
    add constraint FK7a2o5bmdujv0i0dichxdq1yio foreign key (tournament_id) references tournaments (id) on delete cascade;
alter table sessions_tournaments
    add constraint FKlj3ad4mtri80ygnhokkpjyddb foreign key (session_id) references sessions (id) on delete cascade;
alter table tournaments
    add constraint FKf8sf5nrihy1k239unqovhk6rp foreign key (player_id) references users (id) on delete cascade;
alter table tournaments
    add constraint FKkdai9sjqg3xrcvhkvlf4p1daw foreign key (poker_room_id) references poker_rooms (id) on delete cascade;
alter table user_role
    add constraint FKt7e7djp752sqn6w22i6ocqy6q foreign key (role_id) references roles (id) on delete cascade;
alter table user_role
    add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users (id) on delete cascade;
